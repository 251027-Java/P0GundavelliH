package org.example;

import java.util.Date;

public class OrderItems {
    private int orderItemId;
    private int co_id;
    private int productId;
    private double priceAtSale;


    public OrderItems(int orderItemId, int co_id, int productId, double priceAtSale) {
        this.orderItemId = orderItemId;
        this.co_id = co_id;
        this.productId = productId;
        this.priceAtSale = priceAtSale;
    }

    public int getOrderItemId() {
        return this.orderItemId;
    }

    public int getCo_Id() {
        return this.co_id;
    }

    public int getProductId() {return this.productId; }

    public double getPriceAtSale() {
        return this.priceAtSale;
    }


    public String toString() {
        return "CustomerOrder [orderItemId=" + this.orderItemId + ", co_id=" + this.co_id + ", productId=" + this.productId + ", priceAtSale=" + this.priceAtSale  + "]";
    }
}
