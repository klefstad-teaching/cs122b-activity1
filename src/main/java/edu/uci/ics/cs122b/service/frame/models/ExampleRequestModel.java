package edu.uci.ics.cs122b.service.frame.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.cs122b.service.frame.frame.RequestModel;

// Example request model
public class ExampleRequestModel extends RequestModel {
    private Integer x;
    private Integer y;

    @JsonCreator
    public ExampleRequestModel(@JsonProperty(value = "x", required = true) Integer x,
                               @JsonProperty(value = "y", required = true) Integer y) {
        this.x = x;
        this.y = y;
    }

    @JsonProperty("x")
    public Integer getX() {
        return x;
    }

    @JsonProperty("y")
    public Integer getY() {
        return y;
    }
}
