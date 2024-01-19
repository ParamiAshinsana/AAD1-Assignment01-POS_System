package com.example.demo.db;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.LoginDTO;
import lombok.var;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginDBProcess {
    private static final String SAVE_LOGIN_DATA = "INSERT INTO LOGIN (UserName,Password) VALUES (?,?)";

    public void saveLogin(LoginDTO logins, Connection connection){
        try {
            var ps = connection.prepareStatement(SAVE_LOGIN_DATA);
            ps.setString(1,logins.getUserNameQ());
            ps.setString(2,logins.getPasswordQ());

            if (ps.executeUpdate() != 0) {
                System.out.println("Login Data saved");
                System.out.println(logins.getUserNameQ());
            } else {
                System.out.println("Failed to save");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
