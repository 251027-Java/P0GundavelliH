package org.example;

public class Inventory {
    private int inventoryId;
    private int productId;
    private int quantityInStock;
    private String availability;


    // --- Constructor used for CREATION (No ID needed) ---
    public Inventory(int productId, int quantityInStock, String availability) {
        this.inventoryId = 0; // Or -1, to signify it hasn't been set yet
        this.productId = productId;
        this.quantityInStock = quantityInStock;
        this.availability = availability;
    }

    // --- NEW Constructor used for RETRIEVAL (ID is read from DB) ---
    public Inventory(int inventoryId , int productId, int quantityInStock, String availability) {
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.quantityInStock = quantityInStock;
        this.availability = availability;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getInventoryId() { return this.inventoryId; }

    public int getproductId() {
        return this.productId;
    }

    public int getquantityInStock() {
        return this.quantityInStock;
    }

    public String getavailability() { return this.availability; }



    public String toString() {
        return "Product [inventoryId=" + this.productId +
                ", productId=" + this.productId +
                ", quantityInStock=" + this.quantityInStock +
                ", availability= $" + this.availability +
                "]";
    }

}
