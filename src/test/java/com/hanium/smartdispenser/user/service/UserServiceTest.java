package com.hanium.smartdispenser.user.service;


import com.hanium.smartdispenser.user.domain.User;
import com.hanium.smartdispenser.user.dto.UserCreateDto;
import com.hanium.smartdispenser.user.exception.DuplicateEmailException;
import com.hanium.smartdispenser.user.exception.UserNotFoundException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        String email = "aaa@gmail.com";
        UserCreateDto user1 = new UserCreateDto("회원1", email,"123123");
        UserCreateDto user2 = new UserCreateDto("회원2", email,"123123");
        userService.createUser(user1);

        em.flush();

        assertThatThrownBy(() ->
                userService.createUser(user2)).isInstanceOf(DuplicateEmailException.class)
                .hasMessage(String.format(DuplicateEmailException.DEFAULT_MESSAGE, email));
    }

    @Test
    @DisplayName("User를 생성하고 Email로 조회 할 수 있다.")
    void findByEmail_returnUser_whenUserExist() {
        String email = "aaa@gmail.com";
        UserCreateDto dto = new UserCreateDto("회원1", email,"123123");
        userService.createUser(dto);

        User findUser = userService.findByEmail(email);

        assertThat(findUser.getName()).isEqualTo(dto.getName());
        assertThat(findUser.getEmail()).isEqualTo(dto.getEmail());
    }

    @Test
    @DisplayName("존재하지 않는 User 조회 시 예외를 반환한다.")
    void findByEmail_throwException_whenUserDoesNotExist() {
        String email = "aaa@gmail.com";
        assertThatThrownBy(() -> userService.findByEmail(email))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format(UserNotFoundException.DEFAULT_MESSAGE, email));

    }


}