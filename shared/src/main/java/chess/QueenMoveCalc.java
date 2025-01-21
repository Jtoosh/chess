package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMoveCalc implements PieceMoveCalc{
  private ArrayList<ChessMove> validMovePositions=new ArrayList<>();
  private ChessPiece[][] currentBoardPieces=new ChessPiece[8][8];
  private final ChessPiece queenPiece;
  private final ChessPosition queenPosition;

  public QueenMoveCalc(ChessBoard currentBoard, ChessPosition piecePosition){
    this.currentBoardPieces = currentBoard.getBoardPieces();
    this.queenPosition = piecePosition;
    this.queenPiece = currentBoardPieces[queenPosition.getRow()-1][queenPosition.getColumn()-1];
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
    return List.of();
  }
}
