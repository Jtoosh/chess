package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMoveCalc implements PieceMoveCalc{
  private ArrayList<ChessMove> validMovePositions=new ArrayList<>();
  private ChessPiece[][] currentBoardPieces=new ChessPiece[8][8];
  private final ChessPiece pawnPiece;
  private final ChessPosition pawnPosition;

  public PawnMoveCalc(ChessBoard currentBoard, ChessPosition piecePosition){
    this.currentBoardPieces =currentBoard.getBoardPieces();
    this.pawnPosition = piecePosition;
    this.pawnPiece = currentBoardPieces[pawnPosition.getRow()-1][pawnPosition.getColumn()-1];
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
    return List.of();
  }
}
