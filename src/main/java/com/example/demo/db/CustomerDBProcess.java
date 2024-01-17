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

    private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM CUSTOMER";

    private static final String DELETE_CUSTOMER_DATA = "DELETE FROM CUSTOMER WHERE custId = ?" ;

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

    // Customer Delete

    public boolean deleteCustomer(String cusId, Connection connection) {
        System.out.println("DB-deleteCustomer");
        try {
            var ps = connection.prepareStatement(DELETE_CUSTOMER_DATA);
            ps.setString(1, cusId);

            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Customer Update
    private static final String UPDATE_CUSTOMER_DATA = "UPDATE CUSTOMER SET custName=?, custMobile=?, custAddress=? WHERE custId=?";

    public void updateCustomer(CustomerDTO customer, Connection connection) {
        try {
            var ps = connection.prepareStatement(UPDATE_CUSTOMER_DATA);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getMobile());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getId());

            if (ps.executeUpdate() != 0) {
                System.out.println("Data updated");
            } else {
                System.out.println("Failed to update");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
