package edu.uci.ics.cs122b.service.frame.handlers;

import edu.uci.ics.cs122b.service.frame.frame.Handler;
import edu.uci.ics.cs122b.service.frame.frame.RequestModel;
import edu.uci.ics.cs122b.service.frame.frame.ResponseModel;
import edu.uci.ics.cs122b.service.frame.models.ExampleRequestModel;
import edu.uci.ics.cs122b.service.frame.models.ExampleResponseModel;

public class ExamplePostHandler extends Handler {

    private Integer add(Integer x, Integer y) {
        return (x+y);
    }

    @Override
    // Example handler function
    public ResponseModel getResponse(RequestModel requestModel) {
        ExampleRequestModel request = (ExampleRequestModel)requestModel;
        Integer sum = add(request.getX(), request.getY());
        return new ExampleResponseModel(1, ResponseModel.generateMessage(1), sum);
    }

}
