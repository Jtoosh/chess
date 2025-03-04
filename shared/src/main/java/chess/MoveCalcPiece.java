package chess;

import java.util.Collection;

public interface MoveCalcPiece {
    Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);

    static void makeNewPositionAndAddChessMove(ChessPosition startPosition, int rowIndex, int colIndex, Collection<ChessMove> movesCollection, ChessPiece.PieceType promotionPiece){
        ChessPosition newPosition = new ChessPosition(rowIndex + 1, colIndex + 1);
        movesCollection.add(new ChessMove(startPosition, newPosition, promotionPiece));
    }

    static boolean loopBody(int iterationRowIndex, int iterationColIndex, ChessPosition piecePosition, ChessPiece[][] board, Collection<ChessMove> movesCollection){
        ChessPiece originalPiece = board[piecePosition.getRow()-1][piecePosition.getColumn()-1];
        if (board[iterationRowIndex][iterationColIndex] == null){
            MoveCalcPiece.makeNewPositionAndAddChessMove(piecePosition, iterationRowIndex, iterationColIndex, movesCollection, null);
            return false;
        } else if (!board[iterationRowIndex][iterationColIndex].teamColorEquals(originalPiece)){
            MoveCalcPiece.makeNewPositionAndAddChessMove(piecePosition, iterationRowIndex, iterationColIndex, movesCollection, null);
            return true;
        } else {return true;}
    }

    static void checkMovesUp (int rowIndex, ChessPosition piecePosition, ChessPiece[][] board, Collection<ChessMove> validMovesCollection){
        for (int i = rowIndex + 1; i <= 7; i ++){
            boolean result = MoveCalcPiece.loopBody(i, piecePosition.getColumn() -1, piecePosition, board, validMovesCollection);
            if (result) {break;}
        }
    }

    static void checkMovesDown (int rowIndex, ChessPosition piecePosition, ChessPiece[][] board, Collection<ChessMove> validMovesCollection){
        for (int i = rowIndex - 1; i >= 0; i --){
            boolean result = MoveCalcPiece.loopBody(i, piecePosition.getColumn() -1, piecePosition, board, validMovesCollection);
            if (result) {break;}
        }
    }

    static void checkMovesRight (int colIndex, ChessPosition piecePosition, ChessPiece[][] board, Collection<ChessMove> validMovesCollection){
        for (int i = (colIndex + 1); i <= 7; i++){
            boolean result = MoveCalcPiece.loopBody(piecePosition.getRow()-1, i, piecePosition, board, validMovesCollection);
            if (result) {break;}
        }
    }

    static void checkMovesLeft (int colIndex, ChessPosition piecePosition, ChessPiece[][] board, Collection<ChessMove> validMovesCollection){
        for (int i = (colIndex - 1); i >= 0; i--){
            boolean result = MoveCalcPiece.loopBody(piecePosition.getRow()-1, i, piecePosition, board, validMovesCollection);
            if (result) {break;}
        }
    }
}
