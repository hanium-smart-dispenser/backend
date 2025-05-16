package com.hanium.smartdispenser.ingredient.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ingredient")
public class Ingredient {

    @Id @GeneratedValue
    @Column(name = "ingredient_id")
    private Long id;
    private String name;


    public static Ingredient of(String name) {
        Ingredient ingredient = new Ingredient();
        ingredient.name = name;
        return ingredient;
    }
}
