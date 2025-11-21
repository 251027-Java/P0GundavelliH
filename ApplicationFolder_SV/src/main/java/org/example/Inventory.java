package org.example;

import java.util.Date;

public class Inventory {
    private int inventoryId;
    private int productId;
    private int quantityInStock;
    private Date lastUpdated;


    public Inventory(int inventoryId, int productId, int quantityInStock, Date lastUpdated) {
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.quantityInStock = quantityInStock;
        this.lastUpdated = lastUpdated;
    }

    public int getInventoryId() {
        return this.inventoryId;
    }

    public int getProductId() {
        return this.productId;
    }

    public int getQuantityInStock() {
        return this.quantityInStock;
    }

    public Date getLastUpdated() { return this.lastUpdated;}

    public String toString() {
        return "CustomerProfile [inventoryId=" + this.inventoryId + ", productId=" + this.productId + ", quantityInStock=" + this.quantityInStock + ", lastUpdated=" + this.lastUpdated + "]";
    }
}
