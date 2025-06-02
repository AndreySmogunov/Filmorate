package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FilmValidationTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testValidFilm() {
        Film film = new Film();
        film.setName("Valid Film");
        film.setDescription("This is a valid film description.");
        film.setReleaseDate(LocalDate.of(2023, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testInvalidFilmName() {
        Film film = new Film();
        film.setName("");
        film.setDescription("This is a valid film description.");
        film.setReleaseDate(LocalDate.of(2023, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("Название не может быть пустым");
    }

    @Test
    public void testInvalidFilmDescription() {
        Film film = new Film();
        film.setName("Valid Film");
        film.setDescription("This is a very long description that exceeds the maximum length of 200 characters. ".repeat(5));
        film.setReleaseDate(LocalDate.of(2023, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("Максимальная длина описания — 200 символов");
    }

    @Test
    public void testInvalidFilmReleaseDate() {
        Film film = new Film();
        film.setName("Valid Film");
        film.setDescription("This is a valid film description.");
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("Дата релиза не может быть в будущем");
    }

    @Test
    public void testInvalidFilmDuration() {
        Film film = new Film();
        film.setName("Valid Film");
        film.setDescription("This is a valid film description.");
        film.setReleaseDate(LocalDate.of(2023, 1, 1));
        film.setDuration(-1);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertThat(violations).isNotEmpty();
        assertThat(violations).extracting("message").contains("Продолжительность фильма должна быть положительным числом");
    }
}