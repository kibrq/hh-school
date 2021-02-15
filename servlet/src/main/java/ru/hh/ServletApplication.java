package ru.hh;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.hh.servlets.CounterServletHolder;

public class ServletApplication {

  private static Server createServer(int port) {
    Server server = new Server(port);
    ServletHandler handler = new ServletHandler();
    CounterServletHolder holder = new CounterServletHolder();
    handler.addServletWithMapping(new ServletHolder(holder.getBaseServlet()), "/counter");
    handler.addServletWithMapping(new ServletHolder(holder.getClearServlet()), "/counter/clear");
    server.setHandler(handler);
    return server;
  }

  public static void main(String[] args) throws Exception {
    Server server = createServer(8080);
    server.start();
    server.join();
  }
}
