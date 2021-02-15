package ru.hh.servlets;



import ru.hh.Counter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/")
public class CounterServlet extends HttpServlet {
    private static final String SUBTRACTION_VALUE_HEADER = "Subtraction-Value";

    ru.hh.Counter counter;

    public CounterServlet(Counter counter) {
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
        System.out.println("hello");
    }
}
