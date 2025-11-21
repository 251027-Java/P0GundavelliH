package org.example.Repository;

import org.example.CustomerOrders;
import org.example.CustomerProfiles;
import org.example.Products;

import java.sql.*;
import java.util.List;

public class CustomerOrders_Repository extends PostgreSQLRepository implements IRepository<CustomerOrders, Integer> {

    @Override
    public int create(CustomerOrders order) {
        String sql = "INSERT INTO sv.CustomerOrders (customerId, orderDetails, orderDate, totalCost, status) VALUES ( ?, ?, ?, ?, ?);";
        int generatedCo_Id = -1; // Default value for failure

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // 1. Set the parameters for the non-ID fields
            stmt.setInt(1, order.getCustomerId());
            stmt.setString(2, order.getOrderDetails());
            // Corrected: Use new java.sql.Timestamp() and setTimestamp()
            stmt.setTimestamp(3, new java.sql.Timestamp(order.getOrderDate().getTime()));
            stmt.setDouble(4,order.getTotalCost());
            stmt.setString(5, order.getStatus());


            // 2. Execute the update
            int affectedRows = stmt.executeUpdate();

            // Check if the insertion was successful
            if (affectedRows > 0) {

                // 3. Get the ResultSet containing the generated keys
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        // 4. Extract the ID from the first column
                        generatedCo_Id = rs.getInt(1);
                        System.out.println("Order item created successfully! New ID: " + generatedCo_Id);
                    }
                }
            } else {
                System.err.println("Order item insertion failed, no rows affected.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the generated ID (or -1 if the insertion failed)
        return generatedCo_Id;
    }

    @Override
    public CustomerOrders get(Integer co_id) {
        String sql = "SELECT co_id, customerId, orderDetails, orderDate, totalCost, status FROM sv.CustomerOrders WHERE co_id = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, co_id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // 1. READ ALL FOUR FIELDS
                    int id = rs.getInt("co_id"); // Read the ID
                    int customerId = rs.getInt("customerId"); // Read the ID
                    String orderDetails = rs.getString("orderDetails");
                    Date orderDate = rs.getDate("orderDate");
                    double totalCost = rs.getDouble("totalCost");
                    String status = rs.getString("status");

                    // 2. USE THE NEW 4-ARGUMENT CONSTRUCTOR
                    return new CustomerOrders(id, customerId, orderDetails, orderDate, totalCost, status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(CustomerOrders entity) {

    }

    @Override
    public void delete(Integer entity) {

    }

    @Override
    public List<CustomerOrders> findAll() {
        return List.of();
    }

    @Override
    public void saveAll(List<CustomerOrders> entities) {

    }
}
