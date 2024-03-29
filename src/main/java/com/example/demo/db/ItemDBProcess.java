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

    private static final String DELETE_ITEM_DATA = "DELETE FROM ITEM WHERE itemCode = ?" ;

    private static final String UPDATE_ITEM_DATA = "UPDATE ITEM SET itemName=?, itemPrice=?, itemQuantity=? WHERE itemCode=?";

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

    // Item Delete
    public boolean deleteItem(String itemCode, Connection connection) {
        System.out.println("DB-deleteItem");
        try {
            var ps = connection.prepareStatement(DELETE_ITEM_DATA);
            ps.setString(1, itemCode);

            return ps.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Item Update
    public void updateItem(ItemDTO item, Connection connection) {
        System.out.println("Hello updateItem");
        try {
            var ps = connection.prepareStatement(UPDATE_ITEM_DATA);
            ps.setString(1, item.getIname());
            ps.setDouble(2, item.getIprice());
            ps.setInt(3, item.getIqty());
            ps.setString(4, item.getIcode());

            if (ps.executeUpdate() != 0) {
                System.out.println("Data updated");
            } else {
                System.out.println("Failed to update");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    // Get All Item Codes
    public List<String> getAllItemCodes(Connection connection) {
        List<String> itemCodes = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ITEMS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                itemCodes.add(resultSet.getString("itemCode"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemCodes;
    }
}
