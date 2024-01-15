package com.example.demo.db;

import com.example.demo.dto.CustomerDTO;
import lombok.var;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Parami Ashinsana
 */
public class CustomerDBProcess {
    private static final String SAVE_CUSTOMER_DATA = "INSERT INTO CUSTOMER (custId, custName, custMobile, custAddress) VALUES (?,?,?,?)";


    // Customer Save
    public void saveCustomer(CustomerDTO customers, Connection connection){
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER_DATA);
            ps.setString(1, customers.getId());
            ps.setString(2, customers.getName());
            ps.setInt(3, customers.getMobile());
            ps.setString(4, customers.getAddress());

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
