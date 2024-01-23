package com.example.demo.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class PlaceOrderDTO {
    private String orderId;
    private String orderDate;
    private String customerId;
    private String itemCode;
    private Double itemUnitPrice;
    private int itemQty;
    private Double total; // Calculated total for the item
}
