package com.hanium.smartdispenser.recipe.domain;

import com.hanium.smartdispenser.common.domain.BaseEntity;
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
public class Recipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User createdBy;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredientList = new ArrayList<>();

    /**
     * Recipe에 RecipeIngredient를 등록하고 양방향 연관관계 설정합니다.
     */
    public void addIngredient(Ingredient ingredient, int amount) {
        RecipeIngredient ri = RecipeIngredient.of(this, ingredient, amount);
        recipeIngredientList.add(ri);
    }

    public static Recipe of(String name, User user) {
        Recipe recipe = new Recipe();
        recipe.name = name;
        recipe.createdBy = user;
        return recipe;
    }

}
