package server;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class Serializer {
  public String toJSON(Object o){
      var gson = new Gson();
      return gson.toJson(o);
  }

  public <T> T fromJSON(String json, Class<T> genericClass){
    var gson = new Gson();

    return gson.fromJson(json, genericClass);
  }
}
