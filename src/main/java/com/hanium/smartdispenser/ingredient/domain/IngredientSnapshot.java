package com.hanium.smartdispenser.ingredient.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.hanium.smartdispenser.recipe.domain.Recipe;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ingredient_snapshot")
public class IngredientSnapshot {

    @Id @GeneratedValue
    @Column(name = "ingredient_snapshot_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private JsonNode payload;

    public static IngredientSnapshot of(Recipe recipe, JsonNode payload) {
        IngredientSnapshot ingredientSnapshot = new IngredientSnapshot();
        ingredientSnapshot.recipe = recipe;
        ingredientSnapshot.payload = payload;
        return ingredientSnapshot;
    }
}
