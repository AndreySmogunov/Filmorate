package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@Import({FilmDbStorage.class})
public class FilmDbStorageTest {

    @Autowired
    private FilmDbStorage filmStorage;

    @Test
    @Sql("/schema.sql")
    public void testCreateFilm() {
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("This is a test film description.");
        film.setReleaseDate(LocalDate.of(2023, 1, 1));
        film.setDuration(120);
        film.setMpaRating(new MpaRating(1, "G"));

        Film createdFilm = filmStorage.createFilm(film);

        assertThat(createdFilm).isNotNull();
        assertThat(createdFilm.getId()).isNotNull();
        assertThat(createdFilm.getName()).isEqualTo("Test Film");
    }

    @Test
    @Sql("/schema.sql")
    public void testFindFilmById() {
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("This is a test film description.");
        film.setReleaseDate(LocalDate.of(2023, 1, 1));
        film.setDuration(120);
        film.setMpaRating(new MpaRating(1, "G"));

        Film createdFilm = filmStorage.createFilm(film);

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(createdFilm.getId()));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(f ->
                        assertThat(f).hasFieldOrPropertyWithValue("id", createdFilm.getId())
                );
    }

    @Test
    @Sql("/schema.sql")
    public void testUpdateFilm() {
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("This is a test film description.");
        film.setReleaseDate(LocalDate.of(2023, 1, 1));
        film.setDuration(120);
        film.setMpaRating(new MpaRating(1, "G"));

        Film createdFilm = filmStorage.createFilm(film);

        createdFilm.setName("Updated Film");
        Film updatedFilm = filmStorage.updateFilm(createdFilm);

        assertThat(updatedFilm).isNotNull();
        assertThat(updatedFilm.getName()).isEqualTo("Updated Film");
    }

    @Test
    @Sql("/schema.sql")
    public void testDeleteFilm() {
        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("This is a test film description.");
        film.setReleaseDate(LocalDate.of(2023, 1, 1));
        film.setDuration(120);
        film.setMpaRating(new MpaRating(1, "G"));

        Film createdFilm = filmStorage.createFilm(film);

        filmStorage.deleteFilm(createdFilm.getId());

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getFilmById(createdFilm.getId()));

        assertThat(filmOptional).isEmpty();
    }
}