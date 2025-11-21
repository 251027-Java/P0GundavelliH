package org.example;

import org.example.Repository.*;

import org.example.Service.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {


        Scanner sc = new Scanner(System.in);
        Products_Repository ProductsRepo = new Products_Repository();
        Inventory_Repository InventoryRepo = new Inventory_Repository();
        CustomerProfiles_Repository CProfileRepo = new CustomerProfiles_Repository();
        CustomerOrders_Repository COrderRepo = new CustomerOrders_Repository();
        OrderItems_Repository OItemsRepo = new OrderItems_Repository();

        App_Service appService = new App_Service(ProductsRepo, InventoryRepo, CProfileRepo, COrderRepo, OItemsRepo);

        appService.initializeTables();


        // Declare the loop control variable outside the loop
        String continueOrdering = "";

        // --- OUTER DO-WHILE LOOP STARTS HERE ---
        do {
            // Display Menu (Your Existing Inner Loop) ---
            String readyToOrder = "";
            do {
                appService.displayMenu();
                System.out.println("\nAre you ready to order? (yes/no): ");
                readyToOrder = sc.nextLine();
            } while (readyToOrder.equalsIgnoreCase("no"));


            // --- ORDER PLACEMENT (The Transaction) ---

            System.out.println("\n--- Starting New Customer Order ---");
            System.out.println("Enter your name: ");
            String name = sc.nextLine();
            System.out.println("Enter your email: ");
            String email = sc.nextLine();
            System.out.println("Enter your entree: ");
            String entree = sc.nextLine();
            System.out.println("Enter your flavor: ");
            String flavor = sc.nextLine();
            System.out.println("Enter your topping: ");
            String topping = sc.nextLine();

            try {
                // Execute the business logic
                appService.handleCustomerOrder(name, email, entree, flavor, topping);
            } catch (SQLException e) {
                System.err.println("A critical database error occurred: " + e.getMessage());
            }

            //  PROMPT FOR NEXT ORDER ---

            System.out.println("\nWould you like to place another order? (yes/no): ");
            continueOrdering = sc.nextLine();

        } while (continueOrdering.equalsIgnoreCase("yes"));
        // The loop continues as long as the user answers "yes"

        System.out.println("Thank you for using Sweet Verse! Exiting program.");











    }
}
