package org.example;

public class Products {
    private int productId;
    private String itemName;
    private String productType;
    private double atSaleCost;


    // --- Constructor used for CREATION (No ID needed) ---
    public Products(String itemName, String productType, double atSaleCost) {
        this.productId = 0; // Or -1, to signify it hasn't been set yet
        this.itemName = itemName;
        this.productType = productType;
        this.atSaleCost = atSaleCost;
    }

    // --- NEW Constructor used for RETRIEVAL (ID is read from DB) ---
    public Products(int productId, String itemName, String productType, double atSaleCost) {
        this.productId = productId;
        this.itemName = itemName;
        this.productType = productType;
        this.atSaleCost = atSaleCost;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() { return this.productId; }

    public String getItemName() {
        return this.itemName;
    }

    public String getProductType() {
        return this.productType;
    }

    public double getAtSaleCost() { return this.atSaleCost; }



    public String toString() {
        return "Product [productId=" + this.productId +
                ", itemName=" + this.itemName +
                ", productType=" + this.productType +
                ", atSaleCost= $" + this.atSaleCost +
                "]";
    }

}
