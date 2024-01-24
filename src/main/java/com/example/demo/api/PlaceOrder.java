package com.example.demo.api;

import com.example.demo.db.CustomerDBProcess;
import com.example.demo.db.ItemDBProcess;
import com.example.demo.db.PlaceOrderDBProcess;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.PlaceOrderDTO;
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
import java.util.Date;
import java.util.List;

@WebServlet(name = "placeOrder", urlPatterns = "/placeOrder",
        initParams = {
                @WebInitParam(name = "db-user", value = "root"),
                @WebInitParam(name = "db-pw", value = "1234"),
                @WebInitParam(name = "db-url", value = "jdbc:mysql://localhost:3306/vtex?createDatabaseIfNotExist=true"),
                @WebInitParam(name = "db-class", value = "com.mysql.cj.jdbc.Driver"),
        },
        loadOnStartup = 10
)
public class PlaceOrder extends HttpServlet{

    Connection connection;
    @Override
    public void init() throws ServletException {
        System.out.println("hello Init-placeOrder");
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
    // To Save the Order
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Hello doPost-Place Order");
        if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Jsonb jsonb = JsonbBuilder.create();

            PlaceOrderDTO placeOrderDTO;
            placeOrderDTO = jsonb.fromJson(req.getReader(), PlaceOrderDTO.class);

            // Convert the orderDate string to Date
            Date orderDate = placeOrderDTO.getOrderDateAsDate();
            placeOrderDTO.setOrderDate(String.valueOf(orderDate));

            PlaceOrderDBProcess placeOrderDBProcess = new PlaceOrderDBProcess();
            placeOrderDBProcess.saveOrders(placeOrderDTO, connection);
        }
    }

    // To get all item codes and customers ids
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Hello doGet for Item Codes");
        Jsonb jsonb = JsonbBuilder.create();

        ItemDBProcess itemDBProcess = new ItemDBProcess();

        List<String> itemCodes = itemDBProcess.getAllItemCodes(connection);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objMapper = new ObjectMapper();

        String objRslt1 = objMapper.writeValueAsString(itemCodes);

        resp.getWriter().write(objRslt1);

        // customer

//        CustomerDBProcess customerDBProcess = new CustomerDBProcess();
//        List<String> customersIds = customerDBProcess.getAllCustomerIds(connection);
//
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
//
//        String objRslt2 = objMapper.writeValueAsString(customersIds);
//
//        resp.getWriter().write(objRslt2);

        PlaceOrderDBProcess placeOrderDBProcess = new PlaceOrderDBProcess();
        List<PlaceOrderDTO> placeOrderDTOS = placeOrderDBProcess.getAllOrders(connection);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objMapperOr = new ObjectMapper();
        String objRslts = objMapperOr.writeValueAsString(placeOrderDTOS);

        resp.getWriter().write(objRslts);

    }
}
