package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.util.ConnectionFactory;

public class AuthDAO {

	public static boolean authenticateUser(int eId, String username, String password) {
		
		try(Connection con = ConnectionFactory.getConnection())
		{
			String sqlEmployee = "Select * from ers_users where ers_users_id = ? and ers_username = ? and ers_password = ?";
			PreparedStatement ps = con.prepareStatement(sqlEmployee);
			ps.setInt(1, eId);
			ps.setString(2, username);
			ps.setString(3, password);
			System.out.println(username + " " + password);
			ResultSet resultSet = ps.executeQuery();
			if(!resultSet.next()) // if no data in resultset, ID doesn't exist
			{
				System.out.println("Employee doesn't exist!");
				return false; // User not exist.
			}
			return true;
		}
		catch(SQLException sqlExc)
		{
			sqlExc.printStackTrace();
		}
		return false;
	}

}