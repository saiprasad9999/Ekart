package com.ecommerce;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

	public class AdminUser {
		Boolean exit = false;
		String loginId;
		String password;
		Scanner scanner = null;
		
		public void verifyUser() {
		
		try {
			scanner=new Scanner(System.in);
			System.out.println("Admin User Login");
			System.out.println("Enter Admin Login ID>>");
			loginId=scanner.next();
			System.out.println("Enter Password>>");
			password=scanner.next();
			authentication(loginId,password);
			
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			scanner.close();
		}
	}
		
		public void authentication(String loginId, String password) throws SQLException {
			
			Connection con=null;
			PreparedStatement preparedStatement=null;
			
			try {
				con = FetchProductList.getConnectionDetails();
				preparedStatement=con.prepareStatement("select count(idadmin) from admin where loginId=? and password=?;");
				preparedStatement.setString(1, loginId);
				preparedStatement.setString(2, password);
				ResultSet resultSet=preparedStatement.executeQuery();
				if(resultSet.next()) {
					if(resultSet.getString(1).equals("1")) {
						System.out.println("Login Successful..");
						adminMenu();						
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
		
		public void adminMenu() throws IOException, SQLException {
			
			char choice;
			
			System.out.println("\nAdmin Menu:");
			System.out.println("A. Add product details");
			System.out.println("B. Check product quantity");
			System.out.println("C. Display registered users list");
			System.out.println("D. Purchase history for a user");
			System.out.println("E. Exit\n");
			
			Welcome welcome=new Welcome();
			choice=welcome.userChoice();
			
			if(choice=='A') {
				AddProductDetails.setProductDetails();
			}else if (choice=='B') {				
				checkProductQuantity();
			}else if (choice=='C') {
				displayUserList();
			}else if (choice=='D') {
				showPurchaseHistory();
			}else if (choice=='E') {
				exit = true;
//				
			}else {
				System.out.println("Incorrect choice. Please select correct option.");		
				adminMenu();
			}
			
			Logout.doLogout();
		}
		
		public void checkProductQuantity() throws SQLException, IOException {
			int productId = 0;
			Connection con=null;
			PreparedStatement preparedStatement=null;
			Scanner scanner = null;
			
			try {
				scanner = new Scanner(System.in);
				System.out.println("Enter Product ID>>");
				productId=scanner.nextInt();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally {
//				scanner.close();
			}
			
			
			System.out.println("Available Product Quantiy>>\n");
			try {
				con=FetchProductList.getConnectionDetails();
				preparedStatement=con.prepareStatement("select * from products where id=?;");
				preparedStatement.setInt(1, productId);
				ResultSet resultSet=preparedStatement.executeQuery();
				if(resultSet.next()) {
					System.out.println("Available Quantity for product "+resultSet.getString("name")+" is >> "+resultSet.getInt("quantity")+".");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(exit != true) {
					adminMenu();					
				}
				con.close();
				preparedStatement.close();
				
			}			
		}
		
		public void displayUserList() throws SQLException, IOException {
			
			Connection con=null;
			PreparedStatement preparedStatement=null;
			
			System.out.println("\nRegistered Users List>>\n");
			try {
				con=FetchProductList.getConnectionDetails();
				preparedStatement=con.prepareStatement("select firstname,lastname,loginid from users;");
				ResultSet resultSet=preparedStatement.executeQuery();
								
				System.out.println();
				System.out.println("-----------------------------------------------------------------------------------------");
				System.out.println("                                     Registered Users List              ");
				System.out.println("-----------------------------------------------------------------------------------------");
				
				System.out.printf("%30s %30s %30s","First Name","Last Name", "Login ID");
				System.out.println();
				System.out.println("-----------------------------------------------------------------------------------------");
				
				while(resultSet.next()) {
					System.out.printf("%30s %30s %30s",resultSet.getString("firstname"),resultSet.getString("lastname"), resultSet.getString("loginid"));
					System.out.println();				
				}				
				System.out.println("-----------------------------------------------------------------------------------------");				
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {			
				con.close();
				preparedStatement.close();
				adminMenu();
			}
			
		}
		
	
		public void showPurchaseHistory() throws SQLException, IOException {
			
			Connection con=null;
			PreparedStatement preparedStatement=null;
			Scanner scanner=new Scanner(System.in);
			
			String userLoginId=null;
			
			System.out.println("\nPurchase History>>\n");
			try {
				System.out.println("Enter Login ID of user to check purchase history>>");
				userLoginId=scanner.nextLine();
				
				con=FetchProductList.getConnectionDetails();
				preparedStatement=con.prepareStatement("SELECT users.firstname, users.lastname, purchase_history.loginid, purchase_history.quantity, purchase_history.date_created, products.name, products.description FROM purchase_history INNER JOIN users on purchase_history.loginid = users.loginid INNER JOIN products on purchase_history.idproducts = products.id where purchase_history.loginid=?;");
				preparedStatement.setString(1, userLoginId);
				
				ResultSet resultSet=preparedStatement.executeQuery();				
				
				System.out.println();
				System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println("                                     Purchase History              ");
				System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
				
				System.out.printf("%15s %15s %15s %25s %15s %25s %30s","First Name","Last Name", "Login ID", "Purchased Quantity", "Product Name", "Product Description", "Purchased Data & Time");
				System.out.println();
				System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");
				
				while(resultSet.next()) {
					System.out.printf("%15s %15s %15s %25s %15s %25s %30s", resultSet.getString("firstname"),resultSet.getString("lastname"), resultSet.getString("loginid"), resultSet.getInt("quantity"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("date_created"));
					System.out.println();				
				}
				System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------");				
				
			}catch(Exception e) {
				e.printStackTrace();
			}finally {	
				if(exit != true ) {
					adminMenu();					
				}
//				scanner.close();
//				con.close();
//				preparedStatement.close();				
			}
			
			
		}
		
	}