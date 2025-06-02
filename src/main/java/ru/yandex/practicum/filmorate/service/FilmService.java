package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private List<Film> films = new ArrayList<>();

    public List<Film> getAllFilms() {
        return films;
    }

    public Film createFilm(Film film) {
        films.add(film);
        return film;
    }
}