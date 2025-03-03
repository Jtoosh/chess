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
    private final ListService listService = new ListService(authDataAccess, gameDataAccess);

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
                res.body(serializer.toJSON(response.errMsg()));
            }
            return serializer.toJSON(res.body());
        });

        //Register Endpoint
        Spark.post("/user", (req, res) ->{
            RegisterRequest registerRequest = serializer.fromJSON(req.body(), RegisterRequest.class);
          RegisterResponse response =null;
          try {
            response=registerService.register(registerRequest);
              res.status(200);
            res.body(serializer.toJSON(response));
          } catch (AuthorizationException e) {
            res.status(401);
            res.body(serializer.toJSON(new ErrorResponse("Error: unauthorized")));
          } catch (DataAccessException e){
            res.status(403);
            res.body(serializer.toJSON(new ErrorResponse("Error: already taken")));
          }

            return res.body();
        });

        //Login Endpoint
        Spark.post("/session", (req, res) ->{
            LoginRequest loginRequest = serializer.fromJSON(req.body(), LoginRequest.class);
          LoginResponse response =null;
          try {
            response=loginService.login(loginRequest);
            res.status(200);
            res.body(serializer.toJSON(response));
          } catch (AuthorizationException e) {
            res.status(401);
            res.body(serializer.toJSON(new ErrorResponse("Error: unauthorized")));
          } catch (DataAccessException e){
            res.status(500);
            res.body(serializer.toJSON(new ErrorResponse("Error: user not found")));
          }
            return res.body();
        });

        //Logout endpoint
        Spark.delete("/session", (req, res) ->{
         LogoutRequest logoutRequest = new LogoutRequest(req.headers("Authorization"));
          LogoutResponse response =null;
          try {
            response=logoutService.logout(logoutRequest);
            res.status(200);
            res.body(serializer.toJSON(response.errMsg()));
          } catch (AuthorizationException e) {
            res.status(401);
            res.body(serializer.toJSON(new ErrorResponse("Error: unauthorized")));
          }
         return res.body();
        });

        //List Games endpoint
        Spark.get("/game", (req, res) ->{
            ListRequest listRequest = new ListRequest(req.headers("Authorization"));
            ListResponse response = listService.listGames(listRequest);
            res.status(response.statusCode());
            if (response.errMsg() != null){
                res.body(serializer.toJSON(response.errMsg()));
            } else{
                res.body(serializer.toJSON(response.games()));
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
