package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalc implements PieceMoveCalc{
  private ArrayList<Integer> validMovePositions =new ArrayList<Integer>();
  private final ChessBoard currentBoard;
  private ChessPiece[][] currentBoardPieces = new ChessPiece[8][8];
  private ChessPosition rookPosition;
  private ChessPiece rookPiece;

  public RookMoveCalc(ChessBoard currentBoard, ChessPosition piecePosition){
    this.currentBoard = currentBoard;
    this.currentBoardPieces =currentBoard.getBoardPieces();
    this.rookPosition = piecePosition;
    this.rookPiece = currentBoardPieces[piecePosition.getRow()][piecePosition.getColumn()];
  }
  @Override
  public Collection<ChessMove> pieceMoves (ChessBoard board, ChessPosition position){
    return null;
  }

  private void positionChecker(int rowOrColToChange, int rowOrColToPersist, String direction){
    for (int i = rowOrColToChange-2; i > 0; i--){
      if (this.currentBoardPieces[rowOrColToPersist][i] != null){
          if(this.currentBoardPieces[rowOrColToPersist][i].getTeamColor() == this.rookPiece.getTeamColor()){
            break;
          } else {
            validMovePositions.add(rowOrColToPersist, i);
          }
      }
    }
  }
}
