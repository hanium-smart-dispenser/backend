package com.hanium.smartdispenser.user.service;


import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.dto.UserCreateDto;
import com.hanium.smartdispenser.user.exception.DuplicateEmailException;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("User 생성 시 중복된 Email이면 예외를 반환한다.")
    void createUser_throwException_whenEmailIsDuplicated() {
        UserCreateDto user1 = new UserCreateDto("회원1","aaa@gmail.com","123123");
        UserCreateDto user2 = new UserCreateDto("회원2","aaa@gmail.com","123123");
        userService.createUser(user1);

        em.flush();

        Assertions.assertThatThrownBy(() ->
                userService.createUser(user2)).isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    @DisplayName("User를 생성하고 Email로 조회 할 수 있다.")
    void findByEmail_returnUser_whenUserExist() {
        String email = "aaa@gmail.com";
        UserCreateDto dto = new UserCreateDto("회원1", email,"123123");
        userService.createUser(dto);

        User findUser = userService.findByEmail(email);

        Assertions.assertThat(findUser.getName()).isEqualTo(dto.getName());
        Assertions.assertThat(findUser.getEmail()).isEqualTo(dto.getEmail());
    }


}