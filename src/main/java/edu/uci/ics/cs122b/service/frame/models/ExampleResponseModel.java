package edu.uci.ics.cs122b.service.frame.models;

import edu.uci.ics.cs122b.service.frame.frame.ResponseModel;

// Example response model
public class ExampleResponseModel extends ResponseModel {

    private Integer sum;

    public ExampleResponseModel(int resultCode, String message, Integer sum) {
        super(resultCode, message);
        this.sum = sum;
    }

}
