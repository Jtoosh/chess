package response;

import model.GameData;

public record ListResponse(GameData[] games) {
}