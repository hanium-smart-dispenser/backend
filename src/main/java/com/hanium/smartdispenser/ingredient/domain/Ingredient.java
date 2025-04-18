package com.hanium.smartdispenser.ingredient.domain;

import com.hanium.smartdispenser.recipe.domain.RecipeIngredient;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ingredient")
public class Ingredient {

    @Id @GeneratedValue
    @Column(name = "ingredient_id")
    private Long id;
    private String name;
}
