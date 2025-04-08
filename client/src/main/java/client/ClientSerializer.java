package client;

import com.google.gson.*;
import websocket.messages.*;

import java.lang.reflect.Type;

public class ClientSerializer {
  public String toJSON(Object o){
    var gson = new Gson();
    return gson.toJson(o);
  }

  public <T> T fromJSON(String json, Class<T> genericClass){
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ServerMessage.class, new ServerMessageDeserializer());
    Gson gson = builder.create();



    return gson.fromJson(json, genericClass);
  }

  private class ServerMessageDeserializer implements JsonDeserializer<ServerMessage>{

    @Override
    public ServerMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
      JsonObject parsedObject = jsonElement.getAsJsonObject();

      String typeString = parsedObject.get("serverMessageType").getAsString();
      ServerMessage.ServerMessageType messageType =ServerMessage.ServerMessageType.valueOf(typeString);
      return switch (messageType){
        case LOAD_GAME -> context.deserialize(jsonElement, LoadGame.class);
        case ERROR -> context.deserialize(jsonElement, ErrorMsg.class);
        case NOTIFICATION -> context.deserialize(jsonElement, Notification.class);
      };
    }
  }
}
