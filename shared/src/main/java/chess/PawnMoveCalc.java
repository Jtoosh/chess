package chess;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMoveCalc implements PieceMoveCalc {
  private ArrayList<ChessMove> validMovePositions=new ArrayList<>();
  private ChessPiece[][] currentBoardPieces=new ChessPiece[8][8];
  private final ChessPiece pawnPiece;
  private final ChessPosition pawnPosition;
  private boolean initialMove;

  public PawnMoveCalc(ChessBoard currentBoard, ChessPosition piecePosition) {
    this.currentBoardPieces=currentBoard.getBoardPieces();
    this.pawnPosition=piecePosition;
    this.pawnPiece=currentBoardPieces[pawnPosition.getRow() - 1][pawnPosition.getColumn() - 1];
    this.initialMove=pawnPosition.getRow() == 2 || this.pawnPosition.getRow() == 7;
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
    if (this.initialMove) {
      checkInitialMove();
    }
    return List.of();
  }

  private void checkInitialMove() {
    if (currentBoardPieces[(pawnPosition.getRow() - 1) + 1][(pawnPosition.getColumn() - 1)] == null) {
      ChessPosition newPosition=new ChessPosition(pawnPosition.getRow() + 1, pawnPosition.getColumn());
      validMovePositions.add(new ChessMove(pawnPosition, newPosition, null));
      this.initialMove=false;
    }
    if (currentBoardPieces[(pawnPosition.getRow() - 1) + 2][(pawnPosition.getColumn() - 1)] == null) {
      ChessPosition newPosition=new ChessPosition(pawnPosition.getRow() + 2, pawnPosition.getColumn());
      validMovePositions.add(new ChessMove(pawnPosition, newPosition, null));
      this.initialMove=false;
    }
  }

  private void checkCaptureMove(){
    ChessPiece leftCaptureSqaure = currentBoardPieces[(pawnPosition.getRow() - 1) + 1][(pawnPosition.getColumn() - 1)-1];
    ChessPiece rightCaptureSqaure = currentBoardPieces[(pawnPosition.getRow() - 1) + 1][(pawnPosition.getColumn() - 1)+1];
    ChessPiece forwardSquare = currentBoardPieces[(pawnPosition.getRow() - 1) + 1][(pawnPosition.getColumn() - 1)];
    ChessPiece [] potentialMoves = {leftCaptureSqaure, rightCaptureSqaure, forwardSquare};
    
    if (leftCaptureSqaure == null && rightCaptureSqaure == null){
      return;
    }
    for (ChessPiece square : potentialMoves){
      if (square == null){
        break;
      } else{
        ChessPosition newPosition=new ChessPosition();
      }
    }


  }

}






