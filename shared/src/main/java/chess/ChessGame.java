package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor turnTeam;
    private ChessBoard gameBoard;
    private ChessPiece[][] gameBoardPieceStorage;
    private ChessPosition whiteKingPosition = new ChessPosition(1, 5);
    private ChessPosition blackKingPosition = new ChessPosition(8, 5);
    public ChessGame() {
        this.turnTeam = TeamColor.WHITE;
        setBoard(new ChessBoard());
        this.gameBoard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.turnTeam;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.turnTeam = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    private void editPieceStorage(ChessMove move, ChessPiece[][] boardStorage){
        int startPosRowIndex = move.getStartPosition().getRow()-1;
        int startPosColIndex = move.getStartPosition().getColumn()-1;
        ChessPiece tmp = boardStorage[startPosRowIndex][startPosColIndex];
        boardStorage[startPosRowIndex][startPosColIndex] = null;

        int endPosRowIndex = move.getEndPosition().getRow()-1;
        int endPosColIndex = move.getEndPosition().getColumn()-1;
        boardStorage[endPosRowIndex][endPosColIndex] = tmp;
        if (tmp.getPieceType() == ChessPiece.PieceType.KING && tmp.getTeamColor()==TeamColor.WHITE){
            this.whiteKingPosition = move.getEndPosition();
        } else{
            this.blackKingPosition = move.getEndPosition();
        }
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece pieceToValidate = this.gameBoardPieceStorage[startPosition.getRow()-1][startPosition.getColumn()-1];
        Collection<ChessMove> movesToValidate = pieceToValidate.pieceMoves(this.gameBoard, startPosition);
        for (ChessMove move : movesToValidate){
            ChessPiece[][] gameBoardPieceStorageCopy = new ChessBoard(this.gameBoard).getBoard();
            editPieceStorage(move, gameBoardPieceStorageCopy);
            if (inCheckLoopBody(pieceToValidate.getTeamColor(), gameBoardPieceStorageCopy)){
                movesToValidate.remove(move);
            }
            System.out.println("Party time (once I finish coding this)");
        }
        return movesToValidate;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (validMoves(move.getStartPosition()).contains(move)){
           editPieceStorage(move, this.gameBoardPieceStorage);
        } else { throw new InvalidMoveException("That is an invalid move.");}
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        if (teamColor == TeamColor.BLACK){
            return inCheckLoopBody(TeamColor.BLACK, this.gameBoardPieceStorage);
        } else {
            return inCheckLoopBody(TeamColor.WHITE, this.gameBoardPieceStorage);
        }

    }

    private boolean inCheckLoopBody (TeamColor teamColor, ChessPiece[][] boardStorage){
        ChessPosition teamKingPosition = (teamColor == TeamColor.WHITE) ? this.whiteKingPosition : this.blackKingPosition;
        for (int row = 0; row <= 7; row++){
            for (int col = 0; col <= 7; col++){
                if (boardStorage[row][col] == null){ continue;}
                if (boardStorage[row][col].getTeamColor() != teamColor){
                    Collection<ChessMove> thisPieceMoves = boardStorage[row][col].pieceMoves(this.gameBoard, new ChessPosition(row+1, col +1));
                    for (ChessMove move : thisPieceMoves){

                        if (move.getEndPosition() == teamKingPosition){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.gameBoard = board;
        this.gameBoardPieceStorage = this.gameBoard.getBoard();
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.gameBoard;
    }
}
