package org.example;

import java.util.Date;

public class OrderItems {
    private int itemId;
    private int co_id;
    private int productId;
    private double sellingPrice;



    // --- Constructor used for CREATION (No ID needed) ---
    public OrderItems(int co_id, int productId, double sellingPrice) {
        this.itemId = 0;
        this.co_id = co_id;
        this.productId = productId;
        this.sellingPrice = sellingPrice;


    }

    // --- NEW Constructor used for RETRIEVAL (ID is read from DB) ---
    public OrderItems(int itemId, int co_id, int productId, double sellingPrice) {
        this.itemId = itemId;
        this.co_id = co_id;
        this.productId = productId;
        this.sellingPrice = sellingPrice;

    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCo_Id() {
        return co_id;
    }

    public int getProductId() {
        return productId;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }





}
