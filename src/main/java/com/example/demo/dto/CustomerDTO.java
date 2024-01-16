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
    private String id;
    private String name;
    private String mobile;
    private String address;
}
