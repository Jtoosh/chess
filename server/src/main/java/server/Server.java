package server;

import model.UserData;
import request.ClearRequest;
import response.ClearResponse;
import service.ClearService;
import spark.*;

public class Server {
    private Serializer serializer = new Serializer();
    private ClearService clearService = new ClearService();
    private ClearHandler clearHandler = new ClearHandler();

    private class ClearHandler{
        private ClearResponse handleRequest(Request req, Response res){
            new ClearRequest(req.headers(), res.body());
            clearService.clear();
            return new ClearResponse(200, null);
        }
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (req, res) ->{
            System.out.println("Clear endpoint hit!");
            ClearResponse response = clearHandler.handleRequest(req,res);
            res.status(response.statusCode());
            res.body("Clear endpoint successfully called!");
            if (response.errorMessage() != null){
                res.body(response.errorMessage());
            }
            return serializer.toJSON(res.body());
        });

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
