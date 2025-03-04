package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveCalcKing implements MoveCalcPiece{
    private final ChessPiece[][] board;
    private final ChessPosition kingPosition;
    private final ArrayList<ChessMove> validMovePositions;
    private final int kingRow;
    private final int kingCol;

    public MoveCalcKing (ChessBoard board, ChessPosition position){
        this.board = board.getBoard();
        this.kingPosition = position;
        this.validMovePositions = new ArrayList<>();
        this.kingRow = kingPosition.getRow()-1;
        this.kingCol = kingPosition.getColumn()-1;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //Straight up
        checkMove(kingRow + 1, kingCol);
        //Up and Right
        checkMove(kingRow + 1, kingCol + 1);
        //Right
        checkMove(kingRow, kingCol + 1);
        //Down and Right
        checkMove(kingRow - 1, kingCol + 1);
        //Straight Down
        checkMove(kingRow - 1, kingCol);
        //Down and Left
        checkMove(kingRow - 1, kingCol - 1);
        //Left
        checkMove(kingRow, kingCol - 1);
        //Up and Left
        checkMove(kingRow + 1, kingCol - 1);
        return validMovePositions;
    }

    private void checkMove(int newRowIndex, int newColIndex){
        if (newRowIndex <= 7 && newColIndex <= 7 && newRowIndex >= 0 && newColIndex >= 0){
            MoveCalcPiece.loopBody(newRowIndex, newColIndex, kingPosition, this.board, validMovePositions);
        }
    }
}
