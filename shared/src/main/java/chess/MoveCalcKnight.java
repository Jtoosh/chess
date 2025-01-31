package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveCalcKnight implements MoveCalcPiece{
    private final ChessPiece[][] board;
    private final ChessPosition knightPosition;
    private ArrayList<ChessMove> validMovePositions;
    private final int knightRow;
    private final int knightCol;

    public MoveCalcKnight(ChessBoard board, ChessPosition position){
        this.board = board.getBoard();
        this.knightPosition = position;
        this.validMovePositions = new ArrayList<>();
        this.knightRow = knightPosition.getRow() - 1;
        this.knightCol = knightPosition.getColumn() - 1;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //Up moves
        checkMove(knightRow + 2, knightCol + 1);
        checkMove(knightRow + 2, knightCol - 1);
        //Right moves
        checkMove(knightRow + 1, knightCol + 2);
        checkMove(knightRow - 1, knightCol + 2);
        //Down moves
        checkMove(knightRow - 2, knightCol + 1);
        checkMove(knightRow - 2, knightCol - 1);
        //Left moves
        checkMove(knightRow - 1, knightCol - 2);
        checkMove(knightRow + 1, knightCol - 2);
        return validMovePositions;
    }

    private void checkMove(int newRowIndex, int newColIndex){
        if (newRowIndex <= 7 && newColIndex <= 7 && newRowIndex >= 0 && newColIndex >= 0){
            MoveCalcPiece.loopBody(newRowIndex, newColIndex, knightPosition, this.board, validMovePositions);
        }
    }
}
