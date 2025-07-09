package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import javax.persistence.*;

@Entity
@Table(name = "genres")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
}