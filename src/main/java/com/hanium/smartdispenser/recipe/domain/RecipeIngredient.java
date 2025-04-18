package com.hanium.smartdispenser.recipe.domain;

import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

    @Id @GeneratedValue
    @Column(name = "recipe_ingredient_id")
    private Long id;
    private int remaining;
    private int capacity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

}
