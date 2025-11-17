//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.Service;

import java.util.Date;
import org.example.CustomerOrders;
import org.example.Repository.IRepository;

public class CustomerOrders_Service {
    private IRepository<CustomerOrders, Integer> repo;

    public CustomerOrders_Service(IRepository<CustomerOrders, Integer> repository) {
        this.repo = repository;
    }

    public CustomerOrders createNewOrder(int orderId, int customerId, double totalCost, String status) {
        if (this.repo.get(orderId) != null) {
            return null;
        } else {
            CustomerOrders newOrder = new CustomerOrders(orderId, customerId, new Date(), totalCost, status);
            this.repo.create(newOrder);
            return newOrder;
        }
    }

    public CustomerOrders getOrder(int orderId) {
        return (CustomerOrders)this.repo.get(orderId);
    }
}
