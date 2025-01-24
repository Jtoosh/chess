package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMoveCalc implements PieceMoveCalc{
  private ArrayList<ChessMove> validMovePositions=new ArrayList<>();
  private ChessPiece[][] currentBoardPieces=new ChessPiece[8][8];
  private final ChessPiece knightPiece;
  private final ChessPosition knightPosition;

  public KnightMoveCalc(ChessBoard currentBoard, ChessPosition piecePosition){
    this.currentBoardPieces =currentBoard.getBoardPieces();
    this.knightPosition = piecePosition;
    this.knightPiece = currentBoardPieces[knightPosition.getRow()-1][knightPosition.getColumn()-1];
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
    checkDestinationSquare(2,1);
    checkDestinationSquare(2,-1);

    checkDestinationSquare(1,2);
    checkDestinationSquare(-1,2);

    checkDestinationSquare(-2,1);
    checkDestinationSquare(-2,-1);

    checkDestinationSquare(-1,-2);
    checkDestinationSquare(1,-2);

    return this.validMovePositions;
  }

  private void checkDestinationSquare (int rowOffset, int colOffset){
    if ((knightPosition.getRow()-1) + rowOffset > 7 || (knightPosition.getColumn()-1) + colOffset > 7 ){
      return;
    }
    if ((knightPosition.getRow()-1) + rowOffset < 0 || (knightPosition.getColumn()-1) + colOffset < 0){
      return;
    }
    ChessPiece currentPiece =this.currentBoardPieces[(knightPosition.getRow()-1) + rowOffset][(knightPosition.getColumn()-1) + colOffset];
    if ( currentPiece == null || !currentPiece.teamColorsEqual(this.knightPiece)){
      ChessPosition newPosition = new ChessPosition(knightPosition.getRow()+rowOffset, knightPosition.getColumn() + colOffset);
      validMovePositions.add(new ChessMove(knightPosition, newPosition,null));
    }
  }
}


