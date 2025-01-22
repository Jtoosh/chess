package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMoveCalc implements PieceMoveCalc{
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
    furthestTopRight();
    furthestTopLeft();
    furthestBottomRight();
    furthestBottomLeft();
    return validMovePositions;
  }

  private void furthestTopRight (){
    for (int i = 0; i <= 7; i++){
      if((bishopPosition.getRow() + i) > 7 || (bishopPosition.getColumn() + i) > 7) {break;}
      if (currentBoardPieces[bishopPosition.getRow() + i][bishopPosition.getColumn() + i] == null){
        ChessPosition newPosition = new ChessPosition((bishopPosition.getRow()+i + 1),(bishopPosition.getColumn()+ i + 1));
        validMovePositions.add(new ChessMove(bishopPosition, newPosition, null));
      } else if (currentBoardPieces[bishopPosition.getRow() + i][bishopPosition.getColumn() + i].getTeamColor() != bishopPiece.getTeamColor()) {
        ChessPosition newPosition = new ChessPosition((bishopPosition.getRow()+i + 1),(bishopPosition.getColumn()+ i + 1));
        validMovePositions.add(new ChessMove(bishopPosition, newPosition, null));
        break;
      } else {break;}
    }
  }

  private void furthestTopLeft(){
    for (int i = 0; i <= 7; i++){
      if (bishopPosition.getRow() + i > 7 || bishopPosition.getColumn() - i - 2 < 0){break;}
      if (currentBoardPieces[bishopPosition.getRow()+i][bishopPosition.getColumn()-i - 2] == null){
        ChessPosition newPosition = new ChessPosition((bishopPosition.getRow()+i + 1),(bishopPosition.getColumn()- i - 1));
        validMovePositions.add(new ChessMove(bishopPosition, newPosition, null));
      } else if (currentBoardPieces[bishopPosition.getRow() + i][bishopPosition.getColumn() - i - 2].getTeamColor() != bishopPiece.getTeamColor()) {
        ChessPosition newPosition = new ChessPosition((bishopPosition.getRow()+i + 1),(bishopPosition.getColumn()+ i - 1));
        validMovePositions.add(new ChessMove(bishopPosition, newPosition, null));
        break;
      } else {break;}
    }
  }

  private void furthestBottomRight(){
    for (int i = 0; i<=7; i++){
      if (bishopPosition.getRow() - i - 2 < 0 || bishopPosition.getColumn() + i > 7){break;}
      if (currentBoardPieces[bishopPosition.getRow() - i - 2][bishopPosition.getColumn() + i] == null){
        ChessPosition newPosition = new ChessPosition((bishopPosition.getRow()-i - 1),(bishopPosition.getColumn()+ i + 1));
        validMovePositions.add(new ChessMove(bishopPosition, newPosition, null));
      } else if (currentBoardPieces[bishopPosition.getRow() - i - 2][bishopPosition.getColumn() + i].getTeamColor() != bishopPiece.getTeamColor()) {
        ChessPosition newPosition = new ChessPosition((bishopPosition.getRow()-i - 1),(bishopPosition.getColumn()+ i + 1));
        validMovePositions.add(new ChessMove(bishopPosition, newPosition, null));
        break;
      } else {break;}
    }
  }

  private void furthestBottomLeft(){
    for (int i = 0; i <= 7; i++){
      if ((bishopPosition.getRow() - i) -2 < 0 || (bishopPosition.getColumn() - i) -2  < 0){break;}
      if (currentBoardPieces[bishopPosition.getRow() - i - 2][bishopPosition.getColumn() - i - 2] == null){
        ChessPosition newPosition = new ChessPosition((bishopPosition.getRow()-i - 1),(bishopPosition.getColumn()- i - 1));
        validMovePositions.add(new ChessMove(bishopPosition, newPosition, null));
      } else if (currentBoardPieces[bishopPosition.getRow() - i -2 ][bishopPosition.getColumn() - i -2 ].getTeamColor() != bishopPiece.getTeamColor()){
        ChessPosition newPosition = new ChessPosition((bishopPosition.getRow()-i - 1),(bishopPosition.getColumn()- i - 1));
        validMovePositions.add(new ChessMove(bishopPosition, newPosition, null));
        break;
      } else {break;}
    }
  }

}

