package com.example.demo.api;

import com.example.demo.db.CustomerDBProcess;
import com.example.demo.db.ItemDBProcess;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.ItemDTO;
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

@WebServlet(name = "item", urlPatterns = "/item",
        initParams = {
                @WebInitParam(name = "db-user", value = "root"),
                @WebInitParam(name = "db-pw", value = "1234"),
                @WebInitParam(name = "db-url", value = "jdbc:mysql://localhost:3306/vtex?createDatabaseIfNotExist=true"),
                @WebInitParam(name = "db-class", value = "com.mysql.cj.jdbc.Driver"),
        },
        loadOnStartup = 10
)
public class Item extends HttpServlet {
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

    // To Save the Item
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Hello doPost");
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Jsonb jsonb = JsonbBuilder.create();

            ItemDTO itemDTO ;
            itemDTO = jsonb.fromJson(req.getReader() , ItemDTO.class);

            ItemDBProcess itemDBProcess = new ItemDBProcess();
            itemDBProcess.saveItem(itemDTO , connection);
        }
    }

    // To get all the Items
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Hello doGet item");
        Jsonb jsonb = JsonbBuilder.create();

        ItemDTO itemDTO;

        ItemDBProcess itemDBProcess = new ItemDBProcess();

        List<ItemDTO> items = itemDBProcess.getAllItems(connection);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objMapper = new ObjectMapper();

        String objRslt = objMapper.writeValueAsString(items);

        resp.getWriter().write(objRslt);
    }

    // To Delete the Item
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Hello doDelete");

        String itemCode = req.getParameter("icode");

        if (itemCode != null && !itemCode.isEmpty()) {
            ItemDBProcess itemDBProcess = new ItemDBProcess();

            boolean deleted = itemDBProcess.deleteItem(itemCode, connection);

            if (deleted) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Item deleted successfully");
                System.out.println("Item deleted successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Item not found or unable to delete");
                System.out.println("Item not found or unable to delete");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid request parameters");
            System.out.println("Invalid request parameters");
        }
    }

    // To Update the Item
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Hello doPut");

        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Jsonb jsonb = JsonbBuilder.create();

            ItemDTO itemDTO ;
            itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);

            ItemDBProcess itemDBProcess = new ItemDBProcess();
            itemDBProcess.updateItem(itemDTO, connection);
        }
    }

}
