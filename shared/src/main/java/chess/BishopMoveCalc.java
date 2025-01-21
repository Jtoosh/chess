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
    return List.of();
  }
}
