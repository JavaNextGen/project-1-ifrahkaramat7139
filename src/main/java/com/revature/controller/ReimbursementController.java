package com.revature.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.revature.models.AbstractReimbursement;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ReimbursementService;

import io.javalin.http.Handler;

public class ReimbursementController 
{
	ReimbursementService reimbursementService = new ReimbursementService();

	// POST for creating reimbursement
	public Handler submitReimbursement = ctx -> {
		try {


			// Employee ID from path parameter
			int eid = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("eId")));

			// Employee user name and password from request headers
			Map<String, String> headers = ctx.headerMap();

			// Reimbursement Body
			Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);

			if(!AuthController.authenticateUser(eid, headers))
			{
				ctx.json("User not found. Please check your credentials!");
				ctx.status(401);
				return;
			}

			int result = reimbursementService.saveReimbursement(eid, reimbursement);

			if(result == -1)
			{
				ctx.json("Unable to update the Rimbursement details!");
				ctx.status(500);
			}
			else
			{
				ctx.json("Reimbursement submitted with id " + result);
				ctx.status(201);
			}

		}
		catch(Exception exc)
		{
			ctx.json(exc.getMessage());
			ctx.status(400);
		}

	};

	// GET for retrieving all reimbursements for particular employee id
	public Handler getAllReimbursementsForEmployeeID = ctx -> {

		// Employee ID from path parameter
		int eid = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("eId")));

		// Employee user name and password from request headers
		Map<String, String> headers = ctx.headerMap();

		if(!AuthController.authenticateUser(eid, headers))
		{
			ctx.json("User not found. Please check your credentials!");
			ctx.status(401);
			return;
		}

		List<Reimbursement> reimbursements = reimbursementService.getAllReimbursementsFromEmpId(eid);

		if(reimbursements == null)
		{
			ctx.html("No Reimbursement Found for Employee " + eid);
			ctx.status(404);
		}
		else
			ctx.json(reimbursements);
	};

	// GET for retrieving particular reimbursement using reimbursement ID for particular employee ID
	public Handler getReimbursementForEmployeeID = ctx -> {
		// Employee ID from path parameter
		int eid = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("eId")));
		int rid = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("reimbursementId")));

		// Employee user name and password from request headers
		Map<String, String> headers = ctx.headerMap();

		if(!AuthController.authenticateUser(eid, headers))
		{
			ctx.json("User not found. Please check your credentials!");
			ctx.status(401);
			return;
		}

		Reimbursement reimbursement = reimbursementService.getReimbursementFromEmpId(eid, rid);

		if(reimbursement == null)
		{
			ctx.html("No Reimbursement Found for Employee " + eid + " with reimbursement ID " + rid);
			ctx.status(404);
		}
		else
			ctx.json(reimbursement);
	};

	public Handler deleteReimbursementForEmployeeID = ctx -> {
		// Employee ID from path parameter
		int eid = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("eId")));
		int rid = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("reimbursementId")));

		// Employee user name and password from request headers
		Map<String, String> headers = ctx.headerMap();

		if(!AuthController.authenticateUser(eid, headers))
		{
			ctx.json("User not found. Please check your credentials!");
			ctx.status(401);
			return;
		}

		int result = reimbursementService.deleteReimbursementFromEmpId(eid, rid);

		if(result == 0)
		{
			ctx.html("No Pending/Denied Reimbursement Found for Employee " + eid + " with reimbursement ID " + rid);
			ctx.status(404);
		}
		else if(result == 1)
		{
			ctx.html("Reimbursement associated with ID: " + rid + " for employee ID " + eid + " has been deleted!");
			ctx.status(200);
		}
	};

	public Handler updateReimbursementForEmployeeID = ctx -> {
		try {
			// Employee ID from path parameter
			int eid = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("eId")));
			int rid = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("reimbursementId")));
			
			// Employee user name and password from request headers
			Map<String, String> headers = ctx.headerMap();

			// Reimbursement Body
			Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);

			if(!AuthController.authenticateUser(eid, headers))
			{
				ctx.json("User not found. Please check your credentials!");
				ctx.status(401);
				return;
			}

			int result = reimbursementService.updateReimbursement(eid, rid, reimbursement);

			if(result == 0)
			{
				ctx.html("No Pending/Denied Reimbursement Found for Employee " + eid + " with reimbursement ID " + rid);
				ctx.status(404);
			}
			else
			{
				ctx.json("Reimbursement associated with id " + result + " has been updated!");
				ctx.status(201);
			}

		}
		catch(Exception exc)
		{
			ctx.json(exc.getMessage());
			ctx.status(400);
		}

	};

}