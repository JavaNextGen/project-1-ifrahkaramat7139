package com.revature.models;

import java.sql.SQLException;
import java.util.Scanner;

import com.revature.repositories.UserDAO;
import com.revature.services.UserService;

public class menu {
	
	public void displayMenu() throws SQLException {

		 UserService serv = new UserService();	
		 UserDAO dao = new UserDAO();
			boolean displayMenu = true;
			Scanner scan = new Scanner(System.in);
			
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Employee Login ");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
			
			
	            while(displayMenu) {
				
				//menu option
				
				
				String input = scan .nextLine();
				
				switch(input){
				
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
	                System.out.println("enter  role id ");
	                int rolid=scan.nextInt();
	                Role ro=Role.EMPLOYEE;
	                if (rolid==2) {
	                    ro=Role.FINANCE_MANAGER;
	                }
	                UserService us=new UserService();
	                //get the List of users from the repository layer
	                User u=new User(0,unm,pwd,fnm,lnm,eml,ro);
	                User usr=us.create(u);
	                System.out.println(usr);
	                break; //we need a break in each case block, or else all the other cases will still run
	            }
				case "last name": {
					
					System.out.println("What Is Your Last Name:");
					break;
				}
				case"age":{
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