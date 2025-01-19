package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalc implements PieceMoveCalc{
  private ArrayList<ChessMove> validMovePositions = new ArrayList<>();
  private ChessPiece[][] currentBoardPieces = new ChessPiece[8][8];
  private final ChessPiece rookPiece;
  private final ChessPosition rookPosition;

  public RookMoveCalc(ChessBoard currentBoard, ChessPosition piecePosition){
    this.currentBoardPieces =currentBoard.getBoardPieces();
    this.rookPosition = piecePosition;
    this.rookPiece = currentBoardPieces[piecePosition.getRow()][piecePosition.getColumn()];
  }
  @Override
  public Collection<ChessMove> pieceMoves (ChessBoard board, ChessPosition position){
    for (int i = 0; i < 8; i++){
      ChessPosition currentPosition = new ChessPosition(rookPosition.getRow(), i+1);
      if (currentPosition.equals(rookPosition)){continue;}
      validMovePositions.add(new ChessMove(rookPosition, currentPosition, null));
    }
    for (int i = 0; i < 8; i++){
      ChessPosition currentPosition = new ChessPosition(i+1, rookPosition.getColumn());
      if (currentPosition.equals(rookPosition)){continue;}
      validMovePositions.add(new ChessMove(rookPosition, currentPosition, null));
    }
    System.out.println(validMovePositions.toString());
    return validMovePositions;
  }



}

