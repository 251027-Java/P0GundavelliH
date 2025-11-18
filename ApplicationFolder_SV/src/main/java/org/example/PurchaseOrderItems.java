package org.example;

import java.util.Date;

public class PurchaseOrderItems {
    private int po_item_id; // Purchase Order ID
    private int po_id;
    private int productId;
    private int quantity_received;
    private double purchase_cost;

    // Constructor should initialize the Purchase Order fields
    // Constructor initializes the item-specific fields
    public PurchaseOrderItems(int po_item_id, int po_id, int productId, int quantity_received, double purchase_cost) {
        this.po_item_id = po_item_id;
        this.po_id = po_id;
        this.productId = productId;
        this.quantity_received = quantity_received;
        this.purchase_cost = purchase_cost;
    }

    // --- Getters ---
    public int getPoItemId() {
        return this.po_item_id;
    }

    public int getPoId() {
        return this.po_id;
    }

    public int getProductId() {
        return this.productId;
    }

    public int getQuantityReceived() {
        return this.quantity_received;
    }

    public double getPurchaseCost() {
        return this.purchase_cost;
    }

    // --- toString ---
    @Override
    public String toString() {
        return "PurchaseOrderItem [po_item_id=" + this.po_item_id +
                ", po_id=" + this.po_id +
                ", productId=" + this.productId +
                ", quantity_received=" + this.quantity_received +
                ", purchase_cost=" + this.purchase_cost +
                "]";
    }

}
