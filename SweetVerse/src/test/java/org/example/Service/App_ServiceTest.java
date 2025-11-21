package org.example.Service;

import org.example.CustomerOrders;
import org.example.CustomerProfiles;
import org.example.Products;
import org.example.Repository.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

// Mockito imports
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class App_ServiceTest {

    // MOCKS
    @Mock private Products_Repository productsRepo;
    @Mock private CustomerProfiles_Repository cProfileRepo;
    @Mock private Inventory_Repository inventoryRepo;
    @Mock private CustomerOrders_Repository COrderRepo;
    @Mock private OrderItems_Repository OItemsRepo;

    // INJECTED SERVICE
    @InjectMocks
    private App_Service appService;

    //
    // BASIC DELEGATION TESTS (3)
    //

    /** Test Case 1 (Happy Path): createNewProduct */
    @Test
    void testCreateNewProduct_DelegatesToRepo() {
        int expectedId = 5;
        when(productsRepo.create(any(Products.class))).thenReturn(expectedId);

        int productId = appService.createNewProduct("Cocoa", "flavor", 5.50);

        assertEquals(expectedId, productId);
        verify(productsRepo, times(1)).create(any(Products.class));
        verifyNoMoreInteractions(productsRepo);
    }

    /** Test Case 2 (Happy Path): getProduct */
    @Test
    void testGetProduct_DelegatesToRepoAndReturnsProduct() {
        int testProductId = 15;
        Products mockProduct = new Products(testProductId, "TestItem", "TestType", 12.34);

        when(productsRepo.get(testProductId)).thenReturn(mockProduct);

        Products result = appService.getProduct(testProductId);

        assertEquals(mockProduct, result);
        verify(productsRepo, times(1)).get(testProductId);
    }

    /** Test Case 3 (Happy Path): createNewCustomerProfile */
    @Test
    void testCreateNewCustomerProfile_DelegatesToRepo() {
        String name = "New Customer";
        String email = "test@newcustomer.com";
        int expectedId = 99;

        when(cProfileRepo.create(any(CustomerProfiles.class))).thenReturn(expectedId);

        int customerId = appService.createNewCustomerProfile(name, email);

        assertEquals(expectedId, customerId);

        ArgumentCaptor<CustomerProfiles> profileCaptor = ArgumentCaptor.forClass(CustomerProfiles.class);
        verify(cProfileRepo, times(1)).create(profileCaptor.capture());

        assertEquals(name, profileCaptor.getValue().getName());
        assertEquals(email, profileCaptor.getValue().getEmail());
    }

    //
    // CORE BUSINESS LOGIC TESTS (7)
    //

    /** Test Case 4 (Happy Path): Full order transaction for a NEW customer. */
    @Test
    void testHandleCustomerOrder_NewCustomerCreationFlow() throws SQLException {
        // ARRANGE
        String email = "transaction@test.com";
        double expectedTotal = 0.50 + 6.00 + 0.75;

        when(cProfileRepo.getCustomerIdByEmail(email)).thenReturn(-1);
        when(cProfileRepo.create(any(CustomerProfiles.class))).thenReturn(200);

        when(productsRepo.findAtSaleCostByItemName(anyString())).thenReturn(0.50).thenReturn(6.00).thenReturn(0.75);
        when(productsRepo.findProductIdByItemName(anyString())).thenReturn(1).thenReturn(3).thenReturn(6);
        when(productsRepo.findProductTypeByProductId(anyInt())).thenReturn("entree").thenReturn("flavor").thenReturn("topping");
        when(COrderRepo.create(any(CustomerOrders.class))).thenReturn(50);
        when(OItemsRepo.create(any())).thenReturn(1);

        // ACT
        appService.handleCustomerOrder("New User", email, "Bowl", "Vanilla", "Oreo pieces");

        // ASSERT
        verify(cProfileRepo, times(1)).create(any(CustomerProfiles.class));

        ArgumentCaptor<CustomerOrders> orderCaptor = ArgumentCaptor.forClass(CustomerOrders.class);
        verify(COrderRepo, times(1)).create(orderCaptor.capture());
        assertEquals(200, orderCaptor.getValue().getCustomerId());
        assertEquals(expectedTotal, orderCaptor.getValue().getTotalCost(), 0.001);

        verify(OItemsRepo, times(3)).create(any());
        verify(inventoryRepo, times(3)).updateInventoryStock(anyInt(), anyInt());
    }


    /** Test Case 5 (Happy Path): Full order transaction for an EXISTING customer. */
    @Test
    void testHandleCustomerOrder_ExistingCustomerReuseFlow() throws SQLException {
        // ARRANGE
        String email = "existing@test.com";
        int existingId = 100;

        when(cProfileRepo.getCustomerIdByEmail(email)).thenReturn(existingId);

        when(productsRepo.findAtSaleCostByItemName(anyString())).thenReturn(1.50).thenReturn(6.00).thenReturn(0.75);
        when(productsRepo.findProductIdByItemName(anyString())).thenReturn(2).thenReturn(4).thenReturn(7);
        when(productsRepo.findProductTypeByProductId(anyInt())).thenReturn("entree").thenReturn("flavor").thenReturn("topping");
        when(COrderRepo.create(any(CustomerOrders.class))).thenReturn(51);
        when(OItemsRepo.create(any())).thenReturn(1);

        // ACT
        appService.handleCustomerOrder("Returning User", email, "Waffle Cone", "Chocolate", "Crushed Reese's");

        // ASSERT
        verify(cProfileRepo, never()).create(any(CustomerProfiles.class));

        ArgumentCaptor<CustomerOrders> orderCaptor = ArgumentCaptor.forClass(CustomerOrders.class);
        verify(COrderRepo, times(1)).create(orderCaptor.capture());
        assertEquals(existingId, orderCaptor.getValue().getCustomerId());
    }

    /** Test Case 6 (Negative Result): Tests the scenario where one item name returns cost 0.0. */
    @Test
    void testHandleCustomerOrder_InvalidEntree_CalculatesPartialCost() throws SQLException {
        // ARRANGE
        when(cProfileRepo.getCustomerIdByEmail(anyString())).thenReturn(10);

        when(productsRepo.findAtSaleCostByItemName("Bad Item")).thenReturn(0.00);
        when(productsRepo.findAtSaleCostByItemName("Vanilla")).thenReturn(1.00);
        when(productsRepo.findAtSaleCostByItemName("Oreo pieces")).thenReturn(0.50);
        when(productsRepo.findProductIdByItemName(anyString())).thenReturn(1).thenReturn(3).thenReturn(6);
        when(productsRepo.findProductTypeByProductId(anyInt())).thenReturn("entree").thenReturn("flavor").thenReturn("topping");

        when(COrderRepo.create(any(CustomerOrders.class))).thenReturn(52);
        when(OItemsRepo.create(any())).thenReturn(1);

        // ACT
        appService.handleCustomerOrder("User", "user@test.com", "Bad Item", "Vanilla", "Oreo pieces");

        // ASSERT
        ArgumentCaptor<CustomerOrders> orderCaptor = ArgumentCaptor.forClass(CustomerOrders.class);
        verify(COrderRepo, times(1)).create(orderCaptor.capture());

        assertEquals(1.50, orderCaptor.getValue().getTotalCost(), 0.001);
    }

    /** * Test Case 7 (Negative Result - Critical Failure): Tests DB failure during order creation. */
    @Test
    void testHandleCustomerOrder_DatabaseFailsOnOrderCreation() throws SQLException {
        // ARRANGE
        when(cProfileRepo.getCustomerIdByEmail(anyString())).thenReturn(10);

        // --- ONLY STUB METHODS CALLED BEFORE THE EXPECTED FAILURE ---
        // 1. Cost Lookups (Called)
        when(productsRepo.findAtSaleCostByItemName(anyString())).thenReturn(1.00);

        // 2. Order Creation (Expected to fail immediately)
        when(COrderRepo.create(any(CustomerOrders.class)))
                .thenThrow(new RuntimeException("Simulated DB Connection Failure"));

        // REMOVED: Stubs for findProductIdByItemName and findProductTypeByProductId
        // (because the service exits before calling them)

        // ACT & ASSERT
        assertThrows(RuntimeException.class, () -> {
            appService.handleCustomerOrder("User", "user@test.com", "Entree", "Flavor", "Topping");
        }, "The service must throw a RuntimeException upon critical failure.");

        verify(OItemsRepo, never()).create(any());
        verify(inventoryRepo, never()).updateInventoryStock(anyInt(), anyInt());
    }

    /** Test Case 8 (Negative Result): Verifies retrieval of a product that doesn't exist. */
    @Test
    void testGetProduct_NotFound_ReturnsNull() {
        // ARRANGE
        int nonExistentId = 999;
        when(productsRepo.get(nonExistentId)).thenReturn(null);

        // ACT
        Products result = appService.getProduct(nonExistentId);

        // ASSERT
        assertNull(result);
    }

    /** Test Case 9 (Happy Path): Verifies inventory item creation delegation. */
    @Test
    void testCreateNewInventoryItem_HappyPath() {
        // ARRANGE
        when(inventoryRepo.create(any())).thenReturn(25);

        // ACT
        int inventoryId = appService.createNewInventoryItem(1, 50, "available");

        // ASSERT
        assertEquals(25, inventoryId);
        verify(inventoryRepo, times(1)).create(any());
    }

    /** * Test Case 10 (Negative Result - Edge Case): Tests validation for negative cost.
     */
    @Test
    void testCreateNewProduct_NegativeCost_ThrowsException() {
        // ACT & ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            appService.createNewProduct("Invalid Item", "entree", -0.01);
        }, "Creating a product with a negative cost must throw an IllegalArgumentException.");

        // Verify the repository was never called because validation failed first.
        verify(productsRepo, never()).create(any());
    }
}