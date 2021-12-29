package com.revature;

import java.sql.SQLException;

import com.revature.models.menu;

public class Driver {

    public static void main(String[] args) throws SQLException {
    	// cal the manu and display menu
    	menu m = new menu();
    	m.displayMenu();
    	
    }
}