package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
  private ChessMove move;

  public MakeMoveCommand( String authToken, Integer gameID, ChessMove moveArg) {
    super(CommandType.MAKE_MOVE, authToken, gameID);
    this.move = moveArg;
  }

  public ChessMove getMoveToMake(){
    return this.move;
  }
}
