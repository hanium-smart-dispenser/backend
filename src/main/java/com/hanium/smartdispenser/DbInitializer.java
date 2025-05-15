package com.hanium.smartdispenser;

import com.hanium.smartdispenser.dispenser.DispenserRepository;
import com.hanium.smartdispenser.dispenser.domain.Dispenser;
import com.hanium.smartdispenser.dispenser.domain.DispenserStatus;
import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.respository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbInitializer implements ApplicationRunner {

    private final DispenserRepository dispenserRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User testUser1 = User.of("testUser1", "1234", "aaa@aaa.com");
        User testUser2 = User.of("testUser2", "1234", "bbb@bbb.com");
        userRepository.save(testUser1);
        userRepository.save(testUser2);
        dispenserRepository.save(Dispenser.of("testDispenser1", DispenserStatus.BUSY, testUser1));
        dispenserRepository.save(Dispenser.of("testDispenser2", DispenserStatus.BUSY, testUser2));
    }
}
