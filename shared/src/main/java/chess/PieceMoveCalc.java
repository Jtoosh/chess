package chess;

import java.util.Collection;

public interface PieceMoveCalc {
  public Collection<ChessMove> pieceMoves (ChessBoard board, ChessPosition position);

}
