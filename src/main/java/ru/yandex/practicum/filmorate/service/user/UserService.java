package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getAllUsers() {
        log.debug("Fetching all users");
        return userStorage.getAllUsers();
    }

    public User getUserById(Long id) {
        log.debug("Fetching user with id: {}", id);
        User user = userStorage.getUserById(id);
        if (user == null) {
            log.error("User not found with id: {}", id);
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }

    public User createUser(User user) {
        log.debug("Creating user: {}", user);
        validateUser(user);
        User createdUser = userStorage.createUser(user);
        log.info("User created: {}", createdUser);
        return createdUser;
    }

    public User updateUser(User user) {
        log.debug("Updating user: {}", user);
        validateUser(user);
        User updatedUser = userStorage.updateUser(user);
        log.info("User updated: {}", updatedUser);
        return updatedUser;
    }

    public void addFriend(Long userId, Long friendId) {
        log.debug("User {} adding friend {}", userId, friendId);
        userStorage.addFriend(userId, friendId);
        log.info("User {} added friend {}", userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {
        log.debug("User {} removing friend {}", userId, friendId);
        userStorage.removeFriend(userId, friendId);
        log.info("User {} removed friend {}", userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        log.debug("Fetching friends for user: {}", userId);
        return userStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        log.debug("Fetching common friends for users: {} and {}", userId, otherUserId);
        return userStorage.getCommonFriends(userId, otherUserId);
    }

    private void validateUser(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Validation failed for user: {}", user);
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}