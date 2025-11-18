package org.example;

import java.util.Date;

public class StockAlert {
    private int alert_id; // Purchase Order ID
    private int productId;
    private Date timeStamp;
    private String is_resolved;

    // Constructor should initialize the Purchase Order fields
    public StockAlert(int alert_id, int productId, Date timeStamp, String is_resolved) {
        this.alert_id = alert_id;
        this.productId = productId;
        this.timeStamp = timeStamp;
        this.is_resolved = is_resolved;
    }

    // --- Getters for Stock Alert fields ---
    public int getAlertId() {
        return this.alert_id;
    }

    public int getProductId() {
        return this.productId;
    }

    public Date getTimeStamp() {
        return this.timeStamp;
    }

    public String getIsResolved() {
        return this.is_resolved;
    }

    // --- Setters (Example: to resolve the alert) ---
    public void setIsResolved(String is_resolved) {
        this.is_resolved = is_resolved;
    }

    // --- toString() method for Stock Alert ---
    @Override
    public String toString() {
        return "StockAlert [alert_id=" + this.alert_id +
                ", productId=" + this.productId +
                ", timeStamp=" + this.timeStamp +
                ", is_resolved=" + this.is_resolved +
                "]";
    }
}
