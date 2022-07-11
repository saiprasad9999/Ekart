package com.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class NewUser {

	String fname;
	String lname;
	String loginId;
	String password=null;
	
	public void getNewUserDetails() {
		Scanner scanner = null;
		try {
			
			scanner=new Scanner(System.in);	
			System.out.println("\nEnter First Name>>");
			fname=scanner.next();
			System.out.println("Enter Last Name>>");
			lname=scanner.next();
			System.out.println("Enter Login ID>>");
			loginId=scanner.next();			
			System.out.println("Enter Password>>");
			password=scanner.next();
			if(checkLoginIdAvailability(loginId)) {
				addUser(fname, lname, loginId, password);	
			}else {
				getNewUserDetails();
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
//			scanner.close();
		}		
	}
	
	private boolean checkLoginIdAvailability(String loginId) throws SQLException {
				
		Connection con=null;
		PreparedStatement preparedStatement=null;
		Boolean boolean1=false;
		try {
			con = FetchProductList.getConnectionDetails();
			preparedStatement=con.prepareStatement("select count(idusers) from users where loginId=?;");
			preparedStatement.setString(1, loginId);
			ResultSet resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				if(resultSet.getString(1).equals("1")) {
					System.out.println("This loginId is already used by someone else. Please enter new Login ID.");
					boolean1=false;
				}else {
					boolean1=true;
				}
			}
			
			} catch (Exception e) {
				e.printStackTrace();
				}finally {						
					con.close();
					preparedStatement.close();
					}
			return boolean1;
		
	}

	public void addUser(String fname, String lname, String loginId, String password) throws SQLException {
		
		Connection con=null;
		PreparedStatement preparedStatement=null;
		
		try {
			
			con=FetchProductList.getConnectionDetails();
			
			preparedStatement=con.prepareStatement("insert into users(firstname,lastname,loginid,password)values(?,?,?,?)");	
			preparedStatement.setString(1, fname);
			preparedStatement.setString(2, lname);
			preparedStatement.setString(3, loginId);
			preparedStatement.setString(4, password);
			int a=preparedStatement.executeUpdate();
			System.out.println("User "+fname+" added successfully");
			ExistingUser existingUser=new ExistingUser();
			existingUser.verifyUser();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			con.close();
			preparedStatement.close();
		}
	}
	
}
