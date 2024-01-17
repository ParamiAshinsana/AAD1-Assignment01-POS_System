package com.example.demo.dto;

import lombok.*;

/**
 * @author Parami Ashinsana
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class ItemDTO {
    private String icode;
    private String iname;
    private Double iprice;
    private int iqty;
}
