package ru.yandex.practicum.filmorate.model;

public enum FriendshipStatus {
    PENDING("Неподтверждённая"),
    CONFIRMED("Подтверждённая"),
    FRIEND("Друг"); // ← Добавленная константа

    private final String status;

    FriendshipStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
