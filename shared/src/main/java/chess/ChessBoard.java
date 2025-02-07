package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard{
    private ChessPiece[][] board;
    public ChessBoard() {
        this.board = new ChessPiece[8][8];
    }


    public ChessBoard(ChessBoard boardToCopy){
        ChessPiece [][] cloneBoard = new ChessPiece[8][8];
        for (int row = 0; row <= 7; row++){
            System.arraycopy(boardToCopy.board[row], 0, cloneBoard[row], 0, 8);
        }
        this.board = cloneBoard;
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        this.board[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return this.board[position.getRow()-1][position.getColumn()-1];
    }

    public ChessPiece[][] getBoard() {
        return board;
    }

    public void setBoard (ChessPiece[][] newBoard){
        this.board = newBoard;
    }

    private void resetHelper (ChessGame.TeamColor teamColor){
        int firstRow = (teamColor == ChessGame.TeamColor.WHITE) ? 0 : 7;
        int secondRow = (teamColor == ChessGame.TeamColor.WHITE) ? 1 : 6;
        this.board[firstRow][0] = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
        this.board[firstRow][1] = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
        this.board[firstRow][2] = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
        this.board[firstRow][3] = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN);
        this.board[firstRow][4] = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
        this.board[firstRow][5] = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
        this.board[firstRow][6] = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
        this.board[firstRow][7] = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);

        for (int i = 0; i <= 7; i++){
            this.board[secondRow][i] = new ChessPiece(teamColor, ChessPiece.PieceType.PAWN);
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        resetHelper(ChessGame.TeamColor.WHITE);
        resetHelper(ChessGame.TeamColor.BLACK);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }


}
