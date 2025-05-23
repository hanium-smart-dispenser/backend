package com.hanium.smartdispenser;

import com.hanium.smartdispenser.dispenser.repository.DispenserRepository;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import com.hanium.smartdispenser.dispenser.service.DispenserCommandFacade;
import com.hanium.smartdispenser.ingredient.IngredientRepository;
import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import com.hanium.smartdispenser.ingredient.domain.IngredientType;
import com.hanium.smartdispenser.recipe.RecipeService;
import com.hanium.smartdispenser.recipe.dto.IngredientWithAmountDto;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DbInitializer implements ApplicationRunner {

    private final DispenserRepository dispenserRepository;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeService recipeService;
    private final PasswordEncoder passwordEncoder;
    private final DispenserCommandFacade dispenserCommandFacade;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User testUser1 = User.of("testUser1", passwordEncoder.encode("1234"), "aaa@aaa.com");
        User testUser2 = User.of("testUser2", passwordEncoder.encode("1234"), "bbb@bbb.com");
        User testUser3 = User.of("testUser3", passwordEncoder.encode("1234"), "ccc@ccc.com");

        userRepository.save(testUser1);
        userRepository.save(testUser2);
        userRepository.save(testUser3);

        dispenserRepository.save(Dispenser.of(DispenserStatus.READY, testUser1));
        dispenserRepository.save(Dispenser.of(DispenserStatus.READY, testUser2));

        ingredientRepository.save(Ingredient.of("고춧가루", IngredientType.POWDER));
        ingredientRepository.save(Ingredient.of("설탕", IngredientType.POWDER));

        List<IngredientWithAmountDto> ingredients1 = new ArrayList<>();
        ingredients1.add(new IngredientWithAmountDto(1L, 1, IngredientType.POWDER));
        ingredients1.add(new IngredientWithAmountDto(2L, 2, IngredientType.POWDER));

        List<IngredientWithAmountDto> ingredients2 = new ArrayList<>();
        ingredients2.add(new IngredientWithAmountDto(1L, 1, IngredientType.LIQUID));
        ingredients2.add(new IngredientWithAmountDto(2L, 2, IngredientType.LIQUID));

        recipeService.createRecipe(testUser1.getId(), "testRecipe1", ingredients1);
        recipeService.createRecipe(testUser2.getId(), "testRecipe2", ingredients2);


        dispenserCommandFacade.sendCommand(1L, 1L, 1L);

    }
}
