package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmService {
    final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getAllFilms() {
        log.debug("Fetching all films");
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(Long id) {
        log.debug("Fetching film with id: {}", id);
        Film film = filmStorage.getFilmById(id);
        if (film == null) {
            log.error("Film not found with id: {}", id);
            throw new IllegalArgumentException("Film not found");
        }
        return film;
    }

    public Film createFilm(Film film) {
        log.debug("Creating film: {}", film);
        validateFilm(film);
        Film createdFilm = filmStorage.createFilm(film);
        log.info("Film created: {}", createdFilm);
        return createdFilm;
    }

    public Film updateFilm(Film film) {
        log.debug("Updating film: {}", film);
        validateFilm(film);
        Film updatedFilm = filmStorage.updateFilm(film);
        log.info("Film updated: {}", updatedFilm);
        return updatedFilm;
    }

    public void addLike(Long filmId, Long userId) {
        log.debug("User {} adding like to film {}", userId, filmId);
        filmStorage.addLike(filmId, userId);
        log.info("User {} added like to film {}", userId, filmId);
    }

    public void removeLike(Long filmId, Long userId) {
        log.debug("User {} removing like from film {}", userId, filmId);
        filmStorage.removeLike(filmId, userId);
        log.info("User {} removed like from film {}", userId, filmId);
    }

    public List<Film> getTopFilms(int count) {
        log.debug("Fetching top {} films", count);
        return filmStorage.getTopFilms(count);
    }

    private void validateFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Validation failed for film: {}", film);
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
    }
}