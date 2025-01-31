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
}
