package com.example.demo.db;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.PlaceOrderDTO;
import lombok.var;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PlaceOrderDBProcess {
    private static final String SAVE_ORDERS_DATA = "INSERT INTO PLACEORDER (orderId,orderDate,custId,itemCode,unitPrice,orQty,total) VALUES (?,?,?,?,?,?,?)";

    // Customer Save
    public void saveOrders(PlaceOrderDTO placeOrderDTO, Connection connection){
        try {
            var ps = connection.prepareStatement(SAVE_ORDERS_DATA);
            ps.setString(1,placeOrderDTO.getOrderId());
            // Convert the orderDate string to Date
            Date orderDate = convertStringToDate(placeOrderDTO.getOrderDate());
            ps.setDate(2, orderDate);

            ps.setString(3,placeOrderDTO.getCustomerId());
            ps.setString(4,placeOrderDTO.getItemCode());
            ps.setDouble(5,placeOrderDTO.getItemUnitPrice());
            ps.setInt(6,placeOrderDTO.getItemQty());
            ps.setDouble(7,placeOrderDTO.getTotal());

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
    // Helper method to convert string to Date
    private Date convertStringToDate(String dateStr) {
        try {
            return new Date(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US).parse(dateStr).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error converting date string to Date", e);
        }
    }
}
