package server;

import model.UserData;
import request.ClearRequest;
import request.RegisterRequest;
import response.ClearResponse;
import response.RegisterResponse;
import service.ClearService;
import service.RegisterService;
import spark.*;

public class Server {
    private final Serializer serializer = new Serializer();
    private final ClearService clearService = new ClearService();
    private final RegisterService registerService = new RegisterService();


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (req, res) ->{
            ClearRequest clearRequest = new ClearRequest(req.headers(), res.body());
            clearService.clear();
            ClearResponse response = new ClearResponse(200, null);
            res.status(response.statusCode());
            res.body("");
            if (response.errorMessage() != null){
                res.body(response.errorMessage());
            }
            return serializer.toJSON(res.body());
        });

        Spark.post("/user", (req, res) ->{
            RegisterRequest registerRequest = serializer.fromJSON(req.body(), RegisterRequest.class);
            RegisterResponse response = registerService.register(registerRequest);
            res.body(serializer.toJSON(response));
            return res.body();
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
