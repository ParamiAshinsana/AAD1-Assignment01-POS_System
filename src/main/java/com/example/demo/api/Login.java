package com.example.demo.api;

import com.example.demo.db.CustomerDBProcess;
import com.example.demo.db.LoginDBProcess;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

@WebServlet(name = "login", urlPatterns = "/login",
        initParams = {
                @WebInitParam(name = "db-user", value = "root"),
                @WebInitParam(name = "db-pw", value = "1234"),
                @WebInitParam(name = "db-url", value = "jdbc:mysql://localhost:3306/vtex?createDatabaseIfNotExist=true"),
                @WebInitParam(name = "db-class", value = "com.mysql.cj.jdbc.Driver"),
        },
        loadOnStartup = 10
)
public class Login extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Hello doGet");
        Jsonb jsonb = JsonbBuilder.create();
        LoginDTO loginDTO;

        LoginDBProcess loginDBProcess = new LoginDBProcess();

        List<LoginDTO> logins = loginDBProcess.getAllLoginDetails(connection);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objMapper = new ObjectMapper();
        String objRslt = objMapper.writeValueAsString(logins);

        resp.getWriter().write(objRslt);
    }

}
