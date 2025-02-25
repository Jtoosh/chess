package server;


import dataaccess.*;
import request.LoginRequest;
import request.RegisterRequest;
import response.ClearResponse;
import response.LoginResponse;
import response.RegisterResponse;
import service.ClearService;
import service.LoginService;
import service.RegisterService;
import spark.*;

public class Server {
    private UserDAO userDataAccess = new MemoryUserDAO();
    private AuthDAO authDataAccess = new MemoryAuthDAO();
    private GameDAO gameDataAccess = new MemoryGameDAO();

    private final Serializer serializer = new Serializer();
    private final ClearService clearService = new ClearService();
    private final RegisterService registerService = new RegisterService(userDataAccess, authDataAccess);
    private final LoginService loginService = new LoginService(userDataAccess, authDataAccess);


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (req, res) ->{
            clearService.clear();
            ClearResponse response = new ClearResponse(200, null);
            res.status(response.statusCode());
            res.body("");
            if (response.errMsg() != null){
                res.body(response.errMsg());
            }
            return serializer.toJSON(res.body());
        });

        Spark.post("/user", (req, res) ->{
            RegisterRequest registerRequest = serializer.fromJSON(req.body(), RegisterRequest.class);
            RegisterResponse response = registerService.register(registerRequest);
            res.body(serializer.toJSON(response));
            return res.body();
        });

        Spark.post("/session", (req, res) ->{
            LoginRequest loginRequest = serializer.fromJSON(req.body(), LoginRequest.class);
            LoginResponse response = loginService.login(loginRequest);
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
