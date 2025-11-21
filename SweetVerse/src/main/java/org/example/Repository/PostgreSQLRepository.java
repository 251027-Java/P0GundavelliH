package org.example.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLRepository {
    private static final String Postgre_URL = "jdbc:postgresql://localhost:5432/sweetverse_db";
    private static final String Postgre_User = "postgres";
    private static final String Postgre_PW = "my_secure_pass";
    private Connection connection;

    public PostgreSQLRepository() {
        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sweetverse_db", "postgres", "my_secure_pass");
            this.createSchema();

            // Create tables without dependencies first
            this.createProductsTable();
//            this.createCustomerProfilesTable();
//
//            // Create tables that depend on the ones above
            this.createInventoryTable();
//            this.createStockAlertTable();
//            this.createPurchaseOrderTable();
//            this.createCustomerOrdersTable();

            this.createCustomerProfilesTable();
            this.createCustomerOrdersTable();

            this.createOrderItemsTable();
//
//            // Create the final line-item tables
//            this.createOrderItemsTable();
//            this.createPurchaseOrderItemTable();
            System.out.println("Database setup complete!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        return this.connection;
    }

    private void createSchema() throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute("CREATE SCHEMA IF NOT EXISTS sv;");
        }

    }

    private void createProductsTable() throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS sv.Products (" +
                    "productId SERIAL PRIMARY KEY," +
                    "itemName VARCHAR(100) NOT NULL," +
                    "productType VARCHAR(100) NOT NULL," +
                    "atSaleCost FLOAT NOT NULL);";
            stmt.execute(sql);
        }
    }

    private void createInventoryTable() throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS sv.Inventory (" +
                    "inventoryId SERIAL PRIMARY KEY," +
                    "productId INT NOT NULL," + // Foreign key to Product
                    "quantityInStock INT NOT NULL," +
                    "availability VARCHAR(100) NOT NULL);";
            stmt.execute(sql);
        }
    }

    private void createCustomerProfilesTable() throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS sv.CustomerProfiles (" +
                    "customerId SERIAL PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," + // Increased size for name
                    "email VARCHAR(100) NOT NULL);"; // Removed extra parentheses and orderDate
            stmt.execute(sql);
        }
    }

    private void createCustomerOrdersTable() throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS sv.CustomerOrders (" +
                    "co_id SERIAL PRIMARY KEY," + // Changed orderId to co_id
                    "customerId INT NOT NULL," +
                    "orderDetails VARCHAR(100) NOT NULL," +
                    "orderDate TIMESTAMP NOT NULL," +
                    "totalCost FLOAT NOT NULL," +
                    "status VARCHAR(100) NOT NULL);";
            stmt.execute(sql);
        }
    }




    private void createOrderItemsTable() throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS sv.OrderItems (" +
                    "itemId SERIAL PRIMARY KEY," +
                    "co_id INT NOT NULL," + // Foreign key to CustomerOrders
                    "productId INT NOT NULL," + // Foreign key to Product
                    "sellingPrice FLOAT NOT NULL);";
            stmt.execute(sql);
        }
    }








    private void createStockAlertTable() throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS sv.StockAlert (" +
                    "alert_id SERIAL PRIMARY KEY," +
                    "productId INT NOT NULL," + // Foreign key to Product
                    "timestamp TIMESTAMP NOT NULL," +
                    "is_resolved VARCHAR(10) NOT NULL);"; // Using VARCHAR or BOOLEAN is fine
            stmt.execute(sql);
        }
    }

    private void createPurchaseOrderTable() throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS sv.PurchaseOrder (" +
                    "po_id SERIAL PRIMARY KEY," +
                    "vendor_name VARCHAR(50) NOT NULL," +
                    "total_order_cost FLOAT NOT NULL," +
                    "orderDate TIMESTAMP NOT NULL," +
                    "status VARCHAR(20) NOT NULL);";
            stmt.execute(sql);
        }
    }

    private void createPurchaseOrderItemTable() throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS sv.PurchaseOrderItem (" +
                    "po_item_id SERIAL PRIMARY KEY," +
                    "po_id INT NOT NULL," + // Foreign key to PurchaseOrder
                    "productId INT NOT NULL," + // Foreign key to Product
                    "quantity_received INT NOT NULL," +
                    "purchase_cost FLOAT NOT NULL);";
            stmt.execute(sql);
        }
    }
}
