package de.randomerror.cocktails.backend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CreateCocktailDto {
    private String name;
    private Map<Long, Double> inputs;
}
