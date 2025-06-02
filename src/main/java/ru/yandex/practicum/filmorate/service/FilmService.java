package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private List<Film> films = new ArrayList<>();
    private long currentId = 1;

    public List<Film> getAllFilms() {
        return films;
    }

    public Film createFilm(Film film) {
        film.setId(currentId++);
        films.add(film);
        return film;
    }
}