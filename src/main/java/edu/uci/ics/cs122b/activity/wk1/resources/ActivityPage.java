package edu.uci.ics.cs122b.activity.wk1.resources;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.cs122b.activity.wk1.logger.ServiceLogger;
import edu.uci.ics.cs122b.activity.wk1.models.ExampleRequestModel;
import edu.uci.ics.cs122b.activity.wk1.models.ExampleResponseModel;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("example") // Outer path
public class ActivityPage {

    @Path("post") // This function's path
    @POST // Type of request
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // Example endpoint to add two numbers
    public Response examplePost(@Context HttpHeaders headers, String jsonText) {
        ExampleRequestModel requestModel;
        ExampleResponseModel responseModel;
        ObjectMapper mapper = new ObjectMapper();

        // Validate model & map JSON to POJO
        try {
            requestModel = mapper.readValue(jsonText, ExampleRequestModel.class);
        } catch (IOException e) {
            int resultCode;
            e.printStackTrace();
            if (e instanceof JsonParseException) {
                resultCode = -3;
                responseModel = new ExampleResponseModel(resultCode, "JSON Parse Exception", null);
                ServiceLogger.LOGGER.warning("Unable to map JSON to POJO");
                return Response.status(Response.Status.BAD_REQUEST).entity(responseModel).build();
            } else if (e instanceof JsonMappingException) {
                resultCode = -2;
                responseModel = new ExampleResponseModel(resultCode, "JSON Mapping Exception", null);
                ServiceLogger.LOGGER.warning("Unable to map JSON to POJO");
                return Response.status(Response.Status.BAD_REQUEST).entity(responseModel).build();
            } else {
                resultCode = -1;
                responseModel = new ExampleResponseModel(resultCode, "Internal Server Error", null);
                ServiceLogger.LOGGER.severe("Internal Server Error");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseModel).build();
            }
        }

        ServiceLogger.LOGGER.info("Received post request");
        ServiceLogger.LOGGER.info("Request:\n" + jsonText);

        // Do work
        Integer sum = requestModel.getX() + requestModel.getY();
        ServiceLogger.LOGGER.info("Calculated sum");

        responseModel = new ExampleResponseModel(10, "Successfully calculated sum.", sum);
        return Response.status(Response.Status.OK).entity(responseModel).build();
    }

    @Path("get") // This function's path
    @GET // Type of request
    // Note that get requests do not "consume"
    @Produces(MediaType.APPLICATION_JSON)
    // Example endpoint to add 10 to a number
    public Response exampleGet(@Context HttpHeaders headers, @QueryParam("x") Integer x) {
        ExampleRequestModel requestModel = new ExampleRequestModel(x, 10);
        ExampleResponseModel responseModel;

        ServiceLogger.LOGGER.info("Received get request");
        ServiceLogger.LOGGER.info("Request: " + x);

        // Do work
        Integer sum = requestModel.getX() + requestModel.getY();
        ServiceLogger.LOGGER.info("Calculated sum");

        responseModel = new ExampleResponseModel(10, "Successfully calculated sum.", sum);
        return Response.status(Response.Status.OK).entity(responseModel).build();
    }

}
