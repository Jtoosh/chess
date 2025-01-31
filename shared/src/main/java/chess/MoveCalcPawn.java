package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveCalcPawn implements MoveCalcPiece{
    private final ChessPiece[][] board;
    private final ChessPosition pawnPosition;
    private ArrayList<ChessMove> validMovePositions;
    private final int pawnRow;
    private final int pawnCol;
    private final ChessGame.TeamColor pawnColor;
    private boolean initialMove;
    private final int direction;

    public MoveCalcPawn(ChessBoard board, ChessPosition position){
        this.board = board.getBoard();
        this.pawnPosition = position;
        this.validMovePositions = new ArrayList<>();
        this.pawnRow = pawnPosition.getRow() - 1;
        this.pawnCol = pawnPosition.getColumn() - 1;
        this.pawnColor = this.board[pawnRow][pawnCol].getTeamColor();
        this.initialMove = (pawnColor == ChessGame.TeamColor.WHITE) ? (pawnRow == 1) : (pawnRow == 6);
        this.direction = (this.pawnColor == ChessGame.TeamColor.WHITE) ? 1 : -1;
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (checkforPromotion(pawnRow + direction)){
            addAllPromotionMoves(pawnRow + direction, pawnCol);
        } else if (this.board[pawnRow + direction][pawnCol] == null){
            MoveCalcPiece.makeNewPositionAndAddChessMove(pawnPosition,(pawnRow + direction), pawnCol, validMovePositions, null);
        }
        if (pawnCol > 0) {
            ChessPiece leftCaptureSquare = this.board[pawnRow + direction][pawnCol - 1];
            if (leftCaptureSquare != null && leftCaptureSquare.getTeamColor()!=(pawnColor)) {
                if (checkforPromotion(pawnRow + direction)){
                    addAllPromotionMoves(pawnRow + direction, pawnCol - 1);
                } else {
                    MoveCalcPiece.makeNewPositionAndAddChessMove(pawnPosition,(pawnRow + direction), (pawnCol - 1), validMovePositions, null);
                }
            }
        }
        if (pawnCol < 7){
            ChessPiece rightCaptureSquare = this.board[pawnRow + direction][pawnCol + 1];
            if (rightCaptureSquare != null && rightCaptureSquare.getTeamColor()!=(pawnColor)){
                if (checkforPromotion(pawnRow + direction)){
                    addAllPromotionMoves(pawnRow + direction, pawnCol + 1);
                } else {
                    MoveCalcPiece.makeNewPositionAndAddChessMove(pawnPosition,(pawnRow + direction), (pawnCol + 1), validMovePositions, null);
                }
            }
        }
        if (initialMove && this.board[pawnRow + direction][pawnCol] == null&& this.board[pawnRow + (2 * direction)][pawnCol] == null){
            MoveCalcPiece.makeNewPositionAndAddChessMove(pawnPosition,(pawnRow + (2 * direction)), pawnCol, validMovePositions, null);
        }
        return validMovePositions;
    }

    private boolean checkforPromotion(int newRowIndex){
        return (direction == 1) ? newRowIndex == 7 : newRowIndex == 0;
    }

    private void addAllPromotionMoves (int newRowIndex, int newColIndex){
        MoveCalcPiece.makeNewPositionAndAddChessMove(pawnPosition, newRowIndex, newColIndex, validMovePositions, ChessPiece.PieceType.QUEEN);
        MoveCalcPiece.makeNewPositionAndAddChessMove(pawnPosition, newRowIndex, newColIndex, validMovePositions, ChessPiece.PieceType.BISHOP);
        MoveCalcPiece.makeNewPositionAndAddChessMove(pawnPosition, newRowIndex, newColIndex, validMovePositions, ChessPiece.PieceType.ROOK);
        MoveCalcPiece.makeNewPositionAndAddChessMove(pawnPosition, newRowIndex, newColIndex, validMovePositions, ChessPiece.PieceType.KNIGHT);
    }
}
