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
public class CustomerDTO {
    private String code;
    private String name;
    private int mobile;
    private String address;
}
