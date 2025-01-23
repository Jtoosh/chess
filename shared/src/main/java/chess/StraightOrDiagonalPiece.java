package chess;

import java.util.ArrayList;

public interface StraightOrDiagonalPiece extends PieceMoveCalc {

  /**
   * Performs the inner functionality of the column loops, with the direction parameterized, due to the need
   *    * to go in different directions
   *
   * @param i index to access
   * @param currentBoardPieces the pieces on the ChessBoard that the calling instance is on
   * @param piecePosition the ChessPosition of the calling instance
   * @param piece the ChessPiece instance of the calling instance
   * @param validMovePositions the Collection of valid moves for the calling instance.
   * @return True if the loop is complete, False if it needs to continue
   */
  static boolean columnLoop(int i, ChessPiece[][] currentBoardPieces, ChessPosition piecePosition, ChessPiece piece, ArrayList<ChessMove> validMovePositions) {
    int furthestSquareUp;

    if (currentBoardPieces[i][piecePosition.getColumn()-1] == null){
      furthestSquareUp = i+1;
      ChessPosition newPostition=new ChessPosition(furthestSquareUp, piecePosition.getColumn());
      validMovePositions.add(new ChessMove(piecePosition, newPostition, null));
      return false;
    }else if (!currentBoardPieces[i][piecePosition.getColumn()-1].teamColorsEqual(piece)){
      furthestSquareUp = i+1;
      ChessPosition newPostition=new ChessPosition(furthestSquareUp, piecePosition.getColumn());
      validMovePositions.add(new ChessMove(piecePosition, newPostition, null));
      return true;
    } else{return true;}
  }

  /**
   * Performs the inner functionality of the row loops, with the direction parameterized, due to the need
   *    * to go in different directions
   *
   * @param i index to access
   * @param currentBoardPieces the pieces on the ChessBoard that the calling instance is on
   * @param piecePosition the ChessPosition of the calling instance
   * @param piece the ChessPiece instance of the calling instance
   * @param validMovePositions the Collection of valid moves for the calling instance.
   * @return True if the loop is complete, False if it needs to continue
   */
  static boolean rowLoop(int i, ChessPiece[][] currentBoardPieces, ChessPosition piecePosition, ChessPiece piece, ArrayList<ChessMove> validMovePositions) {
    int furthestSquareRight;
    if (currentBoardPieces[piecePosition.getRow()-1][i] ==null){
      furthestSquareRight = i+1;
      ChessPosition newPostition=new ChessPosition(piecePosition.getRow(), furthestSquareRight);
      validMovePositions.add(new ChessMove(piecePosition, newPostition, null));
      return false;
    }else if (!currentBoardPieces[piecePosition.getRow()-1][i].teamColorsEqual(piece)){
      furthestSquareRight = i+1;
      ChessPosition newPostition=new ChessPosition(piecePosition.getRow(), furthestSquareRight);
      validMovePositions.add(new ChessMove(piecePosition, newPostition, null));
      return true;
    }else{return true;}
  }

  static void furthestTopRight (ChessPiece[][] currentBoardPieces, ChessPosition piecePosition, ChessPiece piece, ArrayList<ChessMove> validMovePositions){
    for (int i = 0; i <= 7; i++){
      if((piecePosition.getRow() + i) > 7 || (piecePosition.getColumn() + i) > 7) {break;}
      if (currentBoardPieces[piecePosition.getRow() + i][piecePosition.getColumn() + i] == null){
        ChessPosition newPosition = new ChessPosition((piecePosition.getRow()+i + 1),(piecePosition.getColumn()+ i + 1));
        validMovePositions.add(new ChessMove(piecePosition, newPosition, null));
      } else if (!currentBoardPieces[piecePosition.getRow() + i][piecePosition.getColumn() + i].teamColorsEqual(piece)) {
        ChessPosition newPosition = new ChessPosition((piecePosition.getRow()+i + 1),(piecePosition.getColumn()+ i + 1));
        validMovePositions.add(new ChessMove(piecePosition, newPosition, null));
        break;
      } else {break;}
    }
  }

  static void furthestTopLeft(ChessPiece[][] currentBoardPieces, ChessPosition piecePosition, ChessPiece piece, ArrayList<ChessMove> validMovePositions){
    for (int i = 0; i <= 7; i++){
      if (piecePosition.getRow() + i > 7 || piecePosition.getColumn() - i - 2 < 0){break;}
      if (currentBoardPieces[piecePosition.getRow()+i][piecePosition.getColumn()-i - 2] == null){
        ChessPosition newPosition = new ChessPosition((piecePosition.getRow()+i + 1),(piecePosition.getColumn()- i - 1));
        validMovePositions.add(new ChessMove(piecePosition, newPosition, null));
      } else if (!currentBoardPieces[piecePosition.getRow() + i][piecePosition.getColumn() - i - 2].teamColorsEqual(piece)) {
        ChessPosition newPosition = new ChessPosition((piecePosition.getRow()+i + 1),(piecePosition.getColumn()+ i - 1));
        validMovePositions.add(new ChessMove(piecePosition, newPosition, null));
        break;
      } else {break;}
    }
  }

  static void furthestBottomRight(ChessPiece[][] currentBoardPieces, ChessPosition piecePosition, ChessPiece piece, ArrayList<ChessMove> validMovePositions){
    for (int i = 0; i<=7; i++){
      if (piecePosition.getRow() - i - 2 < 0 || piecePosition.getColumn() + i > 7){break;}
      if (currentBoardPieces[piecePosition.getRow() - i - 2][piecePosition.getColumn() + i] == null){
        ChessPosition newPosition = new ChessPosition((piecePosition.getRow()-i - 1),(piecePosition.getColumn()+ i + 1));
        validMovePositions.add(new ChessMove(piecePosition, newPosition, null));
      } else if (!currentBoardPieces[piecePosition.getRow() - i - 2][piecePosition.getColumn() + i].teamColorsEqual(piece)) {
        ChessPosition newPosition = new ChessPosition((piecePosition.getRow()-i - 1),(piecePosition.getColumn()+ i + 1));
        validMovePositions.add(new ChessMove(piecePosition, newPosition, null));
        break;
      } else {break;}
    }
  }

  static void furthestBottomLeft(ChessPiece[][] currentBoardPieces, ChessPosition piecePosition, ChessPiece piece, ArrayList<ChessMove> validMovePositions){
    for (int i = 0; i <= 7; i++){
      if ((piecePosition.getRow() - i) -2 < 0 || (piecePosition.getColumn() - i) -2  < 0){break;}
      if (currentBoardPieces[piecePosition.getRow() - i - 2][piecePosition.getColumn() - i - 2] == null){
        ChessPosition newPosition = new ChessPosition((piecePosition.getRow()-i - 1),(piecePosition.getColumn()- i - 1));
        validMovePositions.add(new ChessMove(piecePosition, newPosition, null));
      } else if (!currentBoardPieces[piecePosition.getRow() - i -2 ][piecePosition.getColumn() - i -2 ].teamColorsEqual(piece)){
        ChessPosition newPosition = new ChessPosition((piecePosition.getRow()-i - 1),(piecePosition.getColumn()- i - 1));
        validMovePositions.add(new ChessMove(piecePosition, newPosition, null));
        break;
      } else {break;}
    }
  }
}
