package org.example.Repository;

import org.example.Inventory;
import org.example.Products;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Inventory_Repository extends PostgreSQLRepository implements IRepository<Inventory, Integer> {

    public void updateInventoryStock(int pId, int Quantity) throws SQLException {

        // The SQL UPDATE statement
        String sql = "UPDATE sv.Inventory SET quantityInStock = quantityInStock - ? WHERE productId = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {

            // 1. Set the new stock value
            stmt.setInt(1, Quantity);

            // 2. Set the ID to target the correct row
            stmt.setInt(2, pId);

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Warning: Inventory ID " + pId + " not found for update.");
            } else {
                System.out.println("Inventory ID " + pId + " updated successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM sv.Inventory"; // Use 'sv.Inventory' for the Inventory repo
        try (Statement stmt = this.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                // Returns the number of rows in the table
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public void resetSequenceToNine() throws SQLException {
        String sql = "ALTER SEQUENCE sv.Inventory_inventoryId_seq RESTART WITH 9";

        try (Statement stmt = this.getConnection().createStatement()) {
            stmt.execute(sql);
        }
    }


    @Override
    public int create(Inventory item) {
        String sql = "INSERT INTO sv.Inventory (productId, quantityInStock, availability) VALUES ( ?, ?, ?);";
        int generatedInventoryId = -1; // Default value for failure

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // 1. Set the parameters for the non-ID fields
            stmt.setInt(1, item.getproductId());
            stmt.setInt(2, item.getquantityInStock());
            stmt.setString(3, item.getavailability());


            // 2. Execute the update
            int affectedRows = stmt.executeUpdate();

            // Check if the insertion was successful
            if (affectedRows > 0) {

                // 3. Get the ResultSet containing the generated keys
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        // 4. Extract the ID from the first column
                        generatedInventoryId = rs.getInt(1);
                        System.out.println("Inventory item created successfully! New ID: " + generatedInventoryId);
                    }
                }
            } else {
                System.err.println("Inventory item insertion failed, no rows affected.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the generated ID (or -1 if the insertion failed)
        return generatedInventoryId;
    }

    @Override
    public Inventory get(Integer inventoryId) {
        String sql = "SELECT inventoryId, productId, quantityInStock, availability FROM sv.Inventory WHERE inventoryId = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, inventoryId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // 1. READ ALL FOUR FIELDS
                    int id = rs.getInt("inventoryId"); // Read the ID
                    int productId = rs.getInt("productId");
                    int quantityInStock = rs.getInt("quantityInStock");
                    String availability = rs.getString("availability");

                    // 2. USE THE NEW 4-ARGUMENT CONSTRUCTOR
                    return new Inventory(id, productId, quantityInStock, availability);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Inventory entity) {

    }

    @Override
    public void delete(Integer entity) {

    }

    @Override
    public List<Inventory> findAll() {
        return List.of();
    }

    @Override
    public void saveAll(List<Inventory> entities) {

    }
}
