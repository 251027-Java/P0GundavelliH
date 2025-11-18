package org.example;

import org.example.Repository.CustomerOrders_Repository;
import org.example.Repository.IRepository;
import org.example.Service.CustomerOrders_Service;
import org.example.Service.*;

import java.util.Scanner;

public class Main {
    // 1. Create the Scanner as a private field (instance variable)
    private final Scanner scanner = new Scanner(System.in);

    // Instantiate your Service Layer here as well
    private final Main_Service mainService = new Main_Service();

    public static void main(String[] args) {
        // 2. The main method just creates an instance of Main and starts the app
        Main app = new Main();
        app.startApp();
    }

    public void startApp() {
        // ... application loop and logic ...
        //displayUpdatedMenu
    }

    public void handleCustomerOrder() {
        // 3. Any method in Main can now use the scanner object
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Customer Email: ");
        String email = scanner.nextLine();
        System.out.print("Please enter the Entrée Type (e.g., Ice Cream Cone (2 Scoops), Brownies (2)):");
        String entreeName = scanner.nextLine();

        // Define which items require a flavor choice
        boolean requiresFlavor = entreeName.equalsIgnoreCase("Ice Cream Cone (2 Scoops)") ||
                entreeName.equalsIgnoreCase("Ice Cream Bowl (2 Scoops)") ||
                entreeName.equalsIgnoreCase("Milkshake (2 Scoops)");

        String flavorName = null;

        if (requiresFlavor) {

            System.out.print("Enter Ice Cream Flavor (e.g., Vanilla, Chocolate): ");
            flavorName = scanner.nextLine();
        } else {
            // If it's a baked good (Brownie, Cookie), the flavor is null/not needed
            flavorName = "N/A";
        }

        System.out.print("Please enter the Topping Type (e.g., Oreo Pieces, Hot Fudge):");
        String ToppingName = scanner.nextLine();

        //call placeCustomerOrder(String name, String email, String entreeName, String flavorName, String ToppingName)

    }
}
