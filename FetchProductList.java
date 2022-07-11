package com.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

//Class to fetch the product information from the database
public class FetchProductList {

	//Method to create db connection
	public static Connection getConnectionDetails() {
		
		Connection con=null;
		
		try {
			//Loading Driver class
			Class.forName("com.mysql.jdbc.Driver");

			//Establishing connection with MySQL database
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "pune");
			
		}catch(Exception e) {
			e.printStackTrace();
		}	
		return con;
	}
	
	//Method to retrieve the product data from the database, sort it based on the name and display the list of products having quantity greater than zero
	public void getProductData() throws SQLException {
		Connection con=null;
		PreparedStatement preparedStatement=null;
		ArrayList<Products> arrayList=new ArrayList<Products>();
		System.out.println("\nAvailable Product List\n");
		 
		try {
			con=getConnectionDetails();
			preparedStatement=con.prepareStatement("select * from products;");
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				if(resultSet.getInt("quantity")!=0) {
					Products products=new Products();
					products.setId(resultSet.getInt("id"));
					products.setDescription(resultSet.getString("description"));
					products.setPrice(resultSet.getString("price"));
					products.setName(resultSet.getString("name"));
					products.setQuantity(resultSet.getInt("quantity"));
					arrayList.add(products);
				}
			}
			
			Collections.sort(arrayList);			
			
			System.out.println("-----------------------------------------------------------------------------------------");
			System.out.printf("%5s %30s %30s %10s %10s ","ID","Product Name", "Description","Price","Qty");
			System.out.println();
			System.out.println("-----------------------------------------------------------------------------------------");
			for(Products products:arrayList) {
				System.out.format("%5s %30s %30s %10s %10s",products.getId(),products.getName(),products.getDescription(),products.getPrice(),products.getQuantity());
				System.out.println();
				
			}
			System.out.println("-----------------------------------------------------------------------------------------");
			System.out.println();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {			
			con.close();
			preparedStatement.close();			
		}
		Cart cart=new Cart();
		cart.addToCart();		
	}
	
}