package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalc implements PieceMoveCalc {
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

  /**
   * Performs the inner functionality of the column loops, with the direction parameterized, due to the need
   * to go in different directions
   *
   * @param i the index to access
   * @return True if the loop is complete, False if it needs to continue
   **/
  private boolean columnLoop(int i) {
    int furthestSquareUp;
    if (currentBoardPieces[i][rookPosition.getColumn()-1] == null){
      furthestSquareUp = i+1;
      ChessPosition newPostition=new ChessPosition(furthestSquareUp, rookPosition.getColumn());
      validMovePositions.add(new ChessMove(rookPosition, newPostition, null));
      return false;
    }else if (currentBoardPieces[i][rookPosition.getColumn()-1].getTeamColor() != rookPiece.getTeamColor()){
      furthestSquareUp = i+1;
      ChessPosition newPostition=new ChessPosition(furthestSquareUp, rookPosition.getColumn());
      validMovePositions.add(new ChessMove(rookPosition, newPostition, null));
      return true;
    } else{return true;}
  }

  private void furthestMoveUp() {
    int furthestSquareUp = rookPosition.getRow();
    for (int i = furthestSquareUp; i <= 7; i++){
      if (columnLoop(i)) break;
    }
  }

  private void furthestMoveDown (){
    int furthestSquareDown =rookPosition.getRow()-2;
    for (int i =furthestSquareDown; i >= 0; i--){
      if (columnLoop(i)) break;
    }
  }

  private boolean rowLoop(int i) {
    int furthestSquareRight;
    if (currentBoardPieces[rookPosition.getRow()-1][i] ==null){
      furthestSquareRight = i+1;
      ChessPosition newPostition=new ChessPosition(rookPosition.getRow(), furthestSquareRight);
      validMovePositions.add(new ChessMove(rookPosition, newPostition, null));
      return false;
    }else if (currentBoardPieces[rookPosition.getRow()-1][i].getTeamColor() != rookPiece.getTeamColor()){
      furthestSquareRight = i+1;
      ChessPosition newPostition=new ChessPosition(rookPosition.getRow(), furthestSquareRight);
      validMovePositions.add(new ChessMove(rookPosition, newPostition, null));
      return true;
    }else{return true;}
  }

  private void furthestMoveRight (){
    int furthestSquareRight =rookPosition.getColumn();
    for (int i =furthestSquareRight;i <= 7; i++){
      if (rowLoop(i)) break;
    }
  }

  private void furthestMoveLeft (){
    int furthestSquareLeft =rookPosition.getColumn()-2;
    for (int i =furthestSquareLeft;i >= 0; i--){
      if (rowLoop(i)) break;
    }
  }




}

