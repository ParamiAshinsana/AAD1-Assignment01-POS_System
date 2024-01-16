package com.example.demo.db;

import com.example.demo.dto.CustomerDTO;
import lombok.var;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Parami Ashinsana
 */
public class CustomerDBProcess {
    private static final String SAVE_CUSTOMER_DATA = "INSERT INTO CUSTOMER (custId,custName,custMobile,custAddress) VALUES (?,?,?,?)";

    private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer";

    // Customer Save
    public void saveCustomer(CustomerDTO customers, Connection connection){
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER_DATA);
            ps.setString(1,customers.getId());
            ps.setString(2,customers.getName());
            ps.setString(3,customers.getMobile());
            ps.setString(4,customers.getAddress());

            if (ps.executeUpdate() != 0) {
                System.out.println("Data saved");
                System.out.println(customers.getMobile());
            } else {
                System.out.println("Failed to save");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Get All Customers
    public List<CustomerDTO> getAllCustomer(Connection connection) {
        List<CustomerDTO> customerDTOS = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                CustomerDTO customerDTO = new CustomerDTO(
                        resultSet.getString("custId"),
                        resultSet.getString("custName"),
                        resultSet.getString("custMobile"),
                        resultSet.getString("custAddress")
                );
                customerDTOS.add(customerDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerDTOS;
    }

}