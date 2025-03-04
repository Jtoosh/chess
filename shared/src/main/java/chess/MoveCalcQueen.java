package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveCalcQueen implements MoveCalcPiece{
    private final ChessPiece[][] board;
    private final ChessPosition queenPosition;
    private final ArrayList validMovesCollection;
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
        checkMovesUpRight();
        checkMovesDownLeft();
        checkMovesDownRight();
        checkMovesUpLeft();
        return validMovesCollection;
    }
    
    private void checkMovesUpRight(){
        int rowOffset = 1;
        int colOffset = 1;
        while(rowOffset + queenRowIndex <= 7 && colOffset + queenColIndex <= 7){
            int rowIndex = queenRowIndex + rowOffset;
            int colIndex = queenColIndex + colOffset;
            boolean result = MoveCalcPiece.loopBody(rowIndex, colIndex,queenPosition, board, validMovesCollection);
            if (result){break;}
            rowOffset++;
            colOffset++;
        }
    }

    private void checkMovesDownRight(){
        int rowOffset = -1;
        int colOffset = 1;
        while(rowOffset + queenRowIndex >= 0 && colOffset + queenColIndex <= 7){
            int rowIndex = queenRowIndex + rowOffset;
            int colIndex = queenColIndex + colOffset;
            boolean result = MoveCalcPiece.loopBody(rowIndex, colIndex, queenPosition, board,validMovesCollection );
            if (result){break;}
            rowOffset--;
            colOffset++;
        }
    }

    private void checkMovesDownLeft(){
        int rowOffset = -1;
        int colOffset = -1;
        while(rowOffset + queenRowIndex >= 0 && colOffset + queenColIndex >= 0){
            int rowIndex = queenRowIndex + rowOffset;
            int colIndex = queenColIndex + colOffset;
            boolean result = MoveCalcPiece.loopBody(rowIndex, colIndex, queenPosition, board,validMovesCollection );
            if (result){break;}
            rowOffset--;
            colOffset--;
        }
    }

    private void checkMovesUpLeft(){
        int rowOffset = 1;
        int colOffset = -1;
        while(rowOffset + queenRowIndex <= 7 && colOffset + queenColIndex >= 0){
            int rowIndex = queenRowIndex + rowOffset;
            int colIndex = queenColIndex + colOffset;
            boolean result = MoveCalcPiece.loopBody(rowIndex, colIndex,queenPosition, board, validMovesCollection);
            if (result){break;}
            rowOffset++;
            colOffset--;
        }
    }
}
