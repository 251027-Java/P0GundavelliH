package org.example;

public class CustomerProfiles {
    private int customerId;
    private String name;
    private String email;
    


    // --- Constructor used for CREATION (No ID needed) ---
    public CustomerProfiles(String name, String email) {
        this.customerId = 0; // Or -1, to signify it hasn't been set yet
        this.name = name;
        this.email = email;

    }

    // --- NEW Constructor used for RETRIEVAL (ID is read from DB) ---
    public CustomerProfiles(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() { return this.customerId; }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }





    public String toString() {
        return "Product [customerId=" + this.customerId +
                ", name=" + this.name +
                ", email=" + this.email  +
                "]";
    }

}
