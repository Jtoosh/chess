package server;

import com.google.gson.*;
import websocket.commands.*;

import java.lang.reflect.Type;

public class Serializer {
  public String toJSON(Object o){
      var gson = new Gson();
      return gson.toJson(o);
  }

  public <T> T fromJSON(String json, Class<T> genericClass){
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(UserGameCommand.class, new UserGameCommandDeserializer());
    Gson gson = builder.create();

    return gson.fromJson(json, genericClass);
  }

  private static class UserGameCommandDeserializer implements JsonDeserializer<UserGameCommand> {

    @Override
    public UserGameCommand deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
      JsonObject parsedObject = jsonElement.getAsJsonObject();

      String typeString = parsedObject.get("commandType").getAsString();
      UserGameCommand.CommandType commandType = UserGameCommand.CommandType.valueOf(typeString);
      return switch (commandType){
        case MAKE_MOVE -> context.deserialize(jsonElement, MakeMoveCommand.class);
        case CONNECT -> context.deserialize(jsonElement, ConnectCommand.class);
        case LEAVE -> context.deserialize(jsonElement, LeaveCommand.class);
        case RESIGN -> context.deserialize(jsonElement, ResignCommand.class);
      };
    }
  }
}
