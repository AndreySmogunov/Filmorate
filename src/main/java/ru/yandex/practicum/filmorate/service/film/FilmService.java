package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmService {
    final FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> getAllFilms() {
        log.debug("Fetching all films");
        return filmRepository.findAll();
    }

    public Film getFilmById(Long id) {
        log.debug("Fetching film with id: {}", id);
        return filmRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Film not found"));
    }

    public Film createFilm(Film film) {
        log.debug("Creating film: {}", film);
        validateFilm(film);
        Film createdFilm = filmRepository.save(film);
        log.info("Film created: {}", createdFilm);
        return createdFilm;
    }

    public Film updateFilm(Film film) {
        log.debug("Updating film: {}", film);
        validateFilm(film);
        Film updatedFilm = filmRepository.save(film);
        log.info("Film updated: {}", updatedFilm);
        return updatedFilm;
    }

    public void addLike(Long filmId, Long userId) {
        log.debug("User {} adding like to film {}", userId, filmId);
        Film film = getFilmById(filmId);
        film.getLikes().add(userId);
        filmRepository.save(film);
        log.info("User {} added like to film {}", userId, filmId);
    }

    public void removeLike(Long filmId, Long userId) {
        log.debug("User {} removing like from film {}", userId, filmId);
        Film film = getFilmById(filmId);
        film.getLikes().remove(userId);
        filmRepository.save(film);
        log.info("User {} removed like from film {}", userId, filmId);
    }

    public List<Film> getTopFilms(int count) {
        log.debug("Fetching top {} films", count);
        return filmRepository.findAll().stream()
                .sorted((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    private void validateFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Validation failed for film: {}", film);
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
    }
}