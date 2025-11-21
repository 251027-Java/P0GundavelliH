package org.example;

import java.util.Date;

public class CustomerOrders {
    private int co_id;
    private int customerId;
    private String orderDetails;
    private Date orderDate;
    private double totalCost;
    private String status;



    // --- Constructor used for CREATION (No ID needed) ---
    public CustomerOrders(int customerId, String orderDetails, Date orderDate, double totalCost, String status) {
        this.co_id = 0; // Or -1, to signify it hasn't been set yet
        this.customerId = customerId;
        this.orderDetails = orderDetails;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.status = status;

    }

    // --- NEW Constructor used for RETRIEVAL (ID is read from DB) ---
    public CustomerOrders(int co_id,int customerId, String orderDetails, Date orderDate, double totalCost, String status) {
        this.co_id = co_id;
        this.customerId = customerId;
        this.orderDetails = orderDetails;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.status = status;
    }



    public int getCo_Id() {
        return co_id;
    }

    public void setCo_Id(int co_id) {
        this.co_id = co_id;
    }

    public int getCustomerId() { return this.customerId; }

    public String getOrderDetails() {
        return orderDetails;
    }



    public Date getOrderDate() {
        return orderDate;
    }



    public double getTotalCost() {
        return totalCost;
    }



    public String getStatus() {
        return status;
    }




}
