package com.hanium.smartdispenser.ingredient.domain;

import com.hanium.smartdispenser.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ingredient")
public class Ingredient extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ingredient_id")
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private IngredientType type;

    public static Ingredient of(String name, IngredientType type) {
        Ingredient ingredient = new Ingredient();
        ingredient.name = name;
        ingredient.type = type;
        return ingredient;
    }
}
