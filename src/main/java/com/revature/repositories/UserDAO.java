package com.revature.repositories;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserDAO {

    /**
     * Should retrieve a User from the DB with the corresponding username or an empty optional if there is no match.
     */
    public Optional<User> getByUsername(String username) {
        return Optional.empty();
    }

    /**
     * <ul>
     *     <li>Should Insert a new User record into the DB with the provided information.</li>
     *     <li>Should throw an exception if the creation is unsuccessful.</li>
     *     <li>Should return a User object with an updated ID.</li>
     * </ul>
     *
     * Note: The userToBeRegistered will have an id=0, and username and password will not be null.
     * Additional fields may be null.
     * @throws SQLException 
     */
    public User create (User newUser) throws SQLException {
        
        try(Connection conn = ConnectionFactory.getConnection())
        {
            
            //we'll create a SQL statement using parameters to insert a new Employee
            String sql = "INSERT INTO ers_users ( user_name,user_password,fname, lname,email, role_id) " //creating a line break for readability
                        + "VALUES (?, ?, ?,?,?,?); "; //these are parameters!! We have to specify the value of each "?"
            
            PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS); //we use PreparedStatements for SQL commands with variables
            
            //use the PreparedStatement objects' methods to insert values into the query's ?s
            //the values will come from the Employee object we send in.
            ps.setString(1, newUser.getUsername()); //1 is the first ?, 2 is the second, etc.
            ps.setString(2, newUser.getPassword());
            
            ps.setString(3, newUser.getFirst_name()); //1 is the first ?, 2 is the second, etc.
            ps.setString(4, newUser.getLast_name());
            ps.setString(5, newUser.getEmail());
            if (newUser.getRole().toString().equalsIgnoreCase("EMPLOYEE")) {
                ps.setInt(6, 1);
                
            }
            else {ps.setInt(6, 2);}
            
            //this executeUpdate() method actually sends and executes the SQL command we built
            ps.executeUpdate(); //we use executeUpdate() for inserts, updates, and deletes. 
            //we use executeQuery() for selects
            
            //send confirmation to the console if successul.
            System.out.println("Employee " + newUser.getUsername()+ " created. Welcome aboard!");
             try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id=generatedKeys.getInt(1);
                        
                        User usr= getUserById(id);
                        return usr;
                    }
             }catch(Exception e) {
                        //throw new SQLException("Creating user failed, no ID obtained.");
                    }
        } catch(SQLException e) {
            System.out.println("Add employee failed! :(");
            e.printStackTrace();
        }
    return null;
    }

	private User getUserById(int id) {
		try(Connection conn = ConnectionFactory.getConnection())
        { //all of my SQL stuff will be within this try block

            //Initialize an empty ResultSet object that will store the results of our SQL query
            ResultSet rs = null;

            //write the query that we want to send to the database, and assign it to a String
            String sql ="select * from ers_users  where user_id = ?;"
                    ;// "SELECT user_name FROM ers_users  where user_id=?;";
            PreparedStatement pst=conn.prepareStatement(sql);

            //Put the SQL query into a Statement object (The Connection object has a method for this!!)
            pst.setInt(1, id);
            //EXECUTE THE QUERY, by putting the results of the query into our ResultSet object
            //The Statement object has a method that takes Strings to execute as a SQL query
            rs=pst.executeQuery();
            while(rs.next())
                    {
                    //int ud=rs.getInt("user_id");
                        int rd=rs.getInt("role_id");
                            Role ro=Role.EMPLOYEE;
                            if(rd==2)
                                ro=Role.FINANCE_MANAGER;


                            String    unm=null;
                            try {
                            unm=rs.getString("user_name");
                            System.out.println("user name " + unm);
                            }catch(Exception e) {System.out.println(e.getMessage());};

                        String    email=rs.getString("email");
                        String    fname=rs.getString("fname");
                        String    lname=rs.getString("lname");
                        String    pwd=rs.getString("user_password");
                            User user=new User(id,unm,pwd,fname,lname,email,ro);
                            return user;



                    }
                    }catch(SQLException e) {
                        System.out.println("error !!?");
                        e.printStackTrace();


                    }


                return null;
	}
}