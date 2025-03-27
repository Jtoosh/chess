package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveCalcQueen implements MoveCalcPiece{
    private final ChessPiece[][] board;
    private final ChessPosition queenPosition;
    private final ArrayList<ChessMove> validMovesCollection;
    private final int queenRowIndex;
    private final int queenColIndex;


    public MoveCalcQueen(ChessBoard board, ChessPosition position) {
        this.board = board.getBoard();
        this.queenPosition = position;
        this.validMovesCollection = new ArrayList<ChessMove>();
        this.queenRowIndex =  queenPosition.getRow()-1;
        this.queenColIndex = queenPosition.getColumn()-1;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        MoveCalcPiece.checkMovesUp(queenRowIndex, queenPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesDown(queenRowIndex, queenPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesLeft(queenColIndex, queenPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesRight(queenColIndex, queenPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesUpRight(queenRowIndex, queenColIndex, queenPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesDownLeft(queenRowIndex, queenColIndex, queenPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesDownRight(queenRowIndex, queenColIndex, queenPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesUpLeft(queenRowIndex, queenColIndex, queenPosition, this.board, validMovesCollection);
        return validMovesCollection;
    }
}
