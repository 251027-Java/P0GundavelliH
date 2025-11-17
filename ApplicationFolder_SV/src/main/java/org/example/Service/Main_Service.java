package org.example.Service;

public class Main_Service {

    public static void placeOrder(String name, String email, String entree1, String toppings1, String entree2, String toppings2) {
        //use email to find customerId in CustomerProfiles
        //if email not found -> create new CustomerProfile & return new customerId

        //after retrieving customerId enter order details
        //take entree1 - generate entry for that
        //take topping1 - generate entry for that
        //ask if you want to add a second entrée
        //take entree2 - generate entry for that
        //take topping2 - generate entry for that

        //create order entry for that customer's order

        //return order is taken, wait when ready
        //do some wait time to simulate order getting prepared

    }

    public static void retrieveOrderItems(int entree1Id, int topping1Id, int entree2Id, int topping2Id) {
        //look for each itemid in ShopInventory
        //based on quantity selected for order, deduct that from shop inventory
    }

    public static boolean ifItemsEmpty(int itemid) {
        //checks if item in shop inventory is empty
        //when retrieveOrderItems for quantity selected for the order, if item is empty or is not enough
        // -> return true else false
        //-> output sorry we are out or there is not enough
        //-> call restockIventory
        return false;
    }

    public static void restockInventory(int itemid) {
        //use itemid to place new storeOrder for that item
        //say item has been order and will be coming soon
    }


}
