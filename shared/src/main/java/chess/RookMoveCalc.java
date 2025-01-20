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
    this.rookPiece = currentBoardPieces[piecePosition.getRow()-1][piecePosition.getColumn()-1];
  }
  @Override
  public Collection<ChessMove> pieceMoves (ChessBoard board, ChessPosition position){
    for (int i = 0; i < 8; i++){
      ChessPosition currentPosition = new ChessPosition(rookPosition.getRow(), i+1);
      boolean piecePresent = currentBoardPieces[currentPosition.getRow()-1][i] != null;

      if (currentPosition.equals(rookPosition)){continue;}
      else if (piecePresent) {
        ChessPiece currentPiece = currentBoardPieces[currentPosition.getRow()-1][i];
        if (currentPiece != null && currentPiece.getTeamColor() != rookPiece.getTeamColor()){
          validMovePositions.add(new ChessMove(rookPosition, currentPosition, null));
        }
        break;
      }
      validMovePositions.add(new ChessMove(rookPosition, currentPosition, null));
    }
    for (int i = 0; i < 8; i++){
      ChessPosition currentPosition = new ChessPosition(i+1, rookPosition.getColumn());
      boolean piecePresent = currentBoardPieces[i][currentPosition.getColumn()-1] != null;

      if (currentPosition.equals(rookPosition)){continue;}
      else if (piecePresent) {
        ChessPiece currentPiece = currentBoardPieces[i][currentPosition.getColumn()-1];
        if (currentPiece != null && currentPiece.getTeamColor() != rookPiece.getTeamColor()){
          validMovePositions.add(new ChessMove(rookPosition, currentPosition, null));
        }
        break;
      }
      validMovePositions.add(new ChessMove(rookPosition, currentPosition, null));
    }
    System.out.println(validMovePositions.toString());
    return validMovePositions;
  }



}

