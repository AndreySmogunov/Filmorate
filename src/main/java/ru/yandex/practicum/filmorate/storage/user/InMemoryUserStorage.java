package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final List<User> users = new ArrayList<>();
    private long currentId = 1;

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public User createUser(User user) {
        user.setId(currentId++);
        users.add(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        Optional<User> existingUser = users.stream()
                .filter(u -> u.getId().equals(user.getId()))
                .findFirst();

        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setEmail(user.getEmail());
            updatedUser.setLogin(user.getLogin());
            updatedUser.setName(user.getName());
            updatedUser.setBirthday(user.getBirthday());
            updatedUser.setFriends(user.getFriends());
            return updatedUser;
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public void deleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public User getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        if (user != null && friend != null) {
            user.getFriends().put(friendId, FriendshipStatus.PENDING);
            friend.getFriends().put(userId, FriendshipStatus.CONFIRMED);
        }
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        if (user != null && friend != null) {
            user.getFriends().remove(friendId);
            friend.getFriends().remove(userId);
        }
    }

    @Override
    public List<User> getFriends(Long userId) {
        User user = getUserById(userId);
        if (user != null) {
            return user.getFriends().keySet().stream()
                    .map(this::getUserById)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        User user = getUserById(userId);
        User otherUser = getUserById(otherUserId);
        if (user != null && otherUser != null) {
            return user.getFriends().keySet().stream()
                    .filter(otherUser.getFriends().keySet()::contains)
                    .map(this::getUserById)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}