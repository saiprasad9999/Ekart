package com.ecommerce;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddProductDetails {
	
	public static void insertProductData( String name, String desc, String price, int qty) throws SQLException{
		
		Connection connection=null;
		PreparedStatement ps=null;
		
		try {			
			
			connection=FetchProductList.getConnectionDetails();
			ps=connection.prepareStatement("insert into products(description,price,name,quantity) values(?,?,?,?)");
			ps.setString(1, desc);
			ps.setString(2, price);
			ps.setString(3, name);
			ps.setInt(4, qty);
			ps.executeUpdate();
			System.out.println("\nDetails inserted>>");			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
			connection.close();
			ps.close();
		}
		
	}


	public static void setProductDetails() throws SQLException, IOException {
		Scanner sc=null;;
		int number=0;
		try {
			sc = new Scanner(System.in);			
			System.out.println("Enter number of products to add");
			number = Integer.parseInt(sc.nextLine());
			
			for(int i=1;i<=number;i++) {
				System.out.println("Enter Product name");
				String name=sc.nextLine();
				System.out.println("Enter Product Desc");
				String Desc=sc.nextLine();
				System.out.println("Enter Price");
				String price=sc.nextLine();
				System.out.println("Enter Qty");			
				int qty=Integer.parseInt(sc.nextLine());
				
				insertProductData( name, Desc, price, qty);
			}
			
			AdminUser adminUser=new AdminUser();
			adminUser.adminMenu();
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setProductDetails();
		}finally {
			sc.close();
		}
		
	}

}