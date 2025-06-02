package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Film updateFilm(Film film) {
        Optional<Film> existingFilm = films.stream().filter(f -> f.getId().equals(film.getId())).findFirst();
        if (existingFilm.isPresent()) {
            Film updatedFilm = existingFilm.get();
            updatedFilm.setName(film.getName());
            updatedFilm.setDescription(film.getDescription());
            updatedFilm.setReleaseDate(film.getReleaseDate());
            updatedFilm.setDuration(film.getDuration());
            return updatedFilm;
        } else {
            throw new IllegalArgumentException("Film not found");
        }
    }
}