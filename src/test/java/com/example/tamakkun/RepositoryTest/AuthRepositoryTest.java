package com.example.tamakkun.RepositoryTest;

import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Repository.AuthRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthRepositoryTest {

    @Autowired
    AuthRepository authRepository;

    MyUser user1, user2, user3;

    @BeforeEach
    void setUp() {
        user1 = new MyUser(null, "john_doe", "password123", "USER", "johnDoe@user.com",null,null,null,null);
        user2 = new MyUser(null, "jane_doe", "password456", "ADMIN", "JohnDoeTheAdmin@admin.com",null,null,null,null);
        user3 = new MyUser(null, "mohammed", "password334", "CENTRE", "mohammed@Tamakkun.com",null,null,null,null);
        authRepository.save(user1);
        authRepository.save(user2);
    }

    @Test
    public void findMyUserByUsernameTest() {
        MyUser foundUser = authRepository.findMyUserByUsername("john_doe");
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getUsername()).isEqualTo("john_doe");
    }

    @Test
    public void findMyUserByUsernameNotFoundTest() {
        MyUser foundUser = authRepository.findMyUserByUsername("non_existent_user");
        Assertions.assertThat(foundUser).isNull();
    }

    @Test
    public void findMyUserByIdTest() {
        MyUser foundUser = authRepository.findMyUserById(user1.getId());
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser).isEqualTo(user1);
    }

    @Test
    public void findMyUserByIdNotFoundTest() {
        MyUser foundUser = authRepository.findMyUserById(999);
        Assertions.assertThat(foundUser).isNull();
    }
}
