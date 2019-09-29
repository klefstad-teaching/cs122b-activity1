package edu.uci.ics.cs122b.service.frame.frame;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import edu.uci.ics.cs122b.service.frame.logger.ServiceLogger;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel {

    @JsonProperty(value = "resultCode", required = true)
    private int resultCode;

    @JsonProperty(value = "message", required = true)
    private String message;

    @JsonCreator
    public ResponseModel(@JsonProperty(value = "resultCode", required = true) int resultCode,
                         @JsonProperty(value = "message", required = true) String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    @JsonProperty("resultCode")
    public int getResultCode(){
        return resultCode;
    }

    @JsonProperty("message")
    public String getMessage(){
        return message;
    }


    // Takes headers from a request and builds a response
    public Response respond(HttpHeaders headers) {
        ServiceLogger.LOGGER.info(message);
        Response.ResponseBuilder responseBuilder = generateResponseBuilder(this);
        if(headers == null)
            return responseBuilder.entity(this).build();

        // Get headers
        String email = headers.getHeaderString("email");
        String sessionID = headers.getHeaderString("sessionID");
        String transactionID = headers.getHeaderString("transactionID");

        return responseBuilder.entity(this)
                .header("email", email)
                .header("sessionID", sessionID)
                .header("transactionID", transactionID)

        // CORS headers
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-control-allow-methods", "GET, POST, PUT, DELETE")
                .header("Access-control-allow-headers", "Content-type")

                .build();
    }


    // Builds a response model for a NEGATIVE result code
    public static Response generateErrorResponse(int resultCode) {
        ResponseModel responseModel = new ResponseModel(resultCode, generateMessage(resultCode));
        return generateResponseBuilder(responseModel).entity(responseModel).build();
    }

    // For use in CATCH blocks
    public static Response generateExceptionResponse(Throwable e) {
        int resultCode;
        e.printStackTrace();
        if (e instanceof JsonParseException) {
            resultCode = -3;
        } else if (e instanceof JsonMappingException) {
            resultCode = -2;
        } else {
            resultCode = -1;
        }
        return generateErrorResponse(resultCode);
    }

    // Return appropriate message for given result code
    public static String generateMessage(int resultCode) {
        switch(resultCode) {
            case -3: return "JSON Parse Exception.";
            case -2: return "JSON Mapping Exception.";
            case -1: return "Internal Server Error.";
            case 1: return "Example Response.";

            // Add more message cases here

            default: return null;
        }
    }

    private static Response.ResponseBuilder generateResponseBuilder(ResponseModel responseModel) {
        // Check for null response model
        if(responseModel == null){
            ServiceLogger.LOGGER.severe("No response model found.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }

        int resultCode = responseModel.getResultCode();

        // NEGATIVE result codes
        if(resultCode < -1) {
            ServiceLogger.LOGGER.warning(responseModel.getMessage());
            return Response.status(Response.Status.BAD_REQUEST);
        }

        // Internal Server Error
        if(resultCode == -1) {
            ServiceLogger.LOGGER.severe(responseModel.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }

        // POSITIVE result codes
        switch(resultCode) {

            // Add other POSITIVE result code cases here

            case 1:
            case 10:
                ServiceLogger.LOGGER.info(responseModel.getMessage());
                return Response.status(Response.Status.OK);
            default:
                ServiceLogger.LOGGER.severe("Unknown response code returned.");
                return null;
        }
    }

}