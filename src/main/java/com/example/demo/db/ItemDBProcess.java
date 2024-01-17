package com.example.demo.db;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.ItemDTO;
import lombok.var;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDBProcess {
    private static final String SAVE_ITEM_DATA = "INSERT INTO ITEM (itemCode,itemName,itemPrice,itemQuantity) VALUES (?,?,?,?)";

    private static final String SELECT_ALL_ITEMS = "SELECT * FROM ITEM";
    // ITEM Save
    public void saveItem(ItemDTO items, Connection connection){
        try {
            var ps = connection.prepareStatement(SAVE_ITEM_DATA);
            ps.setString(1,items.getIcode());
            ps.setString(2,items.getIname());
            ps.setDouble(3,items.getIprice());
            ps.setInt(4,items.getIqty());

            if (ps.executeUpdate() != 0) {
                System.out.println("Data saved");
                System.out.println(items.getIprice());
            } else {
                System.out.println("Failed to save");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Get All Items
    public List<ItemDTO> getAllItems(Connection connection) {
        List<ItemDTO> itemsDTO = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ITEMS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ItemDTO itemDTO = new ItemDTO(
                        resultSet.getString("itemCode"),
                        resultSet.getString("itemName"),
                        resultSet.getDouble("itemPrice"),
                        resultSet.getInt("itemQuantity")
                );
                itemsDTO.add(itemDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemsDTO;
    }

}
