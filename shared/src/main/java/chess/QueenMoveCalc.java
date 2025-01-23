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
    StraightOrDiagonalPiece.furthestTopRight(this.currentBoardPieces, this.queenPosition, this.queenPiece, this.validMovePositions);
    StraightOrDiagonalPiece.furthestTopLeft(this.currentBoardPieces, this.queenPosition, this.queenPiece, this.validMovePositions);
    StraightOrDiagonalPiece.furthestBottomRight(this.currentBoardPieces, this.queenPosition, this.queenPiece, this.validMovePositions);
    StraightOrDiagonalPiece.furthestBottomLeft(this.currentBoardPieces, this.queenPosition, this.queenPiece, this.validMovePositions);
    furthestMoveUp();
    furthestMoveDown();
    furthestMoveRight();
    furthestMoveLeft();
    return this.validMovePositions;
  }

  private void furthestMoveUp() {
    int furthestSqaureUp =queenPosition.getRow();
    for (int i = furthestSqaureUp; i <= 7; i++){
      if (StraightOrDiagonalPiece.columnLoop(i, this.currentBoardPieces, this.queenPosition, this.queenPiece, this.validMovePositions)) {break;}
    }
  }

  private void furthestMoveDown() {
    int furthestSqaureUp =queenPosition.getRow() - 2;
    for (int i = furthestSqaureUp; i >= 0; i--){
      if (StraightOrDiagonalPiece.columnLoop(i, this.currentBoardPieces, this.queenPosition, this.queenPiece, this.validMovePositions)){break;}
    }
  }

  private void furthestMoveRight (){
    int furthestSquareRight =queenPosition.getColumn();
    for (int i =furthestSquareRight;i <= 7; i++){
      if (StraightOrDiagonalPiece.rowLoop(i, this.currentBoardPieces, this.queenPosition, this.queenPiece, this.validMovePositions)) break;
    }
  }

  private void furthestMoveLeft (){
    int furthestSquareLeft =queenPosition.getColumn()-2;
    for (int i =furthestSquareLeft;i >= 0; i--){
      if (StraightOrDiagonalPiece.rowLoop(i, this.currentBoardPieces, this.queenPosition, this.queenPiece, this.validMovePositions)) break;
    }
  }

}
