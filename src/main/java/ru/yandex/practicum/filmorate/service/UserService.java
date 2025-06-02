package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();
    private long currentId = 1;

    public List<User> getAllUsers() {
        return users;
    }

    public User createUser(User user) {
        user.setId(currentId++);
        users.add(user);
        return user;
    }

    public User updateUser(User user) {
        Optional<User> existingUser = users.stream().filter(u -> u.getId().equals(user.getId())).findFirst();
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setEmail(user.getEmail());
            updatedUser.setLogin(user.getLogin());
            updatedUser.setName(user.getName());
            updatedUser.setBirthday(user.getBirthday());
            return updatedUser;
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}