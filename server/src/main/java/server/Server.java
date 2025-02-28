package server;


import dataaccess.*;
import request.*;
import response.*;
import service.*;
import spark.*;

public class Server {
    private final UserDAO userDataAccess = new MemoryUserDAO();
    private final AuthDAO authDataAccess = new MemoryAuthDAO();
    private final GameDAO gameDataAccess = new MemoryGameDAO();

    private final Serializer serializer = new Serializer();
    private final ClearService clearService = new ClearService();
    private final RegisterService registerService = new RegisterService(userDataAccess, authDataAccess);
    private final LoginService loginService = new LoginService(userDataAccess, authDataAccess);
    private final LogoutService logoutService = new LogoutService(authDataAccess);


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        //Clear Endpoint
        Spark.delete("/db", (req, res) ->{
            ClearResponse response = clearService.clear();
            res.status(response.statusCode());
            res.body("");
            if (response.errMsg() != null){
                res.body(response.errMsg());
            }
            return serializer.toJSON(res.body());
        });

        //Register Endpoint
        Spark.post("/user", (req, res) ->{
            RegisterRequest registerRequest = serializer.fromJSON(req.body(), RegisterRequest.class);
            RegisterResponse response = registerService.register(registerRequest);
            res.status(response.Statuscode());
            res.body(serializer.toJSON(response));
            return res.body();
        });

        //Login Endpoint
        Spark.post("/session", (req, res) ->{
            LoginRequest loginRequest = serializer.fromJSON(req.body(), LoginRequest.class);
            LoginResponse response = loginService.login(loginRequest);
            res.status(response.statusCode());
            res.body(serializer.toJSON(response));
            return res.body();
        });

        //Logout endpoint
        Spark.delete("/session", (req, res) ->{
         LogoutRequest logoutRequest = new LogoutRequest(req.headers("Authorization"));
         LogoutResponse response = logoutService.logout(logoutRequest);
         res.status(response.statusCode());
         res.body("");
            if (response.errMsg() != null){
                res.body(response.errMsg());
            }
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
