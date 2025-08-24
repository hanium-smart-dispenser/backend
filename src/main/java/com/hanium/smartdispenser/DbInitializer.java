package com.hanium.smartdispenser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanium.smartdispenser.dispenser.domain.DispenserSauce;
import com.hanium.smartdispenser.dispenser.repository.DispenserRepository;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import com.hanium.smartdispenser.favorite.Favorite;
import com.hanium.smartdispenser.favorite.FavoriteRepository;
import com.hanium.smartdispenser.history.domain.History;
import com.hanium.smartdispenser.history.repository.HistoryRepository;
import com.hanium.smartdispenser.ingredient.domain.IngredientSnapshot;
import com.hanium.smartdispenser.ingredient.repository.IngredientRepository;
import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import com.hanium.smartdispenser.ingredient.domain.IngredientType;
import com.hanium.smartdispenser.ingredient.repository.IngredientSnapshotRepository;
import com.hanium.smartdispenser.recipe.RecipeRepository;
import com.hanium.smartdispenser.recipe.domain.Recipe;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DbInitializer implements ApplicationRunner {

    private final DispenserRepository dispenserRepository;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final HistoryRepository historyRepository;
    private final FavoriteRepository favoriteRepository;
    private final IngredientSnapshotRepository ingredientSnapshotRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        // 유저 저장
        User user1 = User.of(passwordEncoder.encode("1234"), "aaa@aaa.com", UUID.randomUUID().toString());
        User user2 = User.of(passwordEncoder.encode("1234"), "bbb@bbb.com", UUID.randomUUID().toString());
        User user3 = User.of(passwordEncoder.encode("1234"), "ccc@ccc.com", UUID.randomUUID().toString());

        user1.convertGuestToUser();
        user2.convertGuestToUser();
        user3.convertGuestToUser();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // 재료 저장
        Ingredient soy = Ingredient.of("간장", IngredientType.LIQUID);
        Ingredient allulose = Ingredient.of("알룰로스", IngredientType.LIQUID);
        Ingredient dashida = Ingredient.of("다시다", IngredientType.POWDER);
        Ingredient redPepper = Ingredient.of("고춧가루", IngredientType.POWDER);
        Ingredient gochujang = Ingredient.of("고추장 분말", IngredientType.POWDER);

        ingredientRepository.save(soy);
        ingredientRepository.save(allulose);
        ingredientRepository.save(dashida);
        ingredientRepository.save(redPepper);
        ingredientRepository.save(gochujang);

        // 디스펜서 저장
        Dispenser d1 = Dispenser.of(DispenserStatus.READY, user1, UUID.randomUUID().toString());
        Dispenser d2 = Dispenser.of(DispenserStatus.READY, user2, UUID.randomUUID().toString());
        Dispenser d3 = Dispenser.of(DispenserStatus.READY, user3, UUID.randomUUID().toString());

        dispenserRepository.save(d1);
        dispenserRepository.save(d2);
        dispenserRepository.save(d3);

        d1.assignUser(user1);
        d1.addSauce(DispenserSauce.of(1, soy));
        d1.addSauce(DispenserSauce.of(2, allulose));
        d1.addSauce(DispenserSauce.of(3, dashida));
        d1.addSauce(DispenserSauce.of(4, redPepper));
        d1.addSauce(DispenserSauce.of(5, gochujang));

        // 레시피 저장
        Recipe recipe1 = Recipe.of("testRecipe1", user1);
        recipe1.addIngredient(soy, 10);
        recipe1.addIngredient(allulose, 12);
        recipe1.addIngredient(dashida, 15);
        recipe1.addIngredient(redPepper, 10);
        recipe1.addIngredient(gochujang, 6);

        Recipe recipe2 = Recipe.of("testRecipe2", user1);
        recipe2.addIngredient(soy, 8);
        recipe2.addIngredient(allulose, 14);
        recipe2.addIngredient(dashida, 20);
        recipe2.addIngredient(redPepper, 12);
        recipe2.addIngredient(gochujang, 7);

        Recipe recipe3 = Recipe.of("testRecipe3", user1);
        recipe3.addIngredient(soy, 15);
        recipe3.addIngredient(allulose, 10);
        recipe3.addIngredient(dashida, 18);
        recipe3.addIngredient(redPepper, 9);
        recipe3.addIngredient(gochujang, 5);

        Recipe recipe4 = Recipe.of("testRecipe4", user1);
        recipe4.addIngredient(soy, 12);
        recipe4.addIngredient(allulose, 16);
        recipe4.addIngredient(dashida, 22);
        recipe4.addIngredient(redPepper, 11);
        recipe4.addIngredient(gochujang, 8);

        Recipe recipe5 = Recipe.of("testRecipe5", user1);
        recipe5.addIngredient(soy, 9);
        recipe5.addIngredient(allulose, 13);
        recipe5.addIngredient(dashida, 17);
        recipe5.addIngredient(redPepper, 14);
        recipe5.addIngredient(gochujang, 6);

        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);
        recipeRepository.save(recipe3);
        recipeRepository.save(recipe4);
        recipeRepository.save(recipe5);

        // 과거 이력 저장
        historyRepository.save(History.of(user1, d1, recipe1, LocalDateTime.now()));
        historyRepository.save(History.of(user1, d1, recipe2, LocalDateTime.now()));
        historyRepository.save(History.of(user1, d1, recipe3, LocalDateTime.now()));
        historyRepository.save(History.of(user1, d1, recipe4, LocalDateTime.now()));
        historyRepository.save(History.of(user1, d1, recipe5, LocalDateTime.now()));

        // 즐겨 찾기 저장
        favoriteRepository.save(Favorite.of(user1, recipe1));
        favoriteRepository.save(Favorite.of(user1, recipe3));
        favoriteRepository.save(Favorite.of(user1, recipe5));


        // 메뉴얼 재료 저장
        JsonNode manual1 = objectMapper.readTree("""
                    {
                      "manual_ingredients": [
                        { "amount": "10g", "ingredient": "마늘", "manual": true, "type": "manual" }
                      ]
                    }
                """);
        ingredientSnapshotRepository.save(IngredientSnapshot.of(recipe1, manual1));
        JsonNode manual2 = objectMapper.readTree("""
                    {
                      "manual_ingredients": [
                        { "amount": "20g", "ingredient": "양파", "manual": true, "type": "manual" }
                      ]
                    }
                """);
        ingredientSnapshotRepository.save(IngredientSnapshot.of(recipe2, manual2));
        JsonNode manual3 = objectMapper.readTree("""
                    {
                      "manual_ingredients": [
                        { "amount": "5g", "ingredient": "파", "manual": true, "type": "manual" }
                      ]
                    }
                """);
        ingredientSnapshotRepository.save(IngredientSnapshot.of(recipe3, manual3));

        JsonNode manual4 = objectMapper.readTree("""
                    {
                      "manual_ingredients": [
                        { "amount": "15g", "ingredient": "생강", "manual": true, "type": "manual" }
                      ]
                    }
                """);
        ingredientSnapshotRepository.save(IngredientSnapshot.of(recipe4, manual4));

        JsonNode manual5 = objectMapper.readTree("""
                    {
                      "manual_ingredients": [
                        { "amount": "30ml", "ingredient": "물", "manual": true, "type": "manual" }
                      ]
                    }
                """);
        ingredientSnapshotRepository.save(IngredientSnapshot.of(recipe5, manual5));



    }
}
