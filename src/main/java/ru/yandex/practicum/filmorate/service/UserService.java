package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}