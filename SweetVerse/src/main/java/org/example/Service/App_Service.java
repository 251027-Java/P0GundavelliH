package org.example.Service;

import org.example.CustomerProfiles;
import org.example.Inventory;
import org.example.Products;
import org.example.CustomerOrders;
import org.example.OrderItems;
import org.example.Repository.Inventory_Repository;
import org.example.Repository.Products_Repository;
import org.example.Repository.CustomerProfiles_Repository;
import org.example.Repository.CustomerOrders_Repository;
import org.example.Repository.OrderItems_Repository;

import java.sql.SQLException;
import java.util.Date;

public class App_Service {
    // fields
    private Products_Repository ProductsRepo;
    private Inventory_Repository InventoryRepo;
    private CustomerProfiles_Repository CProfileRepo;
    private CustomerOrders_Repository COrderRepo;
    private OrderItems_Repository OItemsRepo;

    // Constructor
    public App_Service(Products_Repository ProductsRepo, Inventory_Repository InventoryRepo, CustomerProfiles_Repository CProfileRepo, CustomerOrders_Repository COrderRepo , OrderItems_Repository OItemsRepo) {

        this.ProductsRepo = ProductsRepo;
        this.InventoryRepo = InventoryRepo;
        this.CProfileRepo = CProfileRepo;
        this.COrderRepo = COrderRepo;
        this.OItemsRepo = OItemsRepo;

    }

    public int createNewProduct(String itemName, String productType, double atSaleCost){
        // FIX: Add validation for positive cost
        if (atSaleCost <= 0.0) {
            throw new IllegalArgumentException("Product sale cost must be a positive value.");
        }

        Products newProduct = new Products(itemName, productType, atSaleCost);
        int productId = ProductsRepo.create(newProduct);
        return productId;
    }

    public Products getProduct(int productId){
        return ProductsRepo.get(productId);
    }

    public int createNewInventoryItem(int productId, int quantityInStock, String availability){
        // FIX: Add validation for non-negative stock quantity
        if (quantityInStock < 0) {
            throw new IllegalArgumentException("Quantity in stock cannot be negative.");
        }

        Inventory item = new Inventory(productId, quantityInStock, availability);
        int inventoryId = InventoryRepo.create(item);
        return inventoryId;
    }

    public Inventory getInventoryItem(int inventoryId){
        return InventoryRepo.get(inventoryId);
    }

    public void initializeTables() {
        System.out.println("Initializing Products and Inventory tables with default data...");



        try {
            // --- STEP 1: Check for Existing Data ---
            // If the Products table (or any key table) has rows, assume initialization is complete.
            if (ProductsRepo.count() > 0) {
                System.out.println("Database already initialized. Skipping data insertion.");
                return; // Exit the method immediately
            }

            System.out.println("Tables are empty. Beginning initialization...");

            ProductsRepo.resetSequence();
            InventoryRepo.resetSequenceToNine();

            // Use the existing createNewProduct method to insert initial data
            int productId_1 = createNewProduct("Bowl", "entree", 0.50);
            int productId_2 = createNewProduct("Waffle Cone", "entree", 1.50);
            int productId_3 = createNewProduct("Vanilla", "flavor", 6.00);
            int productId_4 = createNewProduct("Chocolate", "flavor", 6.00);
            int productId_5 = createNewProduct("Strawberry", "flavor", 6.00);
            int productId_6 = createNewProduct("Oreo pieces", "topping", 0.75);
            int productId_7 = createNewProduct("Crushed Reese's", "topping", 0.75);
            int productId_8 = createNewProduct("Crushed M&M's", "topping", 0.75);

            createNewInventoryItem(productId_1, 20, "available");
            createNewInventoryItem(productId_2, 20, "available");
            createNewInventoryItem(productId_3, 40, "available");
            createNewInventoryItem(productId_4, 40, "available");
            createNewInventoryItem(productId_5, 40, "available");
            createNewInventoryItem(productId_6, 20, "available");
            createNewInventoryItem(productId_7, 20, "available");
            createNewInventoryItem(productId_8, 20, "available");



            System.out.println("Initial product and inventory data successfully inserted.");

        } catch (Exception e) {
            System.err.println("Error during table initialization: " + e.getMessage());

        }

    }

