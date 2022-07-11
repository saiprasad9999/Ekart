package com.ecommerce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Welcome {
//this is welcome file
	public static void main(String[] args) throws IOException {
		displayLandingPage();
	}
	
	public static void displayLandingPage() throws IOException {
		
		char choice='D';
		System.out.println("Welcome to the E-Commerce portal!");
		
		System.out.println("A: New User \nB: Existing User \nC: Admin Login \nD: Exit\n");
		Welcome welcome=new Welcome();
		choice=welcome.userChoice();
		
		while (choice!='A' && choice!='B' && choice!='C' && choice!='D') {
			System.out.println("Please enter correct choice from provided options>>");
			System.out.println("A: New User \nB: Existing User \nC: Admin Login \nD: Exit\n");
			choice=welcome.userChoice();
		}
			
		if (choice=='A'){
			NewUser newUser=new NewUser();
			newUser.getNewUserDetails();
		} else if (choice=='B'){
			
			ExistingUser existingUser=new ExistingUser();
			existingUser.verifyUser();
			
		} else if (choice=='D') {
			Logout.doLogout();
		} else {
			
			AdminUser adminUser=new AdminUser();
			adminUser.verifyUser();
			
		}
	}
	
	public char userChoice() throws IOException {

		BufferedReader bufferedReader = null;
		char choice='D';
		try{
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			choice= bufferedReader.readLine().charAt(0);
		}catch(Exception e) {
			e.printStackTrace();			
		}finally {
//			bufferedReader.close();
		}
		return choice;
	}
	
}
