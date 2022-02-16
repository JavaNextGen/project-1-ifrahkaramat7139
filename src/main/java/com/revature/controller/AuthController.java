package com.revature.controller;

import java.util.Map;

import com.revature.services.AuthService;

import io.javalin.http.Handler;

public class AuthController {

	public static boolean authenticateUser(int eId, Map<String, String> headers)
	{
		return AuthService.authenticateUser(eId, headers);
	}
		
}