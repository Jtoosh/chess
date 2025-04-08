package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
  private ChessMove moveToMake;
  public MakeMoveCommand(CommandType commandType, String authToken, Integer gameID, ChessMove move) {
    super(commandType, authToken, gameID);
    this.moveToMake = move;
  }
}
