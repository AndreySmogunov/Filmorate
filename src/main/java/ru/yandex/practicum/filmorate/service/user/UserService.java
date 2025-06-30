package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        log.debug("Fetching all users");
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        log.debug("Fetching user with id: {}", id);
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User createUser(User user) {
        log.debug("Creating user: {}", user);
        validateUser(user);
        User createdUser = userRepository.save(user);
        log.info("User created: {}", createdUser);
        return createdUser;
    }

    public User updateUser(User user) {
        log.debug("Updating user: {}", user);
        validateUser(user);
        User updatedUser = userRepository.save(user);
        log.info("User updated: {}", updatedUser);
        return updatedUser;
    }

    public void addFriend(Long userId, Long friendId) {
        log.debug("User {} adding friend {}", userId, friendId);
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriends().put(friendId, FriendshipStatus.PENDING);
        friend.getFriends().put(userId, FriendshipStatus.CONFIRMED);
        userRepository.save(user);
        userRepository.save(friend);
        log.info("User {} added friend {}", userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {
        log.debug("User {} removing friend {}", userId, friendId);
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
        userRepository.save(user);
        userRepository.save(friend);
        log.info("User {} removed friend {}", userId, friendId);
    }

    public List<User> getFriends(Long userId) {
        log.debug("Fetching friends for user: {}", userId);
        User user = getUserById(userId);
        return user.getFriends().keySet().stream().map(this::getUserById).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        log.debug("Fetching common friends for users: {} and {}", userId, otherUserId);
        User user = getUserById(userId);
        User otherUser = getUserById(otherUserId);
        return user.getFriends().keySet().stream()
                .filter(otherUser.getFriends().keySet()::contains)
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    private void validateUser(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Validation failed for user: {}", user);
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}