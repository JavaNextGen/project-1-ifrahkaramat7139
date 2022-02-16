package com.revature.repositories;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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

	// For saving User 
	public User createUser(User newUser) throws SQLException {

		try(Connection conn = ConnectionFactory.getConnection())
		{
			//we'll create a SQL statement using parameters to insert a new Employee
			String sql = "INSERT INTO ers_users ( ers_username,ers_password,user_first_name, user_last_name,user_email, user_role_id) " //creating a line break for readability
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
			else {
				ps.setInt(6, 2);
			}


			ps.executeUpdate(); //we use executeUpdate() for inserts, updates, and deletes. 
			//we use executeQuery() for selects

			//send confirmation to the console if successful.
			System.out.println("Employee " + newUser.getUsername()+ " created. Welcome Aboard!");
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
		}
		return null;
	}

	// For fetching User by ID
	public User getUserById(int id)
	{
		try(Connection conn = ConnectionFactory.getConnection())
		{

			String query ="select * from ers_users where ers_users_id = ?";// "SELECT user_name FROM ers_users  where user_id=?;";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, id);

			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				User user = getUserObject(rs);
				return user;
			}
		}
		catch(SQLException e) 
		{
			System.out.println("error !!?");
			e.printStackTrace();
		}


		return null;
	}

	// For fetching user by email id
	public User getUserByEmail(String email)
	{
		try(Connection conn = ConnectionFactory.getConnection())
		{

			String query ="select * from ers_users where user_email = ?";// "SELECT user_name FROM ers_users  where user_email=?;";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, email);

			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				User user = getUserObject(rs);
				return user;
			}
		}
		catch(SQLException e) 
		{
			System.out.println("error !!?");
			e.printStackTrace();
		}


		return null;
	}

	// For fetching all the users
	public List<User> getUsers()
	{
		try(Connection conn = ConnectionFactory.getConnection())
		{
			Statement selectStmt = conn.createStatement();
			String query = "SELECT * from ers_users";

			List<User> users = new ArrayList<>();
			ResultSet rs = selectStmt
					.executeQuery(query);
			while(rs.next())
			{
				User user = getUserObject(rs);
				users.add(user);

			}
			return users;
		}
		catch(SQLException e) 
		{
			System.out.println("error !!?");
			e.printStackTrace();
		}
		return null;

	}

	// For removing user by ID
	public int removeUser(int id) 
	{	
		try(Connection conn = ConnectionFactory.getConnection())
		{

			String query ="Delete from ers_users where ers_users_id = ?";// "SELECT user_name FROM ers_users  where user_id=?;";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, id);

			int result = pst.executeUpdate();
			return result;
		}
		catch(SQLException e) 
		{
			System.out.println("error !!?");
			e.printStackTrace();
		}
		return -1;
	}

	public int updateUser(int id, User user) 
	{
		
		try(Connection conn = ConnectionFactory.getConnection())
		{
			String query ="select * from ers_users where ers_users_id = ?";// "SELECT user_name FROM ers_users  where user_id=?;";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, id);

			ResultSet resultSet = pst.executeQuery();
			if(!resultSet.next()) // if no data in resultset, ID doesn't exist
			{
				return 0;
			}
						
			String updateQuery = "Update ers_users "
					+ "set ers_username=?, "
					+ "ers_password=?, "
					+ "user_first_name=?, "
					+ "user_last_name=?, "
					+ "user_email=?,"
					+ "user_role_id=? "
					+ " where ers_users_id=?";
			PreparedStatement ps = conn.prepareStatement(updateQuery);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirst_name()); //1 is the first ?, 2 is the second, etc.
			ps.setString(4, user.getLast_name());
			ps.setString(5, user.getEmail());
			if (user.getRole().toString().equalsIgnoreCase("EMPLOYEE")) {
				ps.setInt(6, 1);
			}
			else {
				ps.setInt(6, 2);
			}
			ps.setInt(7, user.getId());
			
			int result = ps.executeUpdate();
			return result;
		}
		catch(SQLException e) 
		{
			System.out.println("error !!?");
			e.printStackTrace();
		}
		return -1;
	}

	// utility function
	private User getUserObject(ResultSet rs) throws SQLException
	{
		int role = rs.getInt("user_role_id");
		Role rl;
		if(role == 1)
			rl = Role.EMPLOYEE;
		else
			rl = Role.FINANCE_MANAGER;

		User user = new User(rs.getInt("ers_users_id"), 
				rs.getString("ers_username"), 
				rs.getString("ers_password"), 
				rs.getString("user_first_name"), 
				rs.getString("user_last_name"), 
				rs.getString("user_email"), 
				rl);
		return user;
	}


}