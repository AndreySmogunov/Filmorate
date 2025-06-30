package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.MpaRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MpaRatingRepository extends JpaRepository<MpaRating, Integer> {
}