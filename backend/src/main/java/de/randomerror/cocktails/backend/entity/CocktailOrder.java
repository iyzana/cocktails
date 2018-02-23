package de.randomerror.cocktails.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@NamedQuery(name = "CocktailOrder.findByRequester", query = "select o from CocktailOrder o where o.requester = :requester")
public class CocktailOrder {
    @Id
    @GeneratedValue
    private long id;

    private String requester;
    private String cocktailName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Input> request;

    public CocktailOrder(String requester, Cocktail cocktail) {
        this(requester, cocktail.getName(), cocktail.getInputs());
    }

    public CocktailOrder(String requester, String cocktailName, List<Input> request) {
        this.requester = requester;
        this.cocktailName = cocktailName;
        this.request = request;
    }
}
