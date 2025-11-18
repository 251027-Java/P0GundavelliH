package org.example;

import java.util.Date;

public class CustomerProfiles {
    private int customerId;
    private String name;
    private String email;


    public CustomerProfiles(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String toString() {
        return "CustomerProfile [customerId=" + this.customerId + ", name=" + this.name + ", email=" + this.email + "]";
    }
}
