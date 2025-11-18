package org.example.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.example.CustomerOrders;

public class CustomerOrders_Repository extends PostgreSQLRepository implements IRepository<CustomerOrders, Integer> {
    public void create(CustomerOrders entity) {
        String sql = "INSERT INTO sv.CustomerOrders (orderId, customerId, orderDate, totalCost, status) VALUES (?, ?, ?, ?, ?);";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, entity.getCo_Id());
            stmt.setInt(2, entity.getCustomerId());
            stmt.setTimestamp(3, new Timestamp(entity.getOrderDate().getTime()));
            stmt.setDouble(4, entity.getTotalCost());
            stmt.setString(5, entity.getStatus());
            stmt.executeUpdate();
            System.out.println("CustomerOrder created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public CustomerOrders get(Integer entity) {
        String sql = "SELECT orderId, customerId, orderDate, totalCost, status FROM sv.CustomerOrders WHERE orderId = ?";

        try {
            CustomerOrders var11;
            try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
                stmt.setInt(1, entity);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }

                    int id = rs.getInt("orderId");
                    int customerId = rs.getInt("customerId");
                    Date orderDate = new Date(rs.getTimestamp("orderDate").getTime());
                    double totalCost = rs.getDouble("totalCost");
                    String status = rs.getString("status");
                    var11 = new CustomerOrders(id, customerId, orderDate, totalCost, status);
                }
            }

            return var11;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(CustomerOrders entity) {
    }

    public void delete(Integer integer) {
    }

    public List<CustomerOrders> findAll() {
        return List.of();
    }

    public void saveAll(List<CustomerOrders> entities) {
    }
}
