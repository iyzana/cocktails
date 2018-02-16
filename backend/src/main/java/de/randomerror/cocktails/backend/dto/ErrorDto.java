package de.randomerror.cocktails.backend.dto;

import lombok.Data;

@Data
public class ErrorDto {
    private final String error;
    private final String message;
}
