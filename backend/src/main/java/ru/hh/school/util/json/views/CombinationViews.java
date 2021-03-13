package ru.hh.school.util.json.views;

public class CombinationViews {
    public static class EmployerShortAndVacancyShort implements EmployerViews.Short, VacancyViews.Short {}

    public static class EmployerShortAndVacancyDetailed implements EmployerViews.Short, VacancyViews.Detailed {}

    public static class EmployerFavoriteDetailedAndVacancyFavoriteDetailed implements EmployerViews.FavoriteDetailed, VacancyViews.FavoriteDetailed {}
}
