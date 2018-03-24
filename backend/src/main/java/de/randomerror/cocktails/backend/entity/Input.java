package de.randomerror.cocktails.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
public class Input {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Ingredient ingredient;
    private double amount;

    public Input(Ingredient ingredient, double amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }
}
