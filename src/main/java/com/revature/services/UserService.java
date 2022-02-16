package com.revature.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.revature.models.User;
import com.revature.repositories.UserDAO;

/**
 * The UserService should handle the processing and retrieval of Users for the ERS application.
 *
 * {@code getByUsername} is the only method required;
 * however, additional methods can be added.
 *
 * Examples:
 * <ul>
 *     <li>Create User</li>
 *     <li>Update User Information</li>
 *     <li>Get Users by ID</li>
 *     <li>Get Users by Email</li>
 *     <li>Get All Users</li>
 * </ul>
 */
public class UserService {

	/**
	 *     Should retrieve a User with the corresponding username or an empty optional if there is no match.
     */
	
	UserDAO userDao = new UserDAO();
	public Optional<User> getByUsername(String username) {
		
		
		return Optional.empty();
	}
	public User createUser(User u) throws SQLException {
  
        User usr=userDao.createUser(u);
        return usr;
    }
	
	public List<User> getAllUsers() throws SQLException
	{
		return userDao.getUsers();
	}
	
	public User getUserById(int id) throws SQLException
	{
		return userDao.getUserById(id);
	}
	
	public User getUserByEmail(String email) throws SQLException
	{
		return userDao.getUserByEmail(email);
	}
	public int removeUser(int id) {
		
		return userDao.removeUser(id);
	}
	public int updateUser(int id, User user) {
		
		return userDao.updateUser(id, user);
	}
	
}
