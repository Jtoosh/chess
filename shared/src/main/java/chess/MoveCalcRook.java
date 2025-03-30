package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveCalcRook implements MoveCalcPiece{
    private final ChessPiece[][] board;
    private final ChessPosition rookPosition;
    private final ArrayList<ChessMove> validMovesCollection;
    private final int rookRowIndex;
    private final int rookColIndex;

    public MoveCalcRook(ChessBoard board, ChessPosition position){
        this.board = board.getBoardMatrix();
        this.rookPosition = position;
        this.validMovesCollection = new ArrayList<>();
        this.rookRowIndex =  rookPosition.getRow()-1;
        this.rookColIndex = rookPosition.getColumn()-1;

    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        MoveCalcPiece.checkMovesUp(rookRowIndex, rookPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesDown(rookRowIndex, rookPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesRight(rookColIndex, rookPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesLeft(rookColIndex, rookPosition, this.board, validMovesCollection);
        return validMovesCollection;
    }

}
