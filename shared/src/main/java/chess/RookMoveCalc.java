package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalc implements PieceMoveCalc{
  private ArrayList<ChessMove> validMovePositions = new ArrayList<>();
  private ChessPiece[][] currentBoardPieces = new ChessPiece[8][8];
  private final ChessPosition rookPosition;
  private final ChessPiece rookPiece;

  public RookMoveCalc(ChessBoard currentBoard, ChessPosition piecePosition){
    this.currentBoardPieces =currentBoard.getBoardPieces();
    this.rookPosition = piecePosition;
    this.rookPiece = currentBoardPieces[piecePosition.getRow()][piecePosition.getColumn()];
  }
  @Override
  public Collection<ChessMove> pieceMoves (ChessBoard board, ChessPosition position){
    positionChecker("left", "row");
    positionChecker("right", "row");
    positionChecker("down", "col");
    positionChecker("up", "col");

    return validMovePositions;
  }

  /* When moving through a row or column looking at rook movements, you can observe a row or column to traverse,
  and the corresponding column or row, respectively, will need to change. The parameters are named following this
  Line of thinking*/
  private void positionChecker(String directionOfTraversal, String typeOfTraversal) {
    int currentRow =rookPosition.getRow();
    int currentCol =rookPosition.getColumn();
    switch (typeOfTraversal) {
      case "row":
        switch (directionOfTraversal){
          case "left":
            for (int i = (currentCol-2); i > 0; i--){
              ChessPiece pieceAtThisSquare = currentBoardPieces[currentRow][i];
              if (pieceAtThisSquare != null && pieceAtThisSquare.getTeamColor() == this.rookPiece.getTeamColor()){
                break;
              } else if (pieceAtThisSquare != null){
                ChessPosition posToAdd = new ChessPosition(currentRow, i) ;
                validMovePositions.add(new ChessMove(this.rookPosition, posToAdd, null));
                break;
              } else{
                ChessPosition posToAdd = new ChessPosition(currentRow, i) ;
                validMovePositions.add(new ChessMove(this.rookPosition, posToAdd, null));
              }
            }
            break;
          case "right":
            for (int i = (currentCol+2); i < 8; i++){
              ChessPiece pieceAtThisSquare = currentBoardPieces[currentRow][i];
              if (pieceAtThisSquare != null && pieceAtThisSquare.getTeamColor() == this.rookPiece.getTeamColor()){
                break;
              } else if(pieceAtThisSquare != null){
                ChessPosition posToAdd = new ChessPosition(currentRow, i);
                validMovePositions.add(new ChessMove(this.rookPosition, posToAdd, null));
                break;
              } else {
                ChessPosition posToAdd = new ChessPosition(currentRow, i);
                validMovePositions.add(new ChessMove(this.rookPosition, posToAdd, null));
              }
            }
            break;
        }
        break;
      case "col":
        switch (directionOfTraversal){
          case "down":
            for (int i = (currentRow-2);i > 0; i--){
              ChessPiece pieceAtThisSquare = currentBoardPieces[i][currentCol];
              if (pieceAtThisSquare != null && pieceAtThisSquare.getTeamColor() == this.rookPiece.getTeamColor()){
                break;
              } else if (pieceAtThisSquare != null){
                ChessPosition posToAdd = new ChessPosition(i, currentCol);
                validMovePositions.add(new ChessMove(this.rookPosition, posToAdd, null));
                break;
              } else {
                ChessPosition posToAdd = new ChessPosition(i, currentCol);
                validMovePositions.add(new ChessMove(this.rookPosition, posToAdd, null));
              }
            }

            }
            break;
          case "up":
            for (int i = (currentRow+2);i < 8; i++){
              ChessPiece pieceAtThisSquare = currentBoardPieces[i][currentCol];
              if (pieceAtThisSquare != null && pieceAtThisSquare.getTeamColor() == this.rookPiece.getTeamColor()){
                break;
              } else if (pieceAtThisSquare != null){
                ChessPosition posToAdd = new ChessPosition(i, currentCol);
                validMovePositions.add(new ChessMove(this.rookPosition, posToAdd, null));
                break;
              } else {
                ChessPosition posToAdd = new ChessPosition(i, currentCol);
                validMovePositions.add(new ChessMove(this.rookPosition, posToAdd, null));
              }
            }
    }
  }

}

