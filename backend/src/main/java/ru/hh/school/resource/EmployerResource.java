package ru.hh.school.resource;

import ru.hh.school.dao.EmployerDao;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/employer")
public class EmployerResource {

    private final EmployerDao employerDao;

    public EmployerResource(EmployerDao employerDao) {
        this.employerDao = employerDao;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public  void setEmployers(@QueryParam("query") String query) {
        employerDao.createEmployer();
    }
}
