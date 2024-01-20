package com.example.demo.db;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.PlaceOrderDTO;
import lombok.var;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderDBProcess {
    private static final String SAVE_ORDERS_DATA = "INSERT INTO CUSTOMER (orderId,orderDate,custId,itemCode,total) VALUES (?,?,?,?,?)";

    // Customer Save
    public void saveOrders(PlaceOrderDTO placeOrderDTO, Connection connection){
        try {
            var ps = connection.prepareStatement(SAVE_ORDERS_DATA);
            ps.setString(1,placeOrderDTO.getOrderId());
            ps.setString(2,placeOrderDTO.getOrderDate());
            ps.setString(3,placeOrderDTO.getCustomerId());
            ps.setString(4,placeOrderDTO.getItemCode());
            ps.setDouble(5,placeOrderDTO.getTotal());

            if (ps.executeUpdate() != 0) {
                System.out.println("Data saved");
                System.out.println(placeOrderDTO.getTotal());
            } else {
                System.out.println("Failed to save");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
