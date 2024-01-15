package com.example.demo.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.var;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "customer", urlPatterns = "/customer",
        initParams = {
                @WebInitParam(name = "db-user", value = "root"),
                @WebInitParam(name = "db-pw", value = "1234"),
                @WebInitParam(name = "db-url", value = "jdbc:mysql://localhost:3306/vtex?createDatabaseIfNotExist=true"),
                @WebInitParam(name = "db-class", value = "com.mysql.cj.jdbc.Driver"),
        },
        loadOnStartup = 10
)
public class Customer extends HttpServlet {
        Connection connection;
        @Override
        public void init() throws ServletException {
                System.out.println("hello Init");
                try {
                        var user = getServletConfig().getInitParameter("db-user");
                        var password = getServletConfig().getInitParameter("db-pw");
                        var url = getServletConfig().getInitParameter("db-url");

                        Class.forName(getServletConfig().getInitParameter("db-class"));
                        this.connection = DriverManager.getConnection(url, user, password);

                } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                }
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                System.out.println("Hello Dopost");
                if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                } else {
                        System.out.println("itemDTO.getCode()");
                        Jsonb jsonb = JsonbBuilder.create();
                        var itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
                        System.out.println(itemDTO.getCode());
                        var dbProcess = new DBProcess();
                        dbProcess.saveItemOne(itemDTO, connection);

                }
        }
}
