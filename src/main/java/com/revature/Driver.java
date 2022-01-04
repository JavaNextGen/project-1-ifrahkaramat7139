package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.models.menu;
import com.revature.util.ConnectionFactory;

public class Driver {		
	
    public static void main(String[] args) throws SQLException {

        Role ro=Role.EMPLOYEE;
        User us=new User(1,"","",ro);

        try(Connection conn = ConnectionFactory.getConnection()){
            System.out.println("Connection Successful :)");
        } catch(SQLException e) {
            System.out.println("Connec"+ "action failed");
            e.printStackTrace();
        }

        menu  men=new menu();
        men.displayMenu();
    }
}