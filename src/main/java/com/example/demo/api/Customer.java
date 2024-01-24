package com.example.demo.api;

import com.example.demo.db.CustomerDBProcess;
import com.example.demo.dto.CustomerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.var;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

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
        final static Logger logger = (Logger) LoggerFactory.getLogger(Customer.class);
        Connection connection;
        @Override
        public void init() throws ServletException {
                System.out.println("hello Init-customer");
                try {
                        InitialContext ctx = new InitialContext();
                        DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/pos");
                        this.connection = pool.getConnection();
                } catch (SQLException | NamingException e) {
                        throw new RuntimeException(e);
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

                String customerId = req.getParameter("id");

                if (customerId != null && !customerId.isEmpty()) {
                        CustomerDBProcess customerDBProcess = new CustomerDBProcess();

                        boolean deleted = customerDBProcess.deleteCustomer(customerId, connection);

                        if (deleted) {
                                resp.setStatus(HttpServletResponse.SC_OK);
                                resp.getWriter().write("Customer deleted successfully");
                                System.out.println("Customer deleted successfully");
                        } else {
                                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                                resp.getWriter().write("Customer not found or unable to delete");
                                System.out.println("Customer not found or unable to delete");
                        }
                } else {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().write("Invalid request parameters");
                        System.out.println("Invalid request parameters");
                }
        }

        // To get all the Customers
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                System.out.println("Hello doGet-customer");
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

        // To Update the Customer
        @Override
        protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                System.out.println("Hello doPut");

                if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                } else {
                        Jsonb jsonb = JsonbBuilder.create();

                        CustomerDTO customerDTO;
                        customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

                        CustomerDBProcess customerDBProcess = new CustomerDBProcess();
                        customerDBProcess.updateCustomer(customerDTO, connection);
                }
        }

}
