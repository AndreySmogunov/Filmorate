package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import javax.persistence.*;

@Entity
@Table(name = "mpa_ratings")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MpaRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    // Конструктор по умолчанию
    public MpaRating() {}

    // Конструктор с аргументами
    public MpaRating(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Метод для получения рейтинга по ID
    public static MpaRating getById(int id) {
        switch (id) {
            case 1:
                return new MpaRating(1, "G");
            case 2:
                return new MpaRating(2, "PG");
            case 3:
                return new MpaRating(3, "PG-13");
            case 4:
                return new MpaRating(4, "R");
            case 5:
                return new MpaRating(5, "NC-17");
            default:
                throw new IllegalArgumentException("Invalid MPA rating ID: " + id);
        }
    }
}