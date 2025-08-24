package com.hanium.smartdispenser.recipe.domain;

import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_ingredient_id")
    private Long id;
    private int amount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    public static RecipeIngredient of(Recipe recipe, Ingredient ingredient, int amount) {
        RecipeIngredient ri = new RecipeIngredient();
        ri.recipe = recipe;
        ri.ingredient = ingredient;
        ri.amount = amount;
        return ri;
    }

}
