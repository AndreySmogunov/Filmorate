package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private List<User> users = new ArrayList<>();
    private long currentId = 1;

    public List<User> getAllUsers() {
        return users;
    }

    public User createUser(User user) {
        validateUser(user);
        user.setId(currentId++);
        users.add(user);
        log.info("User created: {}", user);
        return user;
    }

    public User updateUser(User user) {
        validateUser(user);
        Optional<User> existingUser = users.stream().filter(u -> u.getId().equals(user.getId())).findFirst();
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setEmail(user.getEmail());
            updatedUser.setLogin(user.getLogin());
            updatedUser.setName(user.getName());
            updatedUser.setBirthday(user.getBirthday());
            log.info("User updated: {}", updatedUser);
            return updatedUser;
        } else {
            log.error("User not found with id: {}", user.getId());
            throw new IllegalArgumentException("User not found");
        }
    }

    private void validateUser(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Validation failed for user: {}", user);
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}