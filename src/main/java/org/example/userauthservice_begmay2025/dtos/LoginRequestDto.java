package org.example.userauthservice_begmay2025.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    private String email;

    private String password;
}
