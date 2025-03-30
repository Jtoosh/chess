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

    //Team colors
    private static final ChessGame.TeamColor WHITE = ChessGame.TeamColor.WHITE;

    private static final String LIGHT = "light";
    private static final String DARK = "dark";

    //Padded characters (Note: Chess piece padded characters are in EscapeSequences.java)
    private static final String EMPTY = "   ";
    private static ArrayList<String> fileLables = new ArrayList<>(List.of(EMPTY, " a ", " b "," c ", " d "," e ", " f "," g ", " h ", EMPTY));


    public static void draw(String startColorArg, ChessPiece[][] board){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        assert board != null;
        assert startColorArg.equals(LIGHT) || startColorArg.equals(DARK);

        boolean lightFlag = startColorArg.equals(LIGHT);
        if (!lightFlag){
            fileLables = new ArrayList<>(fileLables.reversed()) ;}
        int rankNumber;

        drawHeaderRow(out);
        for (int i = 0; i < BOARD_ROWS; i++){
            rankNumber = (startColorArg.equals(LIGHT)) ? 7-i : i;
            if (lightFlag){
                drawBoardRow(out, LIGHT, String.format(" %s ", rankNumber + 1), board[rankNumber]);
            } else {
                drawBoardRow(out, DARK, String.format(" %s ", rankNumber + 1), board[rankNumber]);
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
        boolean lightFlag = rowStartColor.equals(LIGHT);
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
      return switch (piece.getPieceType()) {
        case ROOK -> (piece.getTeamColor() == WHITE) ? EscapeSequences.WHITE_ROOK : EscapeSequences.BLACK_ROOK;
        case KNIGHT -> (piece.getTeamColor() == WHITE) ? EscapeSequences.WHITE_KNIGHT : EscapeSequences.BLACK_KNIGHT;
        case BISHOP -> (piece.getTeamColor() == WHITE) ? EscapeSequences.WHITE_BISHOP : EscapeSequences.BLACK_BISHOP;
        case QUEEN -> (piece.getTeamColor() == WHITE) ? EscapeSequences.WHITE_QUEEN : EscapeSequences.BLACK_QUEEN;
        case KING -> (piece.getTeamColor() == WHITE) ? EscapeSequences.WHITE_KING : EscapeSequences.BLACK_KING;
        case PAWN -> (piece.getTeamColor() == WHITE) ? EscapeSequences.WHITE_PAWN : EscapeSequences.BLACK_PAWN;
      };
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
