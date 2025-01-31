package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveCalcRook implements MoveCalcPiece{
    private final ChessPiece[][] board;
    private final ChessPosition rookPosition;
    private ArrayList validMovesCollection;
    private final int rookRowIndex;
    private final int rookColIndex;

    public MoveCalcRook(ChessBoard board, ChessPosition position){
        this.board = board.getBoard();
        this.rookPosition = position;
        this.validMovesCollection = new ArrayList<>();
        this.rookRowIndex =  rookPosition.getRow()-1;
        this.rookColIndex = rookPosition.getColumn()-1;

    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        checkMovesUp();
        checkMovesDown();
        checkMovesRight();
        checkMovesLeft();
        return validMovesCollection;
    }

    private void checkMovesUp (){
        for (int i = rookRowIndex + 1; i <= 7; i ++){
            boolean result = MoveCalcPiece.loopBody(i, rookColIndex,rookPosition, this.board, validMovesCollection);
            if (result) {break;}
        }
    }

    private void checkMovesDown (){
        for (int i = rookRowIndex - 1; i >= 0; i --){
            boolean result = MoveCalcPiece.loopBody(i, rookColIndex,rookPosition, this.board, validMovesCollection);
            if (result) {break;}
        }
    }

    private void checkMovesRight (){
        for (int i = (rookColIndex + 1); i <= 7; i++){
            boolean result = MoveCalcPiece.loopBody(rookRowIndex, i, rookPosition, this.board, validMovesCollection);
            if (result) {break;}
        }
    }

    private void checkMovesLeft (){
        for (int i = (rookColIndex - 1); i >= 0; i--){
            boolean result = MoveCalcPiece.loopBody(rookRowIndex, i, rookPosition, this.board, validMovesCollection);
            if (result) {break;}
        }
    }
}
