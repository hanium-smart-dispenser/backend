package com.hanium.smartdispenser;

import com.hanium.smartdispenser.dispenser.domain.DispenserSauce;
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
import java.util.UUID;

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
        User testUser1 = User.of(passwordEncoder.encode("1234"), "aaa@aaa.com", UUID.randomUUID().toString());
        User testUser2 = User.of(passwordEncoder.encode("1234"), "bbb@bbb.com", UUID.randomUUID().toString());
        User testUser3 = User.of(passwordEncoder.encode("1234"), "ccc@ccc.com", UUID.randomUUID().toString());
        testUser1.convertGuestToUser();
        testUser2.convertGuestToUser();
        testUser3.convertGuestToUser();

        userRepository.save(testUser1);
        userRepository.save(testUser2);
        userRepository.save(testUser3);

        Dispenser dispenser1 = dispenserRepository.save(Dispenser.of(DispenserStatus.READY, testUser1));
        dispenserRepository.save(Dispenser.of(DispenserStatus.READY, testUser2));

        Ingredient redPepperPowder = ingredientRepository.save(Ingredient.of("고춧가루", IngredientType.POWDER));
        Ingredient sugar = ingredientRepository.save(Ingredient.of("설탕", IngredientType.POWDER));
        Ingredient soySauce = ingredientRepository.save(Ingredient.of("간장", IngredientType.LIQUID));
        Ingredient pepper = ingredientRepository.save(Ingredient.of("후추", IngredientType.POWDER));
        Ingredient dasida = ingredientRepository.save(Ingredient.of("다시다", IngredientType.POWDER));

        List<IngredientWithAmountDto> ingredients1 = new ArrayList<>();
        ingredients1.add(new IngredientWithAmountDto(1L, 1, IngredientType.POWDER));
        ingredients1.add(new IngredientWithAmountDto(2L, 2, IngredientType.POWDER));

        List<IngredientWithAmountDto> ingredients2 = new ArrayList<>();
        ingredients2.add(new IngredientWithAmountDto(1L, 1, IngredientType.LIQUID));
        ingredients2.add(new IngredientWithAmountDto(2L, 2, IngredientType.LIQUID));

        recipeService.createRecipe(testUser1.getId(), "testRecipe1", ingredients1);
        recipeService.createRecipe(testUser2.getId(), "testRecipe2", ingredients2);

        dispenser1.addSauce(DispenserSauce.of(1, redPepperPowder));
        dispenser1.addSauce(DispenserSauce.of(2, sugar));
        dispenser1.addSauce(DispenserSauce.of(3, soySauce));
        dispenser1.addSauce(DispenserSauce.of(4, pepper));
        dispenser1.addSauce(DispenserSauce.of(5, dasida));

        //더티 체킹 안되는이유 -> 트랙잭션 끝남
        dispenserRepository.save(dispenser1);

        dispenserCommandFacade.sendCommand(1L, 1L, 1L);

    }
}
