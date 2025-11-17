//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import org.example.Repository.CustomerOrders_Repository;
import org.example.Repository.IRepository;
import org.example.Service.CustomerOrders_Service;

public class Main {
    public static void main(String[] args) {
        System.out.println("SweetVerse Starting...");
        IRepository<CustomerOrders, Integer> repo = new CustomerOrders_Repository();
        CustomerOrders_Service service = new CustomerOrders_Service(repo);
        System.out.println("Creating a CustomerOrder:");
        CustomerOrders order = service.createNewOrder(1, 12, (double)12.0F, "Pending");
        System.out.println(order);
        System.out.println("SweetVerse Closing...");
    }
}
