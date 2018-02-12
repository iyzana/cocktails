package de.randomerror.cocktails.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue
    private long id;
    private String name;
}
