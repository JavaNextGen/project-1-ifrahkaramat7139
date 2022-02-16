package com.revature.models;

/**
 * This concrete User class can include additional fields that can be used for
 * extended functionality of the ERS application.
 *
 * Example fields:
 *
 * <ul>
 *     <li>First Name</li>
 *     <li>Last Name</li>
 *     <li>Email</li>
 *     <li>Phone Number</li>
 *     <li>Address</li>
 * </ul>
 *
 */

public class User extends AbstractUser {

	private String first_name;
	private String last_name;
	private String email;
	public User()
	{
		super();
	}
	public User(int id, String username, String password, Role role) 
	{
		super(id,username, password, role);  
	}   
	public User(int id, String username, String password, String fnm,String lnm, String eml,Role role)   
	{ 
		super(id,username, password, role);        
		this.first_name=fnm;         
		this.last_name=lnm; 
		this.email=eml;    
	}

	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [id=" + getId() + ", username=" + getUsername() + ", password=" + getPassword() + ", role=" + getRole() + "first_name=" + first_name + ", last_name=" + last_name + ", email=" + email + "]";
	}
	
	

}
