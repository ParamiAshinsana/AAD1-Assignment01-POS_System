package com.example.demo.db;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomerDBProcess {
    private static final String SAVE_CUSTOMER_DATA = "INSERT INTO CUSTOMER (custId, custName, custMobile, custAddress) VALUES (?,?,?,?)";


    // Customer Save
    public void saveCustomer(CustomerDTO customers, Connection connection){
        try {
            var ps = connection.prepareStatement(SAVE_ITEM_DATA);
            ps.setString(1, items.getCode());
            ps.setString(2, items.getDescription());
            ps.setDouble(3, items.getUnitPrice());
            ps.setInt(4, items.getQty());

            if (ps.executeUpdate() != 0) {
                System.out.println("Data saved");
            } else {
                System.out.println("Failed to save");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
