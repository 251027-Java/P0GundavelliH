package org.example.Repository;

import org.example.CustomerOrders;
import org.example.OrderItems;

import java.sql.*;
import java.util.List;

public class OrderItems_Repository extends PostgreSQLRepository implements IRepository<OrderItems, Integer> {


    @Override
    public int create(OrderItems item) {
        String sql = "INSERT INTO sv.OrderItems (co_id, productId, sellingPrice) VALUES ( ?, ?, ?);";
        int generatedItemId = -1; // Default value for failure

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // 1. Set the parameters for the non-ID fields
            stmt.setInt(1, item.getCo_Id());
            stmt.setInt(2, item.getProductId());
            stmt.setDouble(3,item.getSellingPrice());



            // 2. Execute the update
            int affectedRows = stmt.executeUpdate();

            // Check if the insertion was successful
            if (affectedRows > 0) {

                // 3. Get the ResultSet containing the generated keys
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        // 4. Extract the ID from the first column
                        generatedItemId = rs.getInt(1);
                        System.out.println("Order item created successfully! New ID: " + generatedItemId);
                    }
                }
            } else {
                System.err.println("Order item insertion failed, no rows affected.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the generated ID (or -1 if the insertion failed)
        return generatedItemId;
    }

    @Override
    public OrderItems get(Integer itemId) {
        String sql = "SELECT itemId, co_id, productId, sellingPrice FROM sv.OrderItems WHERE itemId = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, itemId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // 1. READ ALL FOUR FIELDS
                    int id = rs.getInt("itemId"); // Read the ID
                    int co_id = rs.getInt("co_id"); // Read the ID
                    int productId = rs.getInt("productId"); // Read the ID
                    double sellingPrice = rs.getDouble("sellingPrice");

                    // 2. USE THE NEW 4-ARGUMENT CONSTRUCTOR
                    return new OrderItems(id, co_id, productId, sellingPrice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(OrderItems entity) {

    }

    @Override
    public void delete(Integer entity) {

    }

    @Override
    public List<OrderItems> findAll() {
        return List.of();
    }

    @Override
    public void saveAll(List<OrderItems> entities) {

    }
}
