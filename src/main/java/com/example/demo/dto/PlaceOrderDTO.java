package com.example.demo.dto;

import lombok.*;

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
    private String customerName;
    private String itemCode;
    private String itemName;
    private Double unitPrice;
    private Integer quantity;
    private Double total; // Calculated total for the item
    private Double cash;
    private Double discount;
    private Double balance;
}
