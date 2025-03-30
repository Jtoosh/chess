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

    private ChessPiece[][] editPieceStorage(ChessMove move, ChessPiece[][] boardStorage){
        int startPosRowIndex = move.getStartPosition().getRow()-1;
        int startPosColIndex = move.getStartPosition().getColumn()-1;
        ChessPiece tmp = boardStorage[startPosRowIndex][startPosColIndex];
        if (move.getPromotionPiece() != null){
            tmp = new ChessPiece(boardStorage[startPosRowIndex][startPosColIndex].getTeamColor(), move.getPromotionPiece());
        }
        boardStorage[startPosRowIndex][startPosColIndex] = null;

        int endPosRowIndex = move.getEndPosition().getRow()-1;
        int endPosColIndex = move.getEndPosition().getColumn()-1;
        boardStorage[endPosRowIndex][endPosColIndex] = tmp;
        return boardStorage;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece pieceToValidate = this.gameBoard.getBoardMatrix()[startPosition.getRow()-1][startPosition.getColumn()-1];
        Collection<ChessMove> movesToValidate = pieceToValidate.pieceMoves(this.gameBoard, startPosition);
        Collection <ChessMove> movesToRemove = new ArrayList<>();
        for (ChessMove move : movesToValidate){
            ChessBoard gameBoardCopy = new ChessBoard(this.gameBoard);
            gameBoardCopy.setBoard(editPieceStorage(move, gameBoardCopy.getBoardMatrix()));
            if (inCheckLoopBody(pieceToValidate.getTeamColor(), gameBoardCopy)){
                movesToRemove.add(move);
            }
        }
        movesToValidate.removeAll(movesToRemove);
        return movesToValidate;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece pieceToMove = this.gameBoard.getBoardMatrix()[move.getStartPosition().getRow()-1][move.getStartPosition().getColumn()-1];
        if (pieceToMove == null){throw new InvalidMoveException("There is no piece to move at " + move.getStartPosition().toString());}

        if (pieceToMove.getTeamColor() != this.turnTeam){
            throw new InvalidMoveException("It is not this piece's turn. It is " + this.turnTeam.toString() + "'s turn");
        }

        if (validMoves(move.getStartPosition()).contains(move)){
           this.gameBoard.setBoard(editPieceStorage(move, this.gameBoard.getBoardMatrix()));
        } else { throw new InvalidMoveException("That is an invalid move.");}
        this.turnTeam = (this.turnTeam == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        if (teamColor == TeamColor.BLACK){
            return inCheckLoopBody(TeamColor.BLACK, this.gameBoard);
        } else {
            return inCheckLoopBody(TeamColor.WHITE, this.gameBoard);
        }

    }

    private boolean inCheckLoopBody (TeamColor teamColor, ChessBoard board){
        ChessPiece[][] boardStorage = board.getBoardMatrix();
        for (int row = 0; row <= 7; row++){
            for (int col = 0; col <= 7; col++){
                if (boardStorage[row][col] == null){ continue;}
                if (boardStorage[row][col].getTeamColor() != teamColor){
                    Collection<ChessMove> thisPieceMoves = boardStorage[row][col].pieceMoves(board, new ChessPosition(row+1, col +1));
                    if (checkForCheck(thisPieceMoves, boardStorage)){return true;}
                }
            }
        }
        return false;
    }

    public boolean stalemateLoop(TeamColor teamColor) {
        ChessPiece[][] boardStorage =this.gameBoard.getBoardMatrix();
        for (int row = 0; row <= 7; row++){
            for (int col = 0; col <= 7; col++){
                if (boardStorage[row][col] == null){continue;}
                if (boardStorage[row][col].getTeamColor() != teamColor) {
                }
                else{
                    Collection<ChessMove> thisPieceValidMoves = this.validMoves(new ChessPosition(row+1, col+1));
                    if (!thisPieceValidMoves.isEmpty()) {return false;}
                }
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)){ return false;}
      return stalemateLoop(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {return false;}
        else {return stalemateLoop(teamColor);}
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.gameBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.gameBoard;
    }

    private boolean checkForCheck(Collection<ChessMove> moves, ChessPiece[][]boardStorage){
        for (ChessMove move : moves){
            ChessPiece moveEndSquare = boardStorage[move.getEndPosition().getRow()-1][move.getEndPosition().getColumn()-1];
            if (moveEndSquare != null && moveEndSquare.getPieceType() == ChessPiece.PieceType.KING){
                return true;
            }
        }
        return false;
    }
}
