package com.example.demo.api;

import com.example.demo.db.CustomerDBProcess;
import com.example.demo.dto.CustomerDTO;
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

        // To Save the Customer
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                System.out.println("Hello doPost");
                if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                } else {
                        Jsonb jsonb = JsonbBuilder.create();

                        CustomerDTO customerDTO;
                        customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

                        CustomerDBProcess customerDBProcess = new CustomerDBProcess();
                        customerDBProcess.saveCustomer(customerDTO,connection);

                }
        }

        // To Delete the Customer
        @Override
        protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                System.out.println("Hello doDelete");
                CustomerDBProcess customerDBProcess = new CustomerDBProcess();

                // Directly write the response message based on the result of deleteCustomer
                resp.getWriter().write(customerDBProcess.deleteCustomer(req.getParameter("id"), connection) ? "Customer Deleted!" : "Customer Not Found or Unable to Delete");

        }

        // To get all the Customers
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                System.out.println("Hello doDelete");
                Jsonb jsonb = JsonbBuilder.create();
                CustomerDTO customerDTO;

                CustomerDBProcess customerDBProcess = new CustomerDBProcess();

                List<CustomerDTO> customers = customerDBProcess.getAllCustomer(connection);

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                ObjectMapper objMapper = new ObjectMapper();
                String objRslt = objMapper.writeValueAsString(customers);

                resp.getWriter().write(objRslt);
        }
}
