package com.example.demo.dto;

import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import jakarta.json.bind.annotation.JsonbDateFormat;

import jakarta.json.bind.annotation.JsonbProperty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class PlaceOrderDTO {
    private String orderId;

    @JsonbProperty("orderDate")
    private String orderDate; // Change the type to String

    private String customerId;
    private String itemCode;
    private Double itemUnitPrice;
    private int itemQty;
    private Double total; // Calculated total for the item

    // Add a method to convert the string to Date
    public Date getOrderDateAsDate() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(orderDate);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing orderDate", e);
        }
    }
}
