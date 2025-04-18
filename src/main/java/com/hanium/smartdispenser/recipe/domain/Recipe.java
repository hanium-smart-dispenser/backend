package com.hanium.smartdispenser.recipe.domain;

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
    private List<RecipeIngredient> recipeIngredient = new ArrayList<>();



}
