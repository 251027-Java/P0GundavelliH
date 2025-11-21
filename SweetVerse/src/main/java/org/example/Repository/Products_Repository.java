package org.example.Repository;

import org.example.Products;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import java.util.List;

public class Products_Repository extends PostgreSQLRepository implements IRepository<Products, Integer> {

    public String findProductTypeByProductId (int productId) {
        String sql = "SELECT productType FROM sv.Products WHERE productId = ?";
        String productType = " ";
        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // 1. READ ALL FOUR FIELDS

                    String type = rs.getString("productType");

                    // 2. USE THE NEW 4-ARGUMENT CONSTRUCTOR
                    productType = type;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productType;
    }

    public int findProductIdByItemName(String name) {
        String sql = "SELECT productId FROM sv.Products WHERE itemName = ?";
        int productId = 0;
        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // 1. READ ALL FOUR FIELDS

                    int id = rs.getInt("productId");

                    // 2. USE THE NEW 4-ARGUMENT CONSTRUCTOR
                    productId = id;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productId;

    }

    public double findAtSaleCostByItemName(String name) {
        String sql = "SELECT atSaleCost FROM sv.Products WHERE itemName = ?";
        double cost = 0.0;
        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // 1. READ ALL FOUR FIELDS

                    double atSaleCost = rs.getDouble("atSaleCost");

                    // 2. USE THE NEW 4-ARGUMENT CONSTRUCTOR
                    cost = atSaleCost;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cost;
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM sv.Products"; // Use 'sv.Inventory' for the Inventory repo
        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                // Returns the number of rows in the table
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public void resetSequence() throws SQLException {
        // NOTE: Replace 'sv.products_productid_seq' with the actual sequence name if it's different
        String sql = "ALTER SEQUENCE sv.Products_productId_seq RESTART WITH 1";

        try (Statement stmt = this.getConnection().createStatement()) {
            stmt.execute(sql);
        }
    }

    public int create(Products product) {
        // We expect the new ID back, so change return type to 'int'

        String sql = "INSERT INTO sv.Products (itemName, productType, atSaleCost) VALUES ( ?, ?, ?);";
        int generatedProductId = -1; // Default value for failure

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // 1. Set the parameters for the non-ID fields
            stmt.setString(1, product.getItemName());
            stmt.setString(2, product.getProductType());
            stmt.setDouble(3, product.getAtSaleCost());


            // 2. Execute the update
            int affectedRows = stmt.executeUpdate();

            // Check if the insertion was successful
            if (affectedRows > 0) {

                // 3. Get the ResultSet containing the generated keys
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        // 4. Extract the ID from the first column
                        generatedProductId = rs.getInt(1);
                        //System.out.println("Product created successfully! New ID: " + generatedProductId);
                    }
                }
            } else {
                System.err.println("Product insertion failed, no rows affected.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the generated ID (or -1 if the insertion failed)
        return generatedProductId;
    }

    // Inside Products_Repository

    @Override
    public Products get(Integer productId) {
        String sql = "SELECT productId, itemName, productType, atSaleCost FROM sv.Products WHERE productId = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // 1. READ ALL FOUR FIELDS
                    int id = rs.getInt("productId"); // Read the ID
                    String itemName = rs.getString("itemName");
                    String productType = rs.getString("productType");
                    double atSaleCost = rs.getDouble("atSaleCost");

                    // 2. USE THE NEW 4-ARGUMENT CONSTRUCTOR
                    return new Products(id, itemName, productType, atSaleCost);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Products entity) {

    }

    @Override
    public void delete(Integer entity) {

    }


    @Override
    public List<Products> findAll() {
        return List.of();
    }

    @Override
    public void saveAll(List<Products> entities) {

    }
}
