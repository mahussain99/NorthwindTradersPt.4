package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        if (args.length != 2) {
            System.out.println("Application needs two arguments to run: " + "java com.pluralsight.Main <username> <password>");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("3) Display all categories");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayAllProducts(username, password);
                    break;
                case 2:
                    displayAllCustomers(username, password);
                    break;
                case 3:
                    Displayallcategories(username, password);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void displayAllProducts(String username, String password) {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet results = statement.executeQuery()) {

                while (results.next()) {
                    int productId = results.getInt("ProductID");
                    String productName = results.getString("ProductName");
                    double unitPrice = results.getDouble("UnitPrice");
                    int unitsInStock = results.getInt("UnitsInStock");

                    System.out.println("Product ID: " + productId);
                    System.out.println("Product Name: " + productName);
                    System.out.println("Unit Price: " + unitPrice);
                    System.out.println("Units In Stock: " + unitsInStock);
                    System.out.println("-----------------------------------------");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }
    }

    private static void displayAllCustomers(String username, String password) {
        String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet results = statement.executeQuery()) {

                while (results.next()) {
                    String contactName = results.getString("ContactName");
                    String companyName = results.getString("CompanyName");
                    String city = results.getString("City");
                    String country = results.getString("Country");
                    String phone = results.getString("Phone");

                    System.out.println("Contact Name: " + contactName);
                    System.out.println("Company Name: " + companyName);
                    System.out.println("City: " + city);
                    System.out.println("Country: " + country);
                    System.out.println("Phone: " + phone);
                    System.out.println("-----------------------------------------");
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

        }
    }

    private static void Displayallcategories(String username, String password) {

        String quesry = """ 
                SELECT CategoryID, CategoryName FROM Categories;
                """;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);
                 PreparedStatement statement = connection.prepareStatement(quesry);
                 ResultSet results = statement.executeQuery()) {

                while (results.next()) {
                    int categoryID = results.getInt(" CategoryID");
                    String CategoryName = results.getString(" CategoryName");

                    System.out.println(categoryID + ": " + CategoryName);
                }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the category ID: ");
            int categoryId = scanner.nextInt();

            displayProductsInCategory(connection, categoryId);

        }

        } catch (Exception ex) {
            System.out.println("Show me run time error");
        }
    }

    private static void displayProductsInCategory(Connection connection, int categoryId) {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products WHERE CategoryID = ?";
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(query)) {
            preparedStatement.setInt(1, categoryId);
            try (ResultSet results = preparedStatement.executeQuery()) {

                System.out.println("\n Product select by category:");

                while (results.next()) {
                    int productId = results.getInt("ProductID");
                    String productName = results.getString("ProductName");
                    double unitPrice = results.getDouble("UnitPrice");
                    int unitsInStock = results.getInt("UnitsInStock");

                    System.out.println("Product ID: " + productId);
                    System.out.println("Product Name: " + productName);
                    System.out.println("Unit Price: " + unitPrice);
                    System.out.println("Units In Stock: " + unitsInStock);
                    System.out.println("-----------------------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

