package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Store {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        // Load inventory from the data file (pipe-delimited: SKU|Product Name|Price|Department)
        loadInventory("products.csv", inventory);

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }


    public static void loadInventory(String fileName, ArrayList<Product> inventory) {


         try{
             BufferedReader reader = new BufferedReader(new FileReader(fileName));
             reader.readLine();//Skips the header since "The top is all Strings"
             String line;

             while ((line = reader.readLine()) != null){
                 String[] parts = line.split("\\|");
                 String id = parts[0];
                 String item = parts[1];
                 double price = Double.parseDouble(parts[2]);
                 String department = parts[3];
                 inventory.add(new Product(id, item, price, department));
             }
             reader.close();

         }catch(IOException e){
             System.err.println("Something went wrong " + e.getMessage());
         }
    }


    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {
        for (Product product : inventory) {
            System.out.println(product);
        }

        System.out.print("Enter product ID to add to cart or (X) to return: ");
        String id = scanner.nextLine();
        for (Product product : inventory) {
            if (product.id().equals(id)){
                cart.add(product);
                System.out.println(product.item() + " was added to your cart!");
                product.addQuantity();

            }
            else if(id.equalsIgnoreCase("X")){
                System.out.println("You are being sent back to the home page.");
                return;
            }
        }
    }


    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {

        double subtotal = 0;

        // Display products and calculate subtotal
        for (Product product : cart) {
            System.out.println(product + " | x" + product.quantity());
            subtotal += product.price() * product.quantity();


        // Calculate tax and total
        double tax = subtotal * 0.07;
        double totalAmount = subtotal + tax;

        System.out.printf("Subtotal: $%.2f%n", subtotal);
        System.out.printf("Tax: $%.2f%n", tax);
        System.out.printf("Total: $%.2f%n", totalAmount);

        // Ask the user to pick to check out or return home
        System.out.print("Enter (C) to check out or (X) to return to homepage: ");
        String pick = scanner.nextLine();

        if (pick.equalsIgnoreCase("C")) {
            checkOut(cart, totalAmount, scanner);
            return;

        } else if (pick.equalsIgnoreCase("X")) {
            System.out.println("You are being sent back to the home page.");
            return;
        }
    }
}


    public static void checkOut(ArrayList<Product> cart, double totalAmount, Scanner scanner) {

        System.out.printf("Your total is: $%.2f%n", totalAmount);
        System.out.print("Confirm purchase? (Y/N): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            System.out.printf("Total: $%.2f%n", totalAmount);
            cart.clear();
            System.out.println("Your cart has been cleared. Thank you for your purchase!");

        } else if (confirm.equalsIgnoreCase("N")) {
            System.out.println("Okay take your time.");


        } else {
            System.out.println("Invalid choice.");
        }
    }


    public static Product findProductById(String id, ArrayList<Product> inventory) {
        for (Product product : inventory){
            if (product.id(). equalsIgnoreCase(id)){
                return product;
            }
        }
        return null;
    }
}
