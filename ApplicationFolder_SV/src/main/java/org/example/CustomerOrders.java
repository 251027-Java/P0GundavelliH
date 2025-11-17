//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.util.Date;

public class CustomerOrders {
    private int orderId;
    private int customerId;
    private Date orderDate;
    private double totalCost;
    private String status;

    public CustomerOrders(int orderId, int customerId, Date orderDate, double totalCost, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.status = status;
    }

    public int getOrderId() {
        return this.orderId;
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
        int var10000 = this.orderId;
        return "CustomerOrder [orderId=" + var10000 + ", customerId=" + this.customerId + ", orderDate=" + String.valueOf(this.orderDate) + ", totalCost=" + this.totalCost + ", status=" + this.status + "]";
    }
}
