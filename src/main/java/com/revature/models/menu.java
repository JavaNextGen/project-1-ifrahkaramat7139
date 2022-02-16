package com.revature.models;

import java.sql.SQLException;
import java.util.Scanner;

import com.revature.controller.UserController;
import com.revature.repositories.UserDAO;
import com.revature.services.UserService;

import io.javalin.Javalin;

public class Menu {

	public void displayMenu() throws SQLException {

		UserController userController = new UserController();
		Javalin app = Javalin.create()
				.start(7000);
		app.get("/", ctx -> ctx.html("Hello Everyone!!!"));
		
		boolean displayMenu = true;
		Scanner scan = new Scanner(System.in);

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Employee Login ");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");


		while(displayMenu) {

			//menu option


			String input = scan.nextLine();

			switch(input){

			case "Show All":
			{
				app.get("/users", userController.fetchAllUsers);
			}
			break;
			case "user by id":
			{
				System.out.println("Enter User ID: ");
				int id = scan.nextInt();
				app.get("/users/:id", userController.fetchUserByID);
			}
			break;
			case "user by email":
			{
				System.out.println("Enter Email ID: ");
				String email = scan.next();
				app.get("/users/:email", userController.fetchUserByEmail);
			}
			break;
			
			case "create": {
				System.out.println("inserting user ");
				System.out.println("enter  user name ");
				String unm=scan.nextLine();
				System.out.println("enter  password ");
				String pwd=scan.nextLine();
				System.out.println("enter  first name ");
				String fnm=scan.nextLine();
				System.out.println("enter  last name ");
				String lnm=scan.nextLine();
				System.out.println("enter  email ");
				String eml=scan.nextLine();
				System.out.println("enter role id ");
				int rolid=scan.nextInt();
				Role ro=Role.EMPLOYEE;
				if (rolid==2) {
					ro=Role.FINANCE_MANAGER;
				}
				UserService us=new UserService();
				//get the List of users from the repository layer
				User u=new User(0,unm,pwd,fnm,lnm,eml,ro);
				User usr=us.createUser(u);
				System.out.println(usr);
				break; //we need a break in each case block, or else all the other cases will still run
			}
			case "last name": {

				System.out.println("What Is Your Last Name:");
				break;
			}
			case "age":{
				System.out.println("What Is Your Age:");
				break;

			}
			case "email" : {
				System.out.println("What Is Your E-MAIL");
				break;
			}
			case "exit" :{
				System.out.println("THANK YOU ");
				break;
			}


			}
		}
	}




}
