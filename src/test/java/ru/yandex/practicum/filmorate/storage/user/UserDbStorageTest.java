package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@Import({UserDbStorage.class})
public class UserDbStorageTest {

    @Autowired
    private UserDbStorage userStorage;

    @Test
    @Sql("/schema.sql")
    public void testCreateUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testLogin");
        user.setName("Test Name");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        User createdUser = userStorage.createUser(user);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @Sql("/schema.sql")
    public void testFindUserById() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testLogin");
        user.setName("Test Name");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        User createdUser = userStorage.createUser(user);

        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(createdUser.getId()));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(u ->
                        assertThat(u).hasFieldOrPropertyWithValue("id", createdUser.getId())
                );
    }

    @Test
    @Sql("/schema.sql")
    public void testUpdateUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testLogin");
        user.setName("Test Name");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        User createdUser = userStorage.createUser(user);

        createdUser.setName("Updated Name");
        User updatedUser = userStorage.updateUser(createdUser);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo("Updated Name");
    }

    @Test
    @Sql("/schema.sql")
    public void testDeleteUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setLogin("testLogin");
        user.setName("Test Name");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        User createdUser = userStorage.createUser(user);

        userStorage.deleteUser(createdUser.getId());

        Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(createdUser.getId()));

        assertThat(userOptional).isEmpty();
    }
}