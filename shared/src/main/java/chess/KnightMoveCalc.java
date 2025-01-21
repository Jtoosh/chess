package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMoveCalc implements PieceMoveCalc{
  private ArrayList<ChessMove> validMovePositions=new ArrayList<>();
  private ChessPiece[][] currentBoardPieces=new ChessPiece[8][8];
  private final ChessPiece knightPiece;
  private final ChessPosition knightPosition;

  public KnightMoveCalc(ChessBoard currentBoard, ChessPosition piecePosition){
    this.currentBoardPieces =currentBoard.getBoardPieces();
    this.knightPosition = piecePosition;
    this.knightPiece = currentBoardPieces[knightPosition.getRow()-1][knightPosition.getColumn()-1];
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
    return List.of();
  }
}
