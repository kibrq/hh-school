package ru.hh.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CounterServletHolder {
    private static final String SUBTRACTION_VALUE_HEADER = "Subtraction-Value";
    private static final String COOKIE_NAME = "hh-auth";

    private final BaseServlet bServlet;
    private final ClearServlet cServlet;

    private static class Counter {
        int data = 0;

        public int get() {
            return data;
        }

        public void inc() {
            data += 1;
        }

        public void dec(int decrease) {
            data -= decrease;
        }

        public void clear() {
            data = 0;
        }

    }

    @WebServlet(urlPatterns = "")
    private static class BaseServlet extends HttpServlet {
        Counter counter;

        public BaseServlet(Counter counter) {
            this.counter = counter;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            PrintWriter writer = resp.getWriter();
            writer.print(counter.get());
            writer.flush();
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            counter.inc();
        }

        @Override
        protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try {
                counter.dec(Integer.parseInt(req.getHeader(SUBTRACTION_VALUE_HEADER)));
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, SUBTRACTION_VALUE_HEADER + "must be integer");
            }
        }
    }

    @WebServlet(urlPatterns = "/clear")
    private static class ClearServlet extends HttpServlet {
        Counter counter;

        public ClearServlet(Counter counter) {
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

    public CounterServletHolder() {
        Counter counter = new Counter();
        bServlet = new BaseServlet(counter);
        cServlet = new ClearServlet(counter);
    }

    public HttpServlet getBaseServlet() {
        return bServlet;
    }

    public HttpServlet getClearServlet() {
        return cServlet;
    }
}
