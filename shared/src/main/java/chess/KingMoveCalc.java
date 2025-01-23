package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMoveCalc implements PieceMoveCalc{
  private ArrayList<ChessMove> validMovePositions=new ArrayList<>();
  private ChessPiece[][] currentBoardPieces=new ChessPiece[8][8];
  private final ChessPiece kingPiece;
  private final ChessPosition kingPosition;

  public KingMoveCalc(ChessBoard currentBoard, ChessPosition piecePosition){
    this.currentBoardPieces =currentBoard.getBoardPieces();
    this.kingPosition = piecePosition;
    this.kingPiece = currentBoardPieces[kingPosition.getRow()-1][kingPosition.getColumn()-1];
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
    checkAdjacentSquare(1,0);
    checkAdjacentSquare(1, 1);
    checkAdjacentSquare(0,1);
    checkAdjacentSquare(-1, 1);
    checkAdjacentSquare(-1,0);
    checkAdjacentSquare(-1,-1);
    checkAdjacentSquare(0, -1);
    checkAdjacentSquare(1,-1);

    return this.validMovePositions;
  }

  private void checkAdjacentSquare (int rowOffset, int colOffset){
    if ((kingPosition.getRow()-1) + rowOffset >= 8 || (kingPosition.getRow()-1) + colOffset >= 8){
      return;
    }
    ChessPiece currentPiece =this.currentBoardPieces[(kingPosition.getRow()-1) + rowOffset][(kingPosition.getColumn()-1) + colOffset];
    if ( currentPiece == null || !currentPiece.teamColorsEqual(this.kingPiece)){
      ChessPosition newPosition = new ChessPosition(kingPosition.getRow()+rowOffset, kingPosition.getColumn() + colOffset);
      validMovePositions.add(new ChessMove(kingPosition, newPosition,null));
    }
  }
}
