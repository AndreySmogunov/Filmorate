package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        validateUser(user);
        User createdUser = userStorage.createUser(user);
        log.info("User created: {}", createdUser);
        return createdUser;
    }

    public User updateUser(User user) {
        validateUser(user);
        User updatedUser = userStorage.updateUser(user);
        log.info("User updated: {}", updatedUser);
        return updatedUser;
    }

    public void addFriend(Long userId, Long friendId) {
        userStorage.addFriend(userId, friendId);
        log.info("User {} added friend {}", userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {
        userStorage.removeFriend(userId, friendId);
        log.info("User {} removed friend {}", userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        return userStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        return userStorage.getCommonFriends(userId, otherUserId);
    }

    private void validateUser(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Validation failed for user: {}", user);
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}