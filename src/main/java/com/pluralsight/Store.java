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

    /**
     * Displays all products and lets the user add one to the cart.
     * Typing X returns to the main menu.
     */
    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {
        for (Product product : inventory) {
            System.out.println(product);
        }

        System.out.print("Enter product ID to add to cart (X or x to return): ");
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

    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        for (Product product : cart) {
            System.out.println(product + " | x" + product.quantity());

            System.out.print("Enter (C or c) to check out or (X or x) to return back to the homepage: ");
            String pick = scanner.nextLine();

            if (pick.equalsIgnoreCase("C")) {
                double subtotal = product.price() * product.quantity();
                double tax = subtotal * 0.07;
                double totalAmount = subtotal + tax;
                System.out.printf("Your total is: $%.2f%n", totalAmount);
            }
            else if(pick.equalsIgnoreCase("X")){
                System.out.println("You are being sent back to the home page.");
                return;
            }
        }
    }

    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {
        // TODO: implement steps listed above
    }

    /**
     * Searches a list for a product by its id.
     *
     * @return the matching Product, or null if not found
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // TODO: loop over the list and compare ids
        return null;
    }
}
