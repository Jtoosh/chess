package chess;

import java.util.HashMap;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private HashMap<ChessPosition, ChessPiece> piecesOnBoard;
    public ChessBoard() {
        this.piecesOnBoard = new HashMap<>();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        this.piecesOnBoard.put(position, piece);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return this.piecesOnBoard.get(position);
    }

    private void resetHelper(ChessGame.TeamColor teamColor) {
        
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        this.piecesOnBoard.clear();
        // create hashmap that contains the corresponding pieces at their starting positions on each side of the board
        resetHelper(ChessGame.TeamColor.BLACK);
        resetHelper(ChessGame.TeamColor.WHITE);
        // set `piecesOnBoard` field to that hashmap
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "piecesOnBoard=" + piecesOnBoard +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that=(ChessBoard) o;
        return Objects.equals(piecesOnBoard, that.piecesOnBoard);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(piecesOnBoard);
    }
}