    public int createNewCustomerProfile(String name, String email) {
        CustomerProfiles newProfile = new CustomerProfiles(name, email);
        int customerId = CProfileRepo.create(newProfile);
        return customerId;
    }

    public void handleCustomerOrder(String name, String email, String entree, String flavor, String topping) throws SQLException {

        // --- 1. FIND OR CREATE CUSTOMER ---

        // Find if customer already has a customerId
        int customerId = CProfileRepo.getCustomerIdByEmail(email);

        if (customerId == -1)  {
            // Customer is not found -> create new profile
            customerId = createNewCustomerProfile(name, email);
            System.out.println("New Customer Profile Created!");
        } else {
            System.out.println("Returning Customer!");
        }

        // --- 2. CREATE ORDER DETAILS STRING (for display) ---

        // This line is now reachable! It correctly concatenates the item names.
        String orderDetails = entree + ", " + flavor + ", " + topping;


        double entreeCost = ProductsRepo.findAtSaleCostByItemName(entree);
        double flavorCost = ProductsRepo.findAtSaleCostByItemName(flavor);
        double toppingCost = ProductsRepo.findAtSaleCostByItemName(topping);


        double totalCost = entreeCost + flavorCost + toppingCost;
        System.out.println("Total cost: " + totalCost);

        Date orderDate = new Date();

        String status = "pending";

        //createNewCustomerOrder
        CustomerOrders newOrder = new CustomerOrders(customerId, orderDetails, orderDate, totalCost, status);

        int co_id = COrderRepo.create(newOrder);
        System.out.println("New Customer Order Placed!");

        //create the orderItems
        //have co_id, cost
        //need priorityCount, product_id

        //findProductIdbyitemName
        int entreePId = ProductsRepo.findProductIdByItemName(entree);
        int entreePN = 1;

        int flavorPId = ProductsRepo.findProductIdByItemName(flavor);
        int flavorPN = 2;

        int toppingPId = ProductsRepo.findProductIdByItemName(topping);
        int toppingPn = 3;


        //findProductTypeByProductId
        String PTypeEntree = ProductsRepo.findProductTypeByProductId(entreePId);
        String PTypeFavlor = ProductsRepo.findProductTypeByProductId(flavorPId);
        String PTypeTopping = ProductsRepo.findProductTypeByProductId(toppingPId);

        //entree order
        OrderItems entreeItem = new OrderItems(co_id, entreePId, entreeCost);
        //flavor order
        OrderItems flavorItem = new OrderItems(co_id, flavorPId, flavorCost);
        //topping order
        OrderItems toppingItem = new OrderItems(co_id, toppingPId, toppingCost);

        int entreeItemId = OItemsRepo.create(entreeItem);
        int flavorItemId = OItemsRepo.create(flavorItem);
        int toppingItemId = OItemsRepo.create(toppingItem);

        //flavor -2, topping and entree -1

        //updateEntree
        InventoryRepo.updateInventoryStock(entreePId, 2);
        //updateFlavor
        InventoryRepo.updateInventoryStock(flavorPId, 2);
        //updateTopping
        InventoryRepo.updateInventoryStock(toppingPId, 2);

    }



    public void displayMenu() {
        //productId, itemName, productType, atSaleCost
        System.out.printf("%22s", "Menu\n");
        for (int i = 1; i < 9; i++) {
            //ProductsRepo.get(i);
            Products retrievedProduct = ProductsRepo.get(i);

            String itemName = retrievedProduct.getItemName();
            String productType = retrievedProduct.getProductType();
            double atSaleCost = retrievedProduct.getAtSaleCost();


            System.out.printf("| %-7s | %-15s | $%3.2f |%n",
                    productType,
                    itemName,
                    atSaleCost
            );


        }
    }
}
