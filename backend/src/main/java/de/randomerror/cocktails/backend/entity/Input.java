package de.randomerror.cocktails.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Input {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Ingredient ingredient;
    private double amount;

    public Input(Ingredient ingredient, double amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }
}
