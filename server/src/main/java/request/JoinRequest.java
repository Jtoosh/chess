package request;

import chess.ChessGame;

public record JoinRequest(String authToken, String playerColor, int gameID) {
}
