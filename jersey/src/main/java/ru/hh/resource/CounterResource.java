package ru.hh.resource;


import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.util.Map;

@Path("/counter/")
public class CounterResource {
    private static final String SUBTRACTION_VALUE_HEADER = "Subtraction-Value";
    private static final String COOKIE_NAME = "hh-auth";

    private static class Counter {
        int data;

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

    public static class CounterWrapper {
        LocalDateTime date;
        int value;

        public CounterWrapper() {

        }

        public CounterWrapper(Counter counter) {
            this.value = counter.get();
            this.date = LocalDateTime.now();
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    Counter counter = new Counter();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public CounterWrapper get() {
        return new CounterWrapper(counter);
    }

    @POST
    @Path("/")
    public Response post() {
        counter.inc();
        return Response.ok().build();
    }

    @DELETE
    @Path("/")
    public Response delete(@Context HttpHeaders headers) {
        String value = headers.getHeaderString(SUBTRACTION_VALUE_HEADER);
        try {
            counter.dec(Integer.parseInt(value));
        } catch (NumberFormatException ex) {
            throw new BadRequestException(SUBTRACTION_VALUE_HEADER + "must be an integer");
        }
        return Response.ok().build();
    }

    @POST
    @Path("/clear")
    public Response clear(@Context HttpHeaders headers) {
        Map<String, Cookie> cookies = headers.getCookies();
        if (cookies.containsKey(COOKIE_NAME) && cookies.get(COOKIE_NAME).getValue().length() > 10) {
            counter.clear();
        }
        return Response.ok().build();
    }
}
