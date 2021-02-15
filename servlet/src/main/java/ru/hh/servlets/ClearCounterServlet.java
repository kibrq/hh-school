package ru.hh.servlets;

import ru.hh.Counter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class ClearCounterServlet extends HttpServlet {
    private static final String COOKIE_NAME = "hh-auth";

    ru.hh.Counter counter;

    public ClearCounterServlet(Counter counter) {
        this.counter = counter;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getCookies() == null) {
            return;
        }
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().compareTo(COOKIE_NAME) == 0 && cookie.getValue().length() > 10) {
                counter.clear();
                return;
            }
        }
    }
}
