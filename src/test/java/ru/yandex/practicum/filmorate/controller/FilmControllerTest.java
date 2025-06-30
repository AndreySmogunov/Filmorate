package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;
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

    @Test
    public void testCreateFilm_ValidFilm() throws Exception {
        Film film = new Film();
        film.setName("Valid Film");
        film.setDescription("This is a valid film description.");
        film.setReleaseDate(LocalDate.of(2023, 1, 1));
        film.setDuration(120);

        when(filmService.createFilm(any(Film.class))).thenReturn(film);

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Valid Film\",\"description\":\"This is a valid film description.\",\"releaseDate\":\"2023-01-01\",\"duration\":120}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateFilm_InvalidFilmName() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"This is a valid film description.\",\"releaseDate\":\"2023-01-01\",\"duration\":120}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFilm_InvalidFilmDescription() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Valid Film\",\"description\":\"This is a very long description that exceeds the maximum length of 200 characters. ".repeat(5) + "\",\"releaseDate\":\"2023-01-01\",\"duration\":120}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFilm_InvalidFilmReleaseDate() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Valid Film\",\"description\":\"This is a valid film description.\",\"releaseDate\":\"1895-12-27\",\"duration\":120}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateFilm_InvalidFilmDuration() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Valid Film\",\"description\":\"This is a valid film description.\",\"releaseDate\":\"2023-01-01\",\"duration\":-1}"))
                .andExpect(status().isBadRequest());
    }
}