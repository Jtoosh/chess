package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMoveCalc implements PieceMoveCalc{
  private ArrayList<ChessMove> validMovePositions=new ArrayList<>();
  private ChessPiece[][] currentBoardPieces=new ChessPiece[8][8];
  private final ChessPiece kingPiece;
  private final ChessPosition kingPosition;

  public KingMoveCalc(ChessBoard currentBoard, ChessPosition piecePosition){
    this.currentBoardPieces =currentBoard.getBoardPieces();
    this.kingPosition = piecePosition;
    this.kingPiece = currentBoardPieces[kingPosition.getRow()-1][kingPosition.getColumn()-1];
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
    return List.of();
  }
}
