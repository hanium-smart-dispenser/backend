package com.hanium.smartdispenser.user.respository;

import com.hanium.smartdispenser.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("User를 저장하고 조회 할 수 있다.")
    void findByEmail_returnUser_whenSaved() {
        User user = User.of("회원1","123123","aaa@gmail.com");
        userRepository.save(user);

        Optional<User> findUser = userRepository.findById(user.getId());

        assertThat(findUser).isPresent();
        assertThat(findUser.get()).isEqualTo(user);
    }

    @Test
    @DisplayName("User를 삭제 할 수 있다.")
    void delete_removeUser_whenUserExist() {
        User user = User.of("회원1","123123","aaa@gmail.com");
        userRepository.save(user);

        userRepository.delete(user);
        Optional<User> findUser = userRepository.findById(user.getId());

        assertThat(findUser).isEmpty();
    }

    @Test
    @DisplayName("중복된 Email로 저장시 예외를 반환한다.")
    void save_throwException_whenEmailIsDuplicated() {
        User user1 = User.of("회원1", "123123", "aaa@gmail.com");
        User user2 = User.of("회원2", "123123", "aaa@gmail.com");

        userRepository.save(user1);

        assertThatThrownBy(() -> userRepository.saveAndFlush(user2)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Email 존재하면 existByEmail은 true를 반환한다.")
    void existByEmail_returnTrue_whenEmailIsExist() {
        User user = User.of("회원1", "123123", "aaa@gmail.com");
        userRepository.save(user);

        assertThat(userRepository.existsByEmail(user.getEmail())).isTrue();
    }

    @Test
    @DisplayName("Email 존재하지 않으면 existByEmail은 false를 반환한다.")
    void existByEmail_returnFalse_whenEmailDoesNotExist() {
        User user = User.of("회원1", "123123", "aaa@gmail.com");
        assertThat(userRepository.existsByEmail(user.getEmail())).isFalse();
    }

    @Test
    @DisplayName("User를 Email로 조회 할 수 있다.")
    void findByEmail_returnUser_whenEmailExist() {
        String email = "aaa@gmail.com";
        User user = User.of("123123", email, UUID.randomUUID().toString());
        userRepository.save(user);
        Optional<User> findUser = userRepository.findByEmail(email);

        assertThat(findUser).isPresent();
        assertThat(findUser.get()).isEqualTo(user);
    }

}