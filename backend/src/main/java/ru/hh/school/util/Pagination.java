package ru.hh.school.util;

public class Pagination {
    private static final int PAGE_DEFAULT = 0, PER_PAGE_DEFAULT = 20;
    public int page, perPage;

    public Pagination(Integer page, Integer perPage) {
        this.page = page == null ? PAGE_DEFAULT : page;
        this.perPage = perPage == null ? PER_PAGE_DEFAULT : perPage;
    }

}
