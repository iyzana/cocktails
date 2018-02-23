package de.randomerror.cocktails.backend.dto;

import lombok.Data;

import java.util.Map;


@Data
public class CreateOrderDto {
    /**
     * Name that should be displayed to the user as the cocktails name
     */
    private String name;
    private Map<Long, Double> inputs;
}
