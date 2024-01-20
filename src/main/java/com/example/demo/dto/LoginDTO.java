package com.example.demo.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class LoginDTO {
    private String userNameQ;
    private String passwordQ;
}

