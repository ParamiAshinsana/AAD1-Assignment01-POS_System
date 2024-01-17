package com.example.demo.db;

import com.example.demo.dto.ItemDTO;
import lombok.var;

import java.sql.Connection;
import java.sql.SQLException;

public class ItemDBProcess {
    private static final String SAVE_ITEM_DATA = "INSERT INTO ITEM (itemCode,itemName,itemPrice,itemQuantity) VALUES (?,?,?,?)";

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



}
