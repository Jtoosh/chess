package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveCalcBishop implements MoveCalcPiece{
    private final ChessPiece[][] board;
    private final ChessPosition bishopPosition;
    private final ArrayList<ChessMove> validMovesCollection;
    private final int bishopRowIndex;
    private final int bishopColIndex;

    public MoveCalcBishop(ChessBoard board, ChessPosition position){
        this.board = board.getBoardMatrix();
        this.bishopPosition = position;
        this.validMovesCollection = new ArrayList<>();
        this.bishopRowIndex = bishopPosition.getRow() - 1;
        this.bishopColIndex = bishopPosition.getColumn() - 1;
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        MoveCalcPiece.checkMovesUpRight(bishopRowIndex, bishopColIndex, bishopPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesDownRight(bishopRowIndex, bishopColIndex, bishopPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesDownLeft(bishopRowIndex, bishopColIndex, bishopPosition, this.board, validMovesCollection);
        MoveCalcPiece.checkMovesUpLeft(bishopRowIndex, bishopColIndex, bishopPosition, this.board, validMovesCollection);
        return validMovesCollection;

    }
}
