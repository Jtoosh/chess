package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalc implements StraightOrDiagonalPiece {
  private ArrayList<ChessMove> validMovePositions=new ArrayList<>();
  private ChessPiece[][] currentBoardPieces=new ChessPiece[8][8];
  private final ChessPiece rookPiece;
  private final ChessPosition rookPosition;

  public RookMoveCalc(ChessBoard currentBoard, ChessPosition piecePosition) {
    this.currentBoardPieces=currentBoard.getBoardPieces();
    this.rookPosition=piecePosition;
    this.rookPiece=currentBoardPieces[piecePosition.getRow() - 1][piecePosition.getColumn() - 1];
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
    furthestMoveUp();
    furthestMoveDown();
    furthestMoveRight();
    furthestMoveLeft();

    return validMovePositions;
  }

  private void furthestMoveUp() {
    int furthestSquareUp = rookPosition.getRow();
    for (int i = furthestSquareUp; i <= 7; i++){
      if (StraightOrDiagonalPiece.columnLoop(i,this.currentBoardPieces, this.rookPosition, this.rookPiece, this.validMovePositions)) break;
    }
  }

  private void furthestMoveDown (){
    int furthestSquareDown =rookPosition.getRow()-2;
    for (int i =furthestSquareDown; i >= 0; i--){
      if (StraightOrDiagonalPiece.columnLoop(i,this.currentBoardPieces, this.rookPosition, this.rookPiece, this.validMovePositions)) break;
    }
  }

  private void furthestMoveRight (){
    int furthestSquareRight =rookPosition.getColumn();
    for (int i =furthestSquareRight;i <= 7; i++){
      if (StraightOrDiagonalPiece.rowLoop(i, this.currentBoardPieces, this.rookPosition, this.rookPiece, this.validMovePositions)) break;
    }
  }

  private void furthestMoveLeft (){
    int furthestSquareLeft =rookPosition.getColumn()-2;
    for (int i =furthestSquareLeft;i >= 0; i--){
      if (StraightOrDiagonalPiece.rowLoop(i, this.currentBoardPieces, this.rookPosition, this.rookPiece, this.validMovePositions)) break;
    }
  }




}

