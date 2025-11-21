package org.example.Repository;

import org.example.CustomerProfiles;
import org.example.Inventory;
import org.example.Products;

import java.sql.*;
import java.util.List;

public class CustomerProfiles_Repository extends PostgreSQLRepository implements IRepository<CustomerProfiles, Integer> {

    public int getCustomerIdByEmail(String email) throws SQLException {
        // Return value of -1 indicates the customer was NOT found.
        int customerId = -1;

        // SQL: Select the customerId where the email matches the parameter
        String sql = "SELECT customerId FROM sv.CustomerProfiles WHERE email = ?";

        // Use try-with-resources to ensure PreparedStatement and Connection close
        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {

            // 1. Set the email parameter
            stmt.setString(1, email);

            // 2. Execute the query
            try (ResultSet rs = stmt.executeQuery()) {

                // 3. Check if a row was returned
                if (rs.next()) {
                    // Read the customerId from the result set
                    customerId = rs.getInt("customerId");
                }
            }
        } catch (SQLException e) {
            // Log the error and re-throw the exception for the Service layer to handle
            System.err.println("Database error looking up customer by email: " + e.getMessage());
            e.printStackTrace();
            throw e; // Recommended: Re-throw the exception
            //return -1;
        }

        // Return -1 if not found, or the actual ID if found
        return customerId;
    }

    @Override
    public int create(CustomerProfiles profile) {
        String sql = "INSERT INTO sv.CustomerProfiles (name, email) VALUES ( ?, ?);";
        int generatedProfileId = -1; // Default value for failure

        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // 1. Set the parameters for the non-ID fields
            stmt.setString(1, profile.getName());
            stmt.setString(2, profile.getEmail());


            // 2. Execute the update
            int affectedRows = stmt.executeUpdate();

            // Check if the insertion was successful
            if (affectedRows > 0) {

                // 3. Get the ResultSet containing the generated keys
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        // 4. Extract the ID from the first column
                        generatedProfileId = rs.getInt(1);
                        System.out.println("Profile created successfully! New ID: " + generatedProfileId);
                    }
                }
            } else {
                System.err.println("Profile insertion failed, no rows affected.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the generated ID (or -1 if the insertion failed)
        return generatedProfileId;
    }

    @Override
    public CustomerProfiles get(Integer profileId) {
        String sql = "SELECT customerId, name, email FROM sv.CustomerProfiles WHERE customerId = ?";

        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, profileId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // 1. READ ALL FOUR FIELDS
                    int id = rs.getInt("customerId");
                    String name = rs.getString("name");
                    String email = rs.getString("email");

                    // 2. USE THE NEW 4-ARGUMENT CONSTRUCTOR
                    return new CustomerProfiles(id, name, email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(CustomerProfiles entity) {

    }

    @Override
    public void delete(Integer entity) {

    }

    @Override
    public List<CustomerProfiles> findAll() {
        return List.of();
    }

    @Override
    public void saveAll(List<CustomerProfiles> entities) {

    }
}
