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
    private String customerId;
    private String itemCode;
    private String itemName;
    private Double total; // Calculated total for the item
}
