package com.example.demo.db;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.LoginDTO;
import lombok.var;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDBProcess {
    private static final String SAVE_LOGIN_DATA = "INSERT INTO LOGIN (UserName,Password) VALUES (?,?)";

    private static final String SELECT_ALL_LOGINS_DETAILS = "SELECT * FROM LOGIN";

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

    public List<LoginDTO> getAllLoginDetails(Connection connection) {
        List<LoginDTO> loginDTOS = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LOGINS_DETAILS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                LoginDTO loginDTO = new LoginDTO(
                        resultSet.getString("UserName"),
                        resultSet.getString("Password")
                );
                loginDTOS.add(loginDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loginDTOS;
    }
}
