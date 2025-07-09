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
@Table(name = "users")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email(message = "Электронная почта должна быть корректной")
    String email;

    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "^\\S+$", message = "Логин не может содержать пробелы")
    String login;

    String name;

    @Past(message = "Дата рождения не может быть в будущем")
    @NotNull(message = "Дата рождения не может быть пустой")
    LocalDate birthday;

    @ManyToMany
    @JoinTable(
            name = "friendships",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    Set<User> friends = new HashSet<>();
}