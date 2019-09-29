package edu.uci.ics.cs122b.service.frame.frame;

import edu.uci.ics.cs122b.service.frame.frame.RequestModel;
import edu.uci.ics.cs122b.service.frame.frame.ResponseModel;

public abstract class Handler {

    public abstract ResponseModel getResponse(RequestModel requestModel);

}
