package org.example;

import java.util.Date;

public class CustomerOrders {
    private int co_id;
    private int customerId;
    private Date orderDate;
    private double totalCost;
    private String status;

    public CustomerOrders(int co_id, int customerId, Date orderDate, double totalCost, String status) {
        this.co_id = co_id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.status = status;
    }

    public int getCo_Id() {
        return this.co_id;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    public String getStatus() {
        return this.status;
    }

    public String toString() {
        return "CustomerOrder [co_id=" + this.co_id + ", customerId=" + this.customerId + ", orderDate=" + String.valueOf(this.orderDate) + ", totalCost=" + this.totalCost + ", status=" + this.status + "]";
    }
}
