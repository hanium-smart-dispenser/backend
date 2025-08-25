package com.hanium.smartdispenser.history.repository;

import com.hanium.smartdispenser.history.dto.HistoryDto;
import com.hanium.smartdispenser.history.dto.HistoryResponseDto;
import com.hanium.smartdispenser.ingredient.IngredientName;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hanium.smartdispenser.dispenser.domain.QDispenser.dispenser;
import static com.hanium.smartdispenser.favorite.QFavorite.favorite;
import static com.hanium.smartdispenser.history.domain.QHistory.history;
import static com.hanium.smartdispenser.ingredient.domain.QIngredient.ingredient;
import static com.hanium.smartdispenser.recipe.domain.QRecipe.recipe;
import static com.hanium.smartdispenser.recipe.domain.QRecipeIngredient.recipeIngredient;

@Repository
public class HistoryQueryRepositoryImpl implements HistoryQueryRepository {
    public record IngredientRow(Long historyId, String ingredientName) {}

    private final JPAQueryFactory query;

    public HistoryQueryRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }

    @Override
    public Page<HistoryDto> findAllByUserIdWithPaging(Long userId, Pageable pageable) {
        List<HistoryResponseDto> rows = query.select(Projections.constructor(HistoryResponseDto.class,
                        history.id,
                        history.status,
                        history.requestedAt,
                        history.completedAt,
                        history.user.id,
                        history.dispenser.id,
                        recipe.id,
                        recipe.name,
                        favorite.id.isNotNull()
                ))
                .from(history)
                .join(history.recipe, recipe)
                .join(history.dispenser, dispenser)
                .leftJoin(favorite).on(favorite.user.id.eq(userId).and(favorite.recipe.id.eq(recipe.id)))
                .where(history.user.id.eq(userId))
                .orderBy(history.requestedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(query.select(history.id.count())
                .from(history)
                .where(history.user.id.eq(userId))
                .fetchOne()).orElse(0L);

        if (rows.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, total);
        }

        List<Long> historyIds = rows.stream().map(HistoryResponseDto::getHistoryId).toList();


        List<IngredientRow> ingRows = query.select(Projections.constructor(IngredientRow.class, history.id, ingredient.name))
                .from(history)
                .join(history.recipe, recipe)
                .join(recipeIngredient).on(recipeIngredient.recipe.eq(recipe))
                .join(recipeIngredient.ingredient, ingredient)
                .where(history.id.in(historyIds))
                .fetch();

        Map<Long, List<IngredientName>> ingMap = ingRows.stream()
                .collect(Collectors.groupingBy(
                        IngredientRow::historyId,
                        LinkedHashMap::new,
                        Collectors.mapping(rw -> new IngredientName(rw.ingredientName()), Collectors.toList())
                ));

        List<HistoryDto> content = rows.stream()
                .map(rw -> new HistoryDto(
                        rw.getHistoryId(),
                        rw.getStatus(),
                        rw.getRequestedAt(),
                        rw.getCompletedAt(),
                        rw.getUserId(),
                        rw.getDispenserId(),
                        rw.getRecipeId(),
                        rw.getRecipeName(),
                        ingMap.getOrDefault(rw.getHistoryId(), List.of()),
                        rw.isFavorite()
                )).toList();

        return new PageImpl<>(content, pageable, total);
    }
}
