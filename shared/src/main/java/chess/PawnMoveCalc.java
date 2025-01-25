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
  private int direction;

  public PawnMoveCalc(ChessBoard currentBoard, ChessPosition piecePosition) {
    this.currentBoardPieces=currentBoard.getBoardPieces();
    this.pawnPosition=piecePosition;
    this.pawnPiece=currentBoardPieces[pawnPosition.getRow() - 1][pawnPosition.getColumn() - 1];
    this.initialMove=(pawnPosition.getRow() == 2 && pawnPiece.getTeamColor() == ChessGame.TeamColor.WHITE) || (this.pawnPosition.getRow() == 7&& pawnPiece.getTeamColor() == ChessGame.TeamColor.BLACK);
    this.direction = (pawnPiece.getTeamColor() == ChessGame.TeamColor.WHITE) ? 1 : -1;
  }

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
    ChessPiece oneMoveForward = currentBoardPieces[(pawnPosition.getRow()-1) + (direction)][(pawnPosition.getColumn()-1)];
    if(oneMoveForward == null){
      ChessPosition newPosition = new ChessPosition(pawnPosition.getRow() + direction, pawnPosition.getColumn());
      if (checkForPromotion(newPosition.getRow())){
        //Call promotion moves method
        this.allPromotionTypes(newPosition);
      } else{
        validMovePositions.add(new ChessMove(pawnPosition,newPosition, null ));
      }
    }

    if ((pawnPosition.getColumn() - 1) > 0){
      ChessPiece potentialCaptureLeft=currentBoardPieces[(pawnPosition.getRow() - 1) + direction][(pawnPosition.getColumn() - 1) - 1];
      if (potentialCaptureLeft != null && !potentialCaptureLeft.teamColorsEqual(pawnPiece)) {
        ChessPosition newPosition=new ChessPosition(pawnPosition.getRow() + direction, pawnPosition.getColumn() - 1);
        if (checkForPromotion(newPosition.getRow())){
          //Call promotion moves method
          this.allPromotionTypes(newPosition);
        } else{
          validMovePositions.add(new ChessMove(pawnPosition,newPosition, null ));
        }
      }
    }
    if ((pawnPosition.getColumn() - 1) < 7){
      ChessPiece potentialCaptureRight=currentBoardPieces[(pawnPosition.getRow() - 1) + direction][(pawnPosition.getColumn() - 1) + 1];
      if (potentialCaptureRight != null && !potentialCaptureRight.teamColorsEqual(pawnPiece)) {
        ChessPosition newPosition=new ChessPosition(pawnPosition.getRow() + direction, pawnPosition.getColumn() + 1);
        if (checkForPromotion(newPosition.getRow())){
          //Call promotion moves method
          this.allPromotionTypes(newPosition);
        } else{
          validMovePositions.add(new ChessMove(pawnPosition,newPosition, null ));
        }
      }
    }
    if (initialMove && currentBoardPieces[(pawnPosition.getRow()-1) + (2 * direction)][(pawnPosition.getColumn()-1)] == null && oneMoveForward == null){
      ChessPosition newPosition = new ChessPosition(pawnPosition.getRow() + (2 * direction), pawnPosition.getColumn());
      validMovePositions.add(new ChessMove(pawnPosition,newPosition, null ));
    }
    return validMovePositions;
  }

  private boolean checkForPromotion(int newRow){
    if (this.pawnPiece.getTeamColor() == ChessGame.TeamColor.WHITE && newRow == 8){
      return true;
    } else if (this.pawnPiece.getTeamColor() == ChessGame.TeamColor.BLACK && newRow == 1){
      return true;
    } else { return false;}
  }

  private void allPromotionTypes(ChessPosition newPosition){
    validMovePositions.add(new ChessMove(pawnPosition, newPosition, ChessPiece.PieceType.KNIGHT));
    validMovePositions.add(new ChessMove(pawnPosition, newPosition, ChessPiece.PieceType.ROOK));
    validMovePositions.add(new ChessMove(pawnPosition, newPosition, ChessPiece.PieceType.QUEEN));
    validMovePositions.add(new ChessMove(pawnPosition, newPosition, ChessPiece.PieceType.BISHOP));
  }

}






