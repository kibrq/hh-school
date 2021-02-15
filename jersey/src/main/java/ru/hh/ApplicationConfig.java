package ru.hh;

import ru.hh.resource.CounterResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest")
public class ApplicationConfig extends Application {
    private final Set<Object> singletons = new HashSet<>();
    private final Set<Class<?>> classes = new HashSet<>();
    public ApplicationConfig() {
        singletons.add(new CounterResource());
        classes.add(CounterResource.class);
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

//    @Override
//    public Set<Class<?>> getClasses() {
//        return classes;
//    }
}
