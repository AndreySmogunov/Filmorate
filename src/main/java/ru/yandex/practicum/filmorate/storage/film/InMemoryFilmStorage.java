package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final List<Film> films = new ArrayList<>();
    private long currentId = 1;

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films);
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(currentId++);
        films.add(film);
        return film;
    }

    @Override
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

    @Override
    public void deleteFilm(Long id) {
        films.removeIf(film -> film.getId().equals(id));
    }

    @Override
    public Film getFilmById(Long id) {
        return films.stream().filter(film -> film.getId().equals(id)).findFirst().orElse(null);
    }
}