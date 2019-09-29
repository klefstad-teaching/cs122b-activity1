package edu.uci.ics.cs122b.service.frame.frame;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.cs122b.service.frame.logger.ServiceLogger;


import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class Endpoint {

    public static ObjectMapper MAPPER = new ObjectMapper();

    // For use in a POST endpoint; takes HTTP headers, raw JSON, request class, a logical handler, and description;
    // returns a HTTP response
    public static Response workflowPost(HttpHeaders headers, String jsonText, Class<? extends RequestModel> requestClass,
                                        Handler handler, String desc) {
        RequestModel requestModel;
        ResponseModel responseModel;

        // Validate model & map JSON to POJO
        try {
            requestModel = MAPPER.readValue(jsonText, requestClass);
        } catch (IOException e) {
            return ResponseModel.generateExceptionResponse(e);
        }

        ServiceLogger.LOGGER.info("Received request to " + desc + ".");
        ServiceLogger.LOGGER.info("Request:\n" + jsonText);

        // Pass request to handler
        responseModel = handler.getResponse(requestModel);

        return responseModel.respond(headers);
    }

    // For use in a GET endpoint; takes HTTP headers, a previously constructed request model, a logical handler,
    // and description; returns a HTTP model
    public static Response workflowGet(HttpHeaders headers, RequestModel requestModel, Handler handler, String desc) {
        ResponseModel responseModel;

        ServiceLogger.LOGGER.info("Received request to " + desc + ".");

        // Pass request to handler
        responseModel = handler.getResponse(requestModel);

        return responseModel.respond(headers);
    }


}