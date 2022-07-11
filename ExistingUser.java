package com.ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ExistingUser {
	
	static String loginId;
	String password;
	
	public void verifyUser() {
	
		try {
			Scanner scanner=new Scanner(System.in);
			System.out.println("\nExisting User Login");
			System.out.println("Enter Login ID>>");
			loginId=scanner.next();
			System.out.println("Enter Password>>");
			password=scanner.next();
			authentication(loginId,password);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
	public void authentication(String loginId, String password) throws SQLException {
				
		Connection con=null;
		PreparedStatement preparedStatement=null;
		
		try {
			con = FetchProductList.getConnectionDetails();
			preparedStatement=con.prepareStatement("select count(idusers) from users where loginId=? and password=?;");
			preparedStatement.setString(1, loginId);
			preparedStatement.setString(2, password);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				if(resultSet.getString(1).equals("1")) {
					System.out.println("\nLogin Successful..");
					FetchProductList fetchProductList=new FetchProductList();
					fetchProductList.getProductData();
					}
				else {
					System.out.println("Login failed. Username or password incorrect, Please try again.");
					verifyUser();
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
				}finally {			
					con.close();
					preparedStatement.close();
				}
		
	}	
	
	
	
}
