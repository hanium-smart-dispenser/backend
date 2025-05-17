package com.hanium.smartdispenser;

import com.hanium.smartdispenser.dispenser.DispenserRepository;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import com.hanium.smartdispenser.ingredient.IngredientRepository;
import com.hanium.smartdispenser.ingredient.domain.Ingredient;
import com.hanium.smartdispenser.ingredient.domain.IngredientType;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbInitializer implements ApplicationRunner {

    private final DispenserRepository dispenserRepository;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User testUser1 = User.of("testUser1", passwordEncoder.encode("1234"), "aaa@aaa.com");
        User testUser2 = User.of("testUser2", passwordEncoder.encode("1234"), "bbb@bbb.com");

        userRepository.save(testUser1);
        userRepository.save(testUser2);

        dispenserRepository.save(Dispenser.of("testDispenser1", DispenserStatus.BUSY, testUser1));
        dispenserRepository.save(Dispenser.of("testDispenser2", DispenserStatus.BUSY, testUser2));

        ingredientRepository.save(Ingredient.of("고춧가루", IngredientType.POWDER));
        ingredientRepository.save(Ingredient.of("설탕", IngredientType.POWDER));
    }
}
