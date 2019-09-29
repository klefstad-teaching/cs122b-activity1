package edu.uci.ics.cs122b.activity.wk1.resources;

import edu.uci.ics.cs122b.activity.wk1.logger.ServiceLogger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("test")
public class TestPage {
    // Set the path for this function
    @Path("hello")
    // Set the HTTP request method
    @GET
    // Set the type of content that will be sent in response
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloWorld() {
        System.err.println("Hello world!");
        ServiceLogger.LOGGER.info("Hello!");
        return Response.status(Status.OK).build();
    }
}