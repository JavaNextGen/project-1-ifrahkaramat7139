package com.revature.controller;

import java.util.List;
import java.util.Objects;

import com.revature.models.User;
import com.revature.services.UserService;

import io.javalin.http.Handler;

public class UserController 
{
	UserService userService = new UserService();
	// For fetching all users // GET Req
	public Handler fetchAllUsers = ctx -> {
		List<User> usersList = userService.getAllUsers();
		if(usersList == null)
		{
			ctx.html("No User Found");
			ctx.status(404);
		}
		else
			ctx.json(usersList);
	};

	// For fetching user data by ID // GET Req
	public Handler fetchUserByID = ctx -> {
		int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
		User user = userService.getUserById(id);
		if(user == null)
		{
			ctx.html("No User Found for Id: " + id);
			ctx.status(404);
		}
		else
			ctx.json(user);
	};

	// For fetching user data by Email // GET Req
	public Handler fetchUserByEmail = ctx -> {
		String email = Objects.requireNonNull(ctx.pathParam("email")).toString();
		User user = userService.getUserByEmail(email);
		if(user == null)
		{
			ctx.html("No User Found for Email: " + email);
			ctx.status(404);
		}
		else
			ctx.json(user);
	};

	// For saving the user data // POST Req
	public Handler saveUser = ctx -> {
		try
		{
			User user = ctx.bodyAsClass(User.class);
			user.setId(userService.createUser(user).getId());
			ctx.json(user);
			ctx.status(201);
		}
		catch(Exception exc)
		{
			ctx.json(exc.getMessage());
			ctx.status(400);
		}
	};

	// For deleting the user data // DELETE Req
	public Handler deleteUser = ctx -> {
		int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
		int result = userService.removeUser(id);
		if (result == 0)
		{
			ctx.html("No User Found for Id: " + id);
			ctx.status(404);
		}
		else
		{
			ctx.html("User associated with ID: " + id + " has been deleted!");
			ctx.status(200);
		}


	};
	
	// For updating the user data // PUT Req
	public Handler updateUser = ctx -> {
		int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
		try
		{
			User user = ctx.bodyAsClass(User.class);
			user.setId(id);
			int result = userService.updateUser(id, user);
			
			if(result == 0)
			{
				ctx.html("No User Found for Id: " + id);
				ctx.status(404);
			}
			else if(result == -1)
			{
				ctx.html("Unable to update the Id.");
				ctx.status(500);
			}
			else
			{
				ctx.html("User associated with ID: " + id + " has been updated successfully!");
				ctx.status(200);
			}
		}
		catch(Exception exc)
		{
			ctx.json(exc.getMessage());
			ctx.status(400);
		}
	};;
}
