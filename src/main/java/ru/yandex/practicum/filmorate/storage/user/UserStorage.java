package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    User getUserById(Long id);
    void addFriend(Long userId, Long friendId);
    void confirmFriend(Long userId, Long friendId);
    void removeFriend(Long userId, Long friendId);
    List<User> getFriends(Long userId);
    List<User> getCommonFriends(Long userId, Long otherUserId);
}