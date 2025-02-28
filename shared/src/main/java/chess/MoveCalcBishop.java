package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveCalcBishop implements MoveCalcPiece{
    private final ChessPiece[][] board;
    private final ChessPosition bishopPosition;
    private final ArrayList validMovesCollection;
    private final int bishopRowIndex;
    private final int bishopColIndex;

    public MoveCalcBishop(ChessBoard board, ChessPosition position){
        this.board = board.getBoard();
        this.bishopPosition = position;
        this.validMovesCollection = new ArrayList<>();
        this.bishopRowIndex = bishopPosition.getRow() - 1;
        this.bishopColIndex = bishopPosition.getColumn() - 1;
    }
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        checkMovesUpRight();
        checkMovesDownRight();
        checkMovesDownLeft();
        checkMovesUpLeft();
        return validMovesCollection;

    }

    private void checkMovesUpRight(){
        int rowOffset = 1;
        int colOffset = 1;
        while(rowOffset + bishopRowIndex <= 7 && colOffset + bishopColIndex <= 7){
            int rowIndex = bishopRowIndex + rowOffset;
            int colIndex = bishopColIndex + colOffset;
            boolean result = MoveCalcPiece.loopBody(rowIndex, colIndex,bishopPosition, board, validMovesCollection);
            if (result){break;}
            rowOffset++;
            colOffset++;
        }
    }

    private void checkMovesDownRight(){
        int rowOffset = -1;
        int colOffset = 1;
        while(rowOffset + bishopRowIndex >= 0 && colOffset + bishopColIndex <= 7){
         int rowIndex = bishopRowIndex + rowOffset;
         int colIndex = bishopColIndex + colOffset;
         boolean result = MoveCalcPiece.loopBody(rowIndex, colIndex, bishopPosition, board,validMovesCollection );
         if (result){break;}
         rowOffset--;
         colOffset++;
        }
    }

    private void checkMovesDownLeft(){
        int rowOffset = -1;
        int colOffset = -1;
        while(rowOffset + bishopRowIndex >= 0 && colOffset + bishopColIndex >= 0){
            int rowIndex = bishopRowIndex + rowOffset;
            int colIndex = bishopColIndex + colOffset;
            boolean result = MoveCalcPiece.loopBody(rowIndex, colIndex, bishopPosition, board,validMovesCollection );
            if (result){break;}
            rowOffset--;
            colOffset--;
        }
    }

    private void checkMovesUpLeft(){
        int rowOffset = 1;
        int colOffset = -1;
        while(rowOffset + bishopRowIndex <= 7 && colOffset + bishopColIndex >= 0){
            int rowIndex = bishopRowIndex + rowOffset;
            int colIndex = bishopColIndex + colOffset;
            boolean result = MoveCalcPiece.loopBody(rowIndex, colIndex,bishopPosition, board, validMovesCollection);
            if (result){break;}
            rowOffset++;
            colOffset--;
        }
    }

}
