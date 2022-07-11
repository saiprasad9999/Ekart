package com.ecommerce;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import jdk.jfr.Description;

public class Cart {

	int productId;
	int quantity;
	int more=0;
//	int productCount=0;
	ArrayList<Cart> arrayList=new ArrayList<Cart>();
	
	public void addToCart() {
				
		while(more==0) {
			
			try {			
				System.out.println("Enter Product ID that you want to purchase:");
				Scanner scanner=new Scanner(System.in);
				
				productId=scanner.nextInt();			
				
				System.out.println("Enter quantity that you want to purchase:");
				quantity=scanner.nextInt();
				
				Cart cart=new Cart();
				cart.productId=productId;
				cart.quantity=quantity;
				arrayList.add(cart);
//				productCount++;
				if (more==0)
					cartValueCalculation(arrayList);
				System.out.println("A. Add more products to the cart.");
				System.out.println("B. Proceed to buy cart items.");
				System.out.println("C. Exit");
				String choice=scanner.next();
				if(choice.equals("A")) {
					more = 0;			
				}else if (choice.equals("B")){
					more = 1;
					//cartValueCalculation(arrayList);
					buyProducts(arrayList);
				}else if (choice.equals("C")) {					
					Logout.doLogout();
				} else {
					addToCart();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				
			}
			
		}
		
	}
	
	public void buyProducts(ArrayList<Cart> arrayList) {
		
		double amount = 0;
		double price = 0;
		double total = 0;
		String name=null;
		String description=null;
		
		int productDatabaseQuantity=0;
		Connection con=null;
		PreparedStatement preparedStatement=null;
		PreparedStatement preparedStatement1=null;
		PreparedStatement preparedStatement2=null;
		
		try {
			

			
			System.out.println();
			System.out.println("-----------------------------------------------------------------------------------------");
			System.out.println("                                      INVOICE              ");
			System.out.println("-----------------------------------------------------------------------------------------");
			System.out.printf("%5s %30s %30s %10s %10s %10s","ID","Product Name", "Description","Quantity","Unit Price", "Amount");
			System.out.println();
			System.out.println("-----------------------------------------------------------------------------------------");
			
			con=FetchProductList.getConnectionDetails();		
			preparedStatement=con.prepareStatement("insert into purchase_history (loginid,idproducts,quantity,date_created) values(?,?,?,CURRENT_TIMESTAMP);");
			preparedStatement1=con.prepareStatement("select * from products where id=?");
						
			for(Cart cart : arrayList) {
				preparedStatement.setString(1, ExistingUser.loginId);
				preparedStatement.setInt(2, cart.productId);
				preparedStatement.setInt(3, cart.quantity);
				
				int a=preparedStatement.executeUpdate();
				
				preparedStatement1.setInt(1, cart.productId);
				ResultSet resultSet=preparedStatement1.executeQuery();
				while(resultSet.next()) {
					productDatabaseQuantity=resultSet.getInt("quantity");
					price = Integer.parseInt(resultSet.getString("price"));
					name = resultSet.getString("name");
					description = resultSet.getString("description");
				}
				
				preparedStatement2=con.prepareStatement("update products set quantity=? where id=?");
				
				preparedStatement2.setInt(1, (productDatabaseQuantity-cart.quantity));
				preparedStatement2.setInt(2, cart.productId);
				a=preparedStatement2.executeUpdate();				
				amount = cart.quantity * price;						
				System.out.printf("%5s %30s %30s %10s %10s %10s",cart.productId,name,description,cart.quantity,price,amount);
				System.out.println();
				total = total + amount;
			}			
				System.out.println("-----------------------------------------------------------------------------------------");			
		
				System.out.println("                                                                               Invoice Price>> "+total+"/-");
				System.out.println("                        Order placed Successfully");
				System.out.println("                    Product will be delivered shortly");
				System.out.println();
				
				System.out.println("-----------------------------------------------------------------------------------------");
				System.out.println();

			Logout.doLogout();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
		String q="";
		
		
	}
	

	public void cartValueCalculation(ArrayList<Cart> arrayList) {
		
		int price=0;
		Connection con=null;
		PreparedStatement preparedStatement=null;
		double amount=0;
		try {
			
			con=FetchProductList.getConnectionDetails();		
			
			preparedStatement=con.prepareStatement("select price from products where id=?");
						
			for(Cart cart : arrayList) {
				
				preparedStatement.setInt(1, cart.productId);
				ResultSet resultSet=preparedStatement.executeQuery();
				while(resultSet.next()) {
					price=resultSet.getInt("price");
					amount = amount+cart.quantity * price;
				}
			}
			
			System.out.println("\nCurrent Cart Amount>>"+amount+"\n");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
		String q="";
		
		
	}
	
}
