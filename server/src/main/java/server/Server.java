package server;

import model.UserData;
import request.ClearRequest;
import service.ClearService;
import spark.*;

public class Server {
    private ClearService clearService = new ClearService();

    private class ClearHandler{
        private Request request;
        private Response response;
        private ClearHandler(Request req, Response res){
            this.request = req;
            this.response = res;
        }
        private void handleRequest(){
            new ClearRequest(request.headers(), request.body());
        }
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (request, response) ->
                new ClearHandler(request,response));

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
