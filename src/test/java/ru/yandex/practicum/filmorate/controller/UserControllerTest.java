package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUserValidUser() throws Exception {
        User user = new User();
        user.setEmail("valid@example.com");
        user.setLogin("validLogin");
        user.setName("Valid Name");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        when(userService.createUser(any(User.class))).thenReturn(user);

        ObjectNode userJson = objectMapper.createObjectNode();
        userJson.set("email", new TextNode("valid@example.com"));
        userJson.set("login", new TextNode("validLogin"));
        userJson.set("name", new TextNode("Valid Name"));
        userJson.set("birthday", new TextNode("1990-01-01"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUserInvalidUserEmail() throws Exception {
        ObjectNode userJson = objectMapper.createObjectNode();
        userJson.set("email", new TextNode("invalid-email"));
        userJson.set("login", new TextNode("validLogin"));
        userJson.set("name", new TextNode("Valid Name"));
        userJson.set("birthday", new TextNode("1990-01-01"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUserInvalidUserLogin() throws Exception {
        ObjectNode userJson = objectMapper.createObjectNode();
        userJson.set("email", new TextNode("valid@example.com"));
        userJson.set("login", new TextNode("invalid Login"));
        userJson.set("name", new TextNode("Valid Name"));
        userJson.set("birthday", new TextNode("1990-01-01"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUserInvalidUserBirthday() throws Exception {
        ObjectNode userJson = objectMapper.createObjectNode();
        userJson.set("email", new TextNode("valid@example.com"));
        userJson.set("login", new TextNode("validLogin"));
        userJson.set("name", new TextNode("Valid Name"));
        userJson.set("birthday", new TextNode("2025-01-01"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson.toString()))
                .andExpect(status().isBadRequest());
    }
}