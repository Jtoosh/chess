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
    private final ClearService clearService = new ClearService(userDataAccess, authDataAccess, gameDataAccess);
    private final RegisterService registerService = new RegisterService(userDataAccess, authDataAccess);
    private final LoginService loginService = new LoginService(userDataAccess, authDataAccess);
    private final LogoutService logoutService = new LogoutService(authDataAccess);
    private final ListService listService = new ListService(authDataAccess, gameDataAccess);
    private final CreateService createService = new CreateService(authDataAccess, gameDataAccess);
    private final JoinService joinService = new JoinService(userDataAccess, authDataAccess, gameDataAccess);

    private void handleResponse(Response res, Record request, ServiceHandler service){
      try{
        Record response = service.executeService(request);
        res.status(200);
        res.body(serializer.toJSON(response));
      } catch (IllegalArgumentException e){
        res.status(400);
        res.body(serializer.toJSON(new ErrorResponse(e.getMessage())));
      } catch (AuthorizationException e) {
        res.status(401);
        res.body(serializer.toJSON(new ErrorResponse(e.getMessage())));
      } catch (AlreadyInUseException e){
        res.status(403);
        res.body(serializer.toJSON(new ErrorResponse(e.getMessage())));
      } catch (Exception e) {
        res.status(500);
        res.body(serializer.toJSON(new ErrorResponse(e.getMessage())));
      }
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        // Register your endpoints and handle exceptions here.

        //Clear Endpoint
        Spark.delete("/db", (req, res) ->{
          ClearRequest clearRequest = new ClearRequest(req.headers(), req.body());
          ServiceHandler clearServiceLambda = request -> clearService.clear();

          handleResponse(res, clearRequest, clearServiceLambda );
          return res.body();
        });

        //Register Endpoint
        Spark.post("/user", (req, res) ->{
          RegisterRequest registerRequest = serializer.fromJSON(req.body(), RegisterRequest.class);
          ServiceHandler registerServiceLambda = request -> registerService.register((RegisterRequest) request);
          handleResponse(res, registerRequest, registerServiceLambda);
          return res.body();
        });

        //Login Endpoint
        Spark.post("/session", (req, res) ->{
            LoginRequest loginRequest = serializer.fromJSON(req.body(), LoginRequest.class);
            ServiceHandler loginServiceLambda = request -> loginService.login((LoginRequest)request);
            handleResponse(res, loginRequest, loginServiceLambda);
            return res.body();
        });

        //Logout endpoint
        Spark.delete("/session", (req, res) ->{
         LogoutRequest logoutRequest = new LogoutRequest(req.headers("Authorization"));
         ServiceHandler logoutServiceLambda = request -> logoutService.logout((LogoutRequest) request);
         handleResponse(res, logoutRequest, logoutServiceLambda);
         return res.body();
        });

        //List Games endpoint
        Spark.get("/game", (req, res) ->{
            ListRequest listRequest = new ListRequest(req.headers("Authorization"));
            ServiceHandler listServiceLambda = request -> listService.listGames((ListRequest) request);
            handleResponse(res, listRequest, listServiceLambda);
          return res.body();
        });

        //Create game endpoint
        Spark.post("/game", (req, res) -> {
          CreateRequest testRequest = serializer.fromJSON(req.body(), CreateRequest.class);
          CreateRequest createRequest = new CreateRequest(req.headers("Authorization"), testRequest.gameName());
          ServiceHandler createServiceLambda = request -> createService.createGame((CreateRequest) request);
          handleResponse(res, createRequest, createServiceLambda);
          return res.body();
        });

        //Join game endpoint
        Spark.put("/game", (req,res)->{
          JoinRequest tempRequest = serializer.fromJSON(req.body(), JoinRequest.class);
          JoinRequest joinRequest = new JoinRequest(req.headers("Authorization"), tempRequest.playerColor(), tempRequest.gameID());
          ServiceHandler joinServiceLambda = request -> joinService.joinGame((JoinRequest) request);
          handleResponse(res, joinRequest, joinServiceLambda);
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
