package edu.uci.ics.cs122b.service.frame.resources;


import edu.uci.ics.cs122b.service.frame.frame.Endpoint;
import edu.uci.ics.cs122b.service.frame.handlers.ExamplePostHandler;
import edu.uci.ics.cs122b.service.frame.models.ExampleRequestModel;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("example")
public class ExampleEndpoints {

    @Path("post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Example endpoint to add two numbers
    public Response examplePost(@Context HttpHeaders headers, String jsonText) {
        ExamplePostHandler handler = new ExamplePostHandler();

        return Endpoint.workflowPost(headers, jsonText, ExampleRequestModel.class, handler, "test a post example");
    }

    @Path("get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // Example endpoint to add 10 to a number
    public Response exampleGet(@Context HttpHeaders headers, @QueryParam("number") Integer number) {
        ExampleRequestModel request = new ExampleRequestModel(10, number);
        ExamplePostHandler handler = new ExamplePostHandler();

        return Endpoint.workflowGet(headers, request, handler, "test a get example");
    }

}
