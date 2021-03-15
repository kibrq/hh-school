package ru.hh.school.exceptions;

public class PaginationException extends IllegalArgumentException {
    public PaginationException(String s) {
        super(s);
    }
}
