package ru.hh;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;


public class JerseyApplication {

  private static Server createServer(int port) {
    Server server = new Server(port);

    ServletContextHandler context = new ServletContextHandler();

    server.setHandler(context);

    ServletHolder holder = context.addServlet(ServletContainer.class, "/*");
    holder.setInitOrder(1);
    holder.setInitParameter("javax.ws.rs.Application", ApplicationConfig.class.getName());

    return server;
  }

  public static void main(String[] args) throws Exception {
    Server server = createServer(8080);
    server.start();
    server.join();
  }
}
