package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.controller.AuthController;
import com.revature.controller.ReimbursementController;
import com.revature.controller.UserController;
import com.revature.models.Menu;
import com.revature.util.ConnectionFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import io.javalin.Javalin;

public class Driver {		

	public static void main(String[] args) throws SQLException {

		//        Role ro=Role.EMPLOYEE;
		//        User us=new User(1,"","",ro);
		//
		//        try(Connection conn = ConnectionFactory.getConnection()){
		//            System.out.println("Connection Successful :)");
		//        } catch(SQLException e) {
		//            System.out.println("Connec"+ "action failed");
		//            e.printStackTrace();
		//        }
		UserController userController = new UserController();
		ReimbursementController reimbursementController = new ReimbursementController();

		Javalin app = Javalin.create()
				.start(7001);

		//Testing if application is up or not
		app.get("/", ctx -> ctx.html("Hello Everyone!!!"));

		//To fetch all user details from the db
		app.get("/users", userController.fetchAllUsers);

		//To fetch user details by user id
		app.get("/users/:id", userController.fetchUserByID);

		//To fetch user details by user email
		app.get("/users/email/:email", userController.fetchUserByEmail);

		//To save the details of all users in the db
		app.post("/users", userController.saveUser);

		//To delete user details from the db by giving the id
		app.delete("/users/:id", userController.deleteUser);

		//To update the user details by giving id
		app.put("/users/:id", userController.updateUser);

		//To submit the reimbursement
		app.post("/users/:eId/reimbursements", reimbursementController.submitReimbursement);

		//To get the details of all reimbursements against employee eID
		app.get("/users/:eId/reimbursements", reimbursementController.getAllReimbursementsForEmployeeID);

		//To get the details of particular reimbursement against employee eID
		app.get("/users/:eId/reimbursements/:reimbursementId", reimbursementController.getReimbursementForEmployeeID);

		//To delete the particular reimbursement ONLY IF it is (Pending/ Denied)
		app.delete("/users/:eId/reimbursements/:reimbursementId", reimbursementController.deleteReimbursementForEmployeeID);

		//To edit the particular reimbursement ONLY IF it is (Pending/ Denied). After editing, change its status to PENDING again
		app.put("/users/:eId/reimbursements/:reimbursementId", reimbursementController.updateReimbursementForEmployeeID);
		
		Menu men=new Menu();
		men.displayMenu();
	}
}