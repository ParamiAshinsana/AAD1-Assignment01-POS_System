package com.example.demo.db;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.PlaceOrderDTO;
import lombok.var;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaceOrderDBProcess {
    private static final String SAVE_ORDERS_DATA = "INSERT INTO PLACEORDER (orderId,orderDate,custId,itemCode,unitPrice,orQty,total) VALUES (?,?,?,?,?,?,?)";

    private static final String SELECT_ALL_ORDERS = "SELECT * FROM PLACEORDER";

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

    // Get All orders
    public List<PlaceOrderDTO> getAllOrders(Connection connection) {
        List<PlaceOrderDTO> orderDTO = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                PlaceOrderDTO orders = new PlaceOrderDTO(
                        resultSet.getString("orderId"),
                        resultSet.getString("orderDate"),
                        resultSet.getString("custId"),
                        resultSet.getString("itemCode"),
                        resultSet.getDouble("unitPrice"),
                        resultSet.getInt("orQty"),
                        resultSet.getDouble("total")
                );
                orderDTO.add(orders);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderDTO;
    }
}
