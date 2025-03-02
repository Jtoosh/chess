package response;

import model.GameData;

import java.util.Collection;

public record ListResponse(int statusCode, Collection<GameData> games, String errMsg) {
}