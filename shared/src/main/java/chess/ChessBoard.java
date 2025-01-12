package chess;

import java.util.HashMap;

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
        if (teamColor == ChessGame.TeamColor.WHITE) {
            this.piecesOnBoard.put( new ChessPosition(1,1), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
            this.piecesOnBoard.put(new ChessPosition(1,2), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
            this.piecesOnBoard.put(new ChessPosition(1,3), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
            this.piecesOnBoard.put(new ChessPosition(1,4), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
            this.piecesOnBoard.put(new ChessPosition(1,5), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
            this.piecesOnBoard.put(new ChessPosition(1,6), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
            this.piecesOnBoard.put(new ChessPosition(1,7), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
            this.piecesOnBoard.put( new ChessPosition(1,8), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
            for (int i = 1; i < 9; i++){
                this.piecesOnBoard.put(new ChessPosition(2, i), new ChessPiece(teamColor, ChessPiece.PieceType.PAWN));
            }
        } else{
            this.piecesOnBoard.put( new ChessPosition(8,1), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
            this.piecesOnBoard.put(new ChessPosition(8,2), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
            this.piecesOnBoard.put(new ChessPosition(8,3), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
            this.piecesOnBoard.put(new ChessPosition(8,4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
            this.piecesOnBoard.put(new ChessPosition(8,5), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
            this.piecesOnBoard.put(new ChessPosition(8,6), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
            this.piecesOnBoard.put(new ChessPosition(8,7), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
            this.piecesOnBoard.put( new ChessPosition(8,8), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
            for (int i = 1; i < 9; i++){
                this.piecesOnBoard.put(new ChessPosition(7, i), new ChessPiece(teamColor, ChessPiece.PieceType.PAWN));
            }
        }

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
}
