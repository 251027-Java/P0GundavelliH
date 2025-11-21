package org.example;

public class Product {
    private int productId;
    private String itemName;
    private double sellingPrice;
    private double unitCost;
    private String category;


    public Product(int productId, String itemName, double sellingPrice, double unitCost, String category) {
        this.productId = productId;
        this.itemName = itemName;
        this.sellingPrice = sellingPrice;
        this.unitCost = unitCost;
        this.category = category;
    }

    public int getProductId() { return this.productId; }

    public String getItemName() {
        return this.itemName;
    }

    public double getSellingPrice() {
        return this.sellingPrice;
    }

    public double getUnitCost() {
        return this.unitCost;
    }

    public String getCategory() {
        return this.category;
    }




    public String toString() {
        return "Product [productId=" + this.productId +
                ", itemName=" + this.itemName +
                ", sellingPrice=" + this.sellingPrice +
                ", unitCost=" + this.unitCost +
                ", category=" + this.category +
                "]";
    }
}
