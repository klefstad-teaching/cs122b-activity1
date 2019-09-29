package edu.uci.ics.cs122b.service.frame.frame;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.cs122b.service.frame.logger.ServiceLogger;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

import static edu.uci.ics.cs122b.service.frame.frame.Endpoint.MAPPER;

public class Microservice {

    // Used for communication between microservices; takes a request model, a response class, path information,
    // and a description; returns a response model
    public static ResponseModel request(RequestModel requestModel, Class<? extends ResponseModel> responseClass,
                                        String servicePath, String endpointPath, String desc) {
        ServiceLogger.LOGGER.info("Sending request to " + desc + "...");

        // Create a new Client
        ServiceLogger.LOGGER.info("Building client...");
        Client client = ClientBuilder.newClient();
        client.register(JacksonFeature.class);

        // Create a WebTarget to send a request at
        ServiceLogger.LOGGER.info("Building WebTarget...");
        WebTarget webTarget = client.target(servicePath).path(endpointPath);

        // Create an InvocationBuilder to create the HTTP request
        ServiceLogger.LOGGER.info("Starting invocation builder...");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        // Send the request and save it to a Response
        ServiceLogger.LOGGER.info("Sending request...");
        Response response = invocationBuilder.post(Entity.entity(requestModel, MediaType.APPLICATION_JSON));
        ServiceLogger.LOGGER.info("Request sent.");

        ServiceLogger.LOGGER.info("Received status " + response.getStatus());
        try {
            String jsonText = response.readEntity(String.class);
            ResponseModel responseModel = MAPPER.readValue(jsonText, responseClass);
            ServiceLogger.LOGGER.info("Successfully mapped response to POJO.");
            return responseModel;
        } catch (IOException e) {
            ServiceLogger.LOGGER.warning("Unable to map response to POJO.");
            return null;
        }
    }

}