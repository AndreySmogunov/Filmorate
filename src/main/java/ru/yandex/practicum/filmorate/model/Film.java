package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "films")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Название не может быть пустым")
    String name;

    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    String description;

    @PastOrPresent(message = "Дата релиза не может быть в будущем")
    @NotNull(message = "Дата релиза не может быть пустой")
    LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    int duration;

    @ManyToOne
    @JoinColumn(name = "mpa_rating_id")
    MpaRating mpaRating;

    @ManyToMany
    @JoinTable(
            name = "film_genres",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    Set<Genre> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "film_likes",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<User> likes = new HashSet<>();
}