package client;

import com.google.gson.*;
import websocket.messages.ServerMessage;

import java.lang.reflect.Type;

public class ClientSerializer {
  public String toJSON(Object o){
    var gson = new Gson();
    return gson.toJson(o);
  }

  public <T> T fromJSON(String json, Class<T> genericClass){
    var gson = new Gson();

    return gson.fromJson(json, genericClass);
  }

  private class ServerMessageDeserializer implements JsonDeserializer<ServerMessage>{

    @Override
    public ServerMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
      JsonObject parsedObject = jsonElement.getAsJsonObject();

      String typeString = parsedObject.get("serverMessageType").getAsString();
      ServerMessage.ServerMessageType messageType =ServerMessage.ServerMessageType.valueOf(typeString);
      return switch (messageType){
//        case NOTIFICATION -> context.deserialize(jsonElement, Ser);
      };
    }
  }
}
