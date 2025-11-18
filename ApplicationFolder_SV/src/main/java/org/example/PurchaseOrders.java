package org.example;

import java.util.Date;

public class PurchaseOrders {
    private int po_id; // Purchase Order ID
    private String vendorName;
    private double totalOrderCost;
    private Date orderDate;
    private String status;

    // Constructor should initialize the Purchase Order fields
    public PurchaseOrders(int po_id, String vendorName, double totalOrderCost, Date orderDate, String status) {
        this.po_id = po_id;
        this.vendorName = vendorName;
        this.totalOrderCost = totalOrderCost;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters for Purchase Order fields
    public int getPoId() {
        return this.po_id;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public double getTotalOrderCost() {
        return this.totalOrderCost;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public String getStatus() {
        return this.status;
    }

    // Setters (often useful, but optional)
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PurchaseOrder [po_id=" + this.po_id +
                ", vendorName=" + this.vendorName +
                ", totalOrderCost=" + this.totalOrderCost +
                ", orderDate=" + this.orderDate +
                ", status=" + this.status +
                "]";
    }
}
