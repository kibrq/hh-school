package ru.hh.school.entity;

public enum Popularity {
    REGULAR,
    POPULAR;

    private static final int THRESHOLD = 50;

    public static Popularity getByViews(int views) {
        return views < THRESHOLD ? REGULAR : POPULAR;
    }
}
