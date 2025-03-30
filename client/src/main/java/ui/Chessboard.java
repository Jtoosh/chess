package ui;

import chess.ChessGame;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Chessboard {

    //Board dimensions
    private static final int BOARD_SIZE_IN_SQUARES = 10;
    private static final int BOARD_ROWS = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 1;

    //Team colors
    private static final ChessGame.TeamColor WHITE = ChessGame.TeamColor.WHITE;
    private static final ChessGame.TeamColor BLACK = ChessGame.TeamColor.BLACK;

    //Padded characters (Note: Chess piece padded characters are in EscapeSequences.java)
    private static final String EMPTY = "   ";
    private static ArrayList<String> fileLables = new ArrayList<>(List.of(EMPTY, " a ", " b "," c ", " d "," e ", " f "," g ", " h ", EMPTY));
    private static ArrayList<String> blackRowOne = new ArrayList<>(List.of(
            EscapeSequences.BLACK_ROOK,
            EscapeSequences.BLACK_KNIGHT,
            EscapeSequences.BLACK_BISHOP,
            EscapeSequences.BLACK_QUEEN,
            EscapeSequences.BLACK_KING,
            EscapeSequences.BLACK_BISHOP,
            EscapeSequences.BLACK_KNIGHT,
            EscapeSequences.BLACK_ROOK));
    private static ArrayList<String> whiteRowOne = new ArrayList<>(List.of(
            EscapeSequences.WHITE_ROOK,
            EscapeSequences.WHITE_KNIGHT,
            EscapeSequences.WHITE_BISHOP,
            EscapeSequences.WHITE_QUEEN,
            EscapeSequences.WHITE_KING,
            EscapeSequences.WHITE_BISHOP,
            EscapeSequences.WHITE_KNIGHT,
            EscapeSequences.WHITE_ROOK));

    private static String startColor;

    public static void main(String startColorArg, ChessPiece[][] board){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        assert board != null;
        assert startColorArg.equals("light") || startColorArg.equals("dark");

        boolean lightFlag = startColorArg.equals("light");
        if (!lightFlag){
            fileLables = new ArrayList<>(fileLables.reversed()) ;}
        int rankNumber = 0;

        drawHeaderRow(out);
        for (int i = 0; i < BOARD_ROWS; i++){
            rankNumber = (startColorArg.equals("light")) ? i + 1 : 8-i;
            if (lightFlag){
                drawBoardRow(out, "light", String.format(" %s ", rankNumber), board[i]);
            } else {
                drawBoardRow(out, "dark", String.format(" %s ", rankNumber), board[i]);
            }
            lightFlag = !lightFlag;
        }
        drawHeaderRow(out);

    }

    private static void drawHeaderRow(PrintStream out){
        headerFormat(out);
        for (String label : fileLables){
            out.print(label);
        }
        reset(out);
        out.print("\n");
    }

    private static void drawBoardRow(PrintStream out, String rowStartColor, String rank, ChessPiece[] row){
        boolean lightFlag = (rowStartColor.equals("light")) ? true : false;
        headerFormat(out);
        out.print(rank);

        out.print(EscapeSequences.RESET_TEXT_COLOR);
        for (int i = 0; i < BOARD_SIZE_IN_SQUARES - 2; i++){
            if (lightFlag){
                out.print(EscapeSequences.SET_BG_COLOR_TAN);
            } else{
                out.print(EscapeSequences.SET_BG_COLOR_DARK_BROWN);
            }
            out.print(evaluateSquare(row[i]));
            lightFlag = !lightFlag;
        }

        headerFormat(out);
        out.print(rank);
        reset(out);
        out.print("\n");

    }

    private static String evaluateSquare (ChessPiece piece){
        if (piece == null){
            return EMPTY;
        }
        switch (piece.getPieceType()){
            case ROOK:
                return (piece.getTeamColor() == WHITE) ? EscapeSequences.WHITE_ROOK : EscapeSequences.BLACK_ROOK;
            case KNIGHT:
                return (piece.getTeamColor() == WHITE) ? EscapeSequences.WHITE_KNIGHT : EscapeSequences.BLACK_KNIGHT;
            case BISHOP:
                return (piece.getTeamColor() == WHITE) ? EscapeSequences.WHITE_BISHOP : EscapeSequences.BLACK_BISHOP;
            case QUEEN:
                return (piece.getTeamColor() == WHITE) ? EscapeSequences.WHITE_QUEEN : EscapeSequences.BLACK_QUEEN;
            case KING:
                return (piece.getTeamColor() == WHITE) ? EscapeSequences.WHITE_KING : EscapeSequences.BLACK_KING;
            case PAWN:
                return (piece.getTeamColor() == WHITE) ? EscapeSequences.WHITE_PAWN : EscapeSequences.BLACK_PAWN;
            default:
                return EMPTY;
        }
    }

    private static void headerFormat(PrintStream out){
        out.print(EscapeSequences.SET_BG_COLOR_DARK_GREEN);
        out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);
    }

    private static void reset(PrintStream out){
        out.print(EscapeSequences.RESET_BG_COLOR);
        out.print(EscapeSequences.RESET_TEXT_COLOR);
    }
}
