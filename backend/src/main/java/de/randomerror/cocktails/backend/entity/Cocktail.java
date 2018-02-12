package de.randomerror.cocktails.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Cocktail {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Input> inputs;

    public Cocktail(String name, List<Input> inputs) {
        this.name = name;
        this.inputs = inputs;
    }
}
