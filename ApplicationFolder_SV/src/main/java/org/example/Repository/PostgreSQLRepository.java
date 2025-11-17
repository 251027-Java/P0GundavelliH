//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
            this.createCustomerOrdersTable();
            System.out.println("Database setup complete!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected Connection getConnection() {
        return this.connection;
    }

    private void createSchema() throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute("CREATE SCHEMA IF NOT EXISTS sv;");
        }

    }

    private void createCustomerOrdersTable() throws SQLException {
        try (Statement stmt = this.connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS sv.CustomerOrders (orderId SERIAL PRIMARY KEY,customerId INT NOT NULL,orderDate TIMESTAMP NOT NULL,totalCost FLOAT NOT NULL,status VARCHAR(20) NOT NULL);";
            stmt.execute(sql);
        }

    }
}
