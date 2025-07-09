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
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateFilm_ValidFilm() throws Exception {
        Film film = new Film();
        film.setName("Valid Film");
        film.setDescription("This is a valid film description.");
        film.setReleaseDate(LocalDate.of(2023, 1, 1));
        film.setDuration(120);
        film.setMpaRating(new MpaRating(1, "G"));

        when(filmService.createFilm(any(Film.class))).thenReturn(film);

        ObjectNode filmJson = objectMapper.createObjectNode();
        filmJson.set("name", new TextNode("Valid Film"));
        filmJson.set("description", new TextNode("This is a valid film description."));
        filmJson.set("releaseDate", new TextNode("2023-01-01"));
        filmJson.set("duration", new TextNode("120"));
        filmJson.set("mpaRating", objectMapper.createObjectNode()
                .put("id", 1)
                .put("name", "G"));

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateFilm_InvalidFilmName() throws Exception {
        ObjectNode filmJson = objectMapper.createObjectNode();
        filmJson.set("name", new TextNode(""));
        filmJson.set("description", new TextNode("This is a valid film description."));
        filmJson.set("releaseDate", new TextNode("2023-01-01"));
        filmJson.set("duration", new TextNode("120"));
        filmJson.set("mpaRating", objectMapper.createObjectNode()
                .put("id", 1)
                .put("name", "G"));

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFilm_InvalidFilmReleaseDate() throws Exception {
        ObjectNode filmJson = objectMapper.createObjectNode();
        filmJson.set("name", new TextNode("Valid Film"));
        filmJson.set("description", new TextNode("This is a valid film description."));
        filmJson.set("releaseDate", new TextNode("1800-01-01"));
        filmJson.set("duration", new TextNode("120"));
        filmJson.set("mpaRating", objectMapper.createObjectNode()
                .put("id", 1)
                .put("name", "G"));

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFilm_InvalidFilmDuration() throws Exception {
        ObjectNode filmJson = objectMapper.createObjectNode();
        filmJson.set("name", new TextNode("Valid Film"));
        filmJson.set("description", new TextNode("This is a valid film description."));
        filmJson.set("releaseDate", new TextNode("2023-01-01"));
        filmJson.set("duration", new TextNode("-120"));
        filmJson.set("mpaRating", objectMapper.createObjectNode()
                .put("id", 1)
                .put("name", "G"));

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson.toString()))
                .andExpect(status().isBadRequest());
    }
}