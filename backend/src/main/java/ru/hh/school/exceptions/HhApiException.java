package ru.hh.school.exceptions;

import java.io.IOException;

public class HhApiException extends IOException {
    public HhApiException(String message) {
        super("Error while getting information from hh.ru:" + message + ".");
    }
}
