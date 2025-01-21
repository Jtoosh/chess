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
    furthestMoveUp(7);
    furthestMoveDown(0);
    furthestMoveRight(7);
    furthestMoveLeft(0);

    return validMovePositions;
  }

  private void furthestMoveUp(int max) {
    if (max == rookPosition.getRow()-1){return;}
    int furthestSquareUp = rookPosition.getRow()-1;
    for (int i = rookPosition.getRow(); i <= max; i++){
      if (currentBoardPieces[i][rookPosition.getColumn()-1] == null){
        furthestSquareUp = i+1;
      }else if (currentBoardPieces[i][rookPosition.getColumn()-1].getTeamColor() != rookPiece.getTeamColor()){
        furthestSquareUp = i+1;
        break;
      } else{break;}
    }
    if (furthestSquareUp != max) {
      ChessPosition newPostition=new ChessPosition(rookPosition.getRow(), furthestSquareUp);
      validMovePositions.add(new ChessMove(rookPosition, newPostition, null));
    }
    furthestMoveUp(max - 1);
  }
  private void furthestMoveDown (int min){
    if (min == rookPosition.getRow()-1) {return;}
    int furthestSquareDown =rookPosition.getRow()-1;
    for (int i =rookPosition.getRow() - 2; i >= min; i--){
      if(currentBoardPieces[i][rookPosition.getColumn()-1] == null){
        furthestSquareDown = i + 1;
      }else if (currentBoardPieces[i][rookPosition.getColumn()-1].getTeamColor() != rookPiece.getTeamColor()){
        furthestSquareDown = i+1;
      }else{break;}
      break;
    }
    if (furthestSquareDown != min) {
      ChessPosition newPostition=new ChessPosition(rookPosition.getRow(), furthestSquareDown);
      validMovePositions.add(new ChessMove(rookPosition, newPostition, null));
    }
    furthestMoveDown(min + 1);
  }

  private void furthestMoveRight (int max){
    if (max == rookPosition.getColumn()-1){return;}
    int furthestSquareRight =rookPosition.getColumn()-1;
    for (int i =rookPosition.getColumn();i <= max; i++){
      if (currentBoardPieces[rookPosition.getRow()-1][i] ==null){
        furthestSquareRight = i+1;
      }else if (currentBoardPieces[rookPosition.getRow()-1][i].getTeamColor() != rookPiece.getTeamColor()){
        furthestSquareRight = i+1;
        break;
      }else{break;}
    }
    if (furthestSquareRight != max) {
      ChessPosition newPostition=new ChessPosition(rookPosition.getRow(), furthestSquareRight);
      validMovePositions.add(new ChessMove(rookPosition, newPostition, null));
    }
    furthestMoveRight(max - 1);
  }

  private void furthestMoveLeft (int min){
    if (min == rookPosition.getColumn()){return;}
    int furthestSquareLeft =rookPosition.getColumn()-1;
    for (int i =rookPosition.getColumn()-2;i >= min; i--){
      if (currentBoardPieces[rookPosition.getRow()-1][i] ==null){
        furthestSquareLeft = i+1;
      } else if (currentBoardPieces[rookPosition.getRow()-1][i].getTeamColor() != rookPiece.getTeamColor()){
        furthestSquareLeft = i+1;
        break;
      }else{break;}
    }
    if (furthestSquareLeft != min) {
      ChessPosition newPostition=new ChessPosition(rookPosition.getRow(), furthestSquareLeft);
      validMovePositions.add(new ChessMove(rookPosition, newPostition, null));
    }
    furthestMoveLeft(min + 1);
  }




}

