package ru.yandex.practicum.filmorate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.filmorate.model.MpaRating;

public interface MpaRatingRepository extends JpaRepository<MpaRating, Long> {
}
