package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMoveCalc implements StraightOrDiagonalPiece{
  private ArrayList<ChessMove> validMovePositions=new ArrayList<>();
  private ChessPiece[][] currentBoardPieces=new ChessPiece[8][8];
  private final ChessPiece bishopPiece;
  private final ChessPosition bishopPosition;

  public BishopMoveCalc(ChessBoard currentBoard, ChessPosition currentPosition){
    this.currentBoardPieces = currentBoard.getBoardPieces();
    this.bishopPosition = currentPosition;
    this.bishopPiece = currentBoardPieces[bishopPosition.getRow()-1][bishopPosition.getColumn()-1];
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
    StraightOrDiagonalPiece.furthestTopRight(this.currentBoardPieces, this.bishopPosition, this.bishopPiece, this.validMovePositions);
    StraightOrDiagonalPiece.furthestTopLeft(this.currentBoardPieces, this.bishopPosition, this.bishopPiece, this.validMovePositions);
    StraightOrDiagonalPiece.furthestBottomRight(this.currentBoardPieces, this.bishopPosition, this.bishopPiece, this.validMovePositions);
    StraightOrDiagonalPiece.furthestBottomLeft(this.currentBoardPieces, this.bishopPosition, this.bishopPiece, this.validMovePositions);
    return validMovePositions;
  }
}

