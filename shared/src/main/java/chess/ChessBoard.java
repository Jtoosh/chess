package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] board;
    public ChessBoard() {
        board = new ChessPiece[8][8];
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

    private void resetHelper(ChessGame.TeamColor teamColor) {
        if (teamColor == ChessGame.TeamColor.WHITE) {
            for (int i = 0; i < 8; i++){
                this.board[1][i] = new ChessPiece(teamColor, ChessPiece.PieceType.PAWN);
            }
            this.board[0][0] = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
            this.board[0][7] = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
            this.board[0][1] = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
            this.board[0][6] = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
            this.board[0][2] = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
            this.board[0][5] = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
            this.board[0][3] = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN);
            this.board[0][4] = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
        } else{
            for (int i = 0; i < 8; i++){
                this.board[6][i] = new ChessPiece(teamColor, ChessPiece.PieceType.PAWN);
            }
            this.board[7][0] = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
            this.board[7][7] = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
            this.board[7][1] = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
            this.board[7][6] = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
            this.board[7][2] = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
            this.board[7][5] = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
            this.board[7][3] = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN);
            this.board[7][4] = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
        }

    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        this.board = new ChessPiece[8][8];
        resetHelper(ChessGame.TeamColor.WHITE);
        resetHelper(ChessGame.TeamColor.BLACK);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that=(ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
