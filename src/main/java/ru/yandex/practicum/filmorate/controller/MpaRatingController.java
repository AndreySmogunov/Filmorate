package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.mpa.MpaRatingStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class MpaRatingController {

    private final MpaRatingStorage mpaRatingStorage;

    @Autowired
    public MpaRatingController(MpaRatingStorage mpaRatingStorage) {
        this.mpaRatingStorage = mpaRatingStorage;
    }

    @GetMapping
    public List<MpaRating> getAllMpaRatings() {
        return mpaRatingStorage.getAllMpaRatings();
    }

    @GetMapping("/{id}")
    public MpaRating getMpaRatingById(@PathVariable int id) {
        return mpaRatingStorage.getMpaRatingById(id);
    }
}