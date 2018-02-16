package de.randomerror.cocktails.backend.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String name;
    private String password;
}
