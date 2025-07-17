package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final List<Film> films = new ArrayList<>();
    private long currentId = 1;
    private final UserStorage userStorage;

    @Autowired
    public InMemoryFilmStorage(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

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
            updatedFilm.setMpaRating(film.getMpaRating());
            updatedFilm.setGenres(film.getGenres());
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

    @Override
    public void addLike(Long filmId, Long userId) {
        Film film = getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        if (film != null && user != null) {
            film.getLikes().add(user);
        }
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        Film film = getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        if (film != null && user != null) {
            film.getLikes().remove(user);
        }
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return films.stream()
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}