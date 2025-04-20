package com.hanium.smartdispenser.recipe.domain;

import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import com.hanium.smartdispenser.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recipe")
public class Recipe {

    @Id @GeneratedValue
    @Column(name = "recipe_id")
    private Long id;
    private String name;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = LAZY)
    private User createdBy;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> recipeIngredientList = new ArrayList<>();

    /**
     * Recipe에 RecipeIngredient를 등록하고 양방향 연관관계 설정합니다.
     */
    public void addIngredient(Ingredient ingredient, int amount) {
        RecipeIngredient ri = RecipeIngredient.of(this, ingredient, amount);
        recipeIngredientList.add(ri);
    }

}
