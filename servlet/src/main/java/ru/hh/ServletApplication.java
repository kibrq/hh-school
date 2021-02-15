package ru.hh;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.hh.servlets.ClearCounterServlet;
import ru.hh.servlets.CounterServlet;

public class ServletApplication {

  private static Server createServer(int port) {
    Server server = new Server(port);
    ServletHandler handler = new ServletHandler();
    Counter counter = new Counter();
    CounterServlet counterServlet = new CounterServlet(counter);
    ClearCounterServlet clearServlet = new ClearCounterServlet(counter);
    handler.addServletWithMapping(new ServletHolder(counterServlet), "/counter");
    handler.addServletWithMapping(new ServletHolder(clearServlet), "/counter/clear");
    server.setHandler(handler);
    return server;
  }

  public static void main(String[] args) throws Exception {
    Server server = createServer(8080);
    server.start();
    server.join();
  }
}
