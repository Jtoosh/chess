package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Chessboard {

    //Board dimensions
    private static final int BOARD_SIZE_IN_SQUARES = 10;
    private static final int BOARD_ROWS = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 1;

    //Padded characters (Note: Chess piece padded characters are in EscapeSequences.java)
    private static final String EMPTY = "   ";
    private static ArrayList<String> FILE_LABELS = new ArrayList<>(List.of(EMPTY, " a ", " b "," c ", " d "," e ", " f "," g ", " h ", EMPTY));
    private static ArrayList<String> blackRowOne = new ArrayList<>(List.of(
                                                         EscapeSequences.BLACK_ROOK,
                                                         EscapeSequences.BLACK_KNIGHT,
                                                         EscapeSequences.BLACK_BISHOP,
                                                         EscapeSequences.BLACK_KING,
                                                         EscapeSequences.BLACK_QUEEN,
                                                         EscapeSequences.BLACK_BISHOP,
                                                         EscapeSequences.BLACK_KNIGHT,
                                                         EscapeSequences.BLACK_ROOK));
    private static ArrayList<String> whiteRowOne = new ArrayList<>(List.of(
            EscapeSequences.WHITE_ROOK,
            EscapeSequences.WHITE_KNIGHT,
            EscapeSequences.WHITE_BISHOP,
            EscapeSequences.WHITE_KING,
            EscapeSequences.WHITE_QUEEN,
            EscapeSequences.WHITE_BISHOP,
            EscapeSequences.WHITE_KNIGHT,
            EscapeSequences.WHITE_ROOK));

    private static String startColor;

    public static void main(String startColorArg){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        startColor = startColorArg;
        boolean lightFlag = (startColorArg.equals("light")) ? true : false;
        if (!lightFlag){FILE_LABELS = new ArrayList<>(FILE_LABELS.reversed()) ;}
        int rankNumber = 0;
        drawHeaderRow(out);

        for (int i = 0; i < BOARD_ROWS; i++){
            rankNumber = (startColor.equals("light")) ? 8-i : i + 1;
            if (lightFlag){
                drawBoardRow(out, "light", String.format(" %s ", rankNumber));
            } else {
                drawBoardRow(out, "dark", String.format(" %s ", rankNumber));
            }
            lightFlag = !lightFlag;
        }

        drawHeaderRow(out);
    }

    private static void drawHeaderRow(PrintStream out){
        headerFormat(out);
        for (String label : FILE_LABELS){
            out.print(label);
        }
        reset(out);
        out.print("\n");
    }

    private static void drawBoardRow(PrintStream out, String rowStartColor, String rank){
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
            handlePawnRow(out, rank);


            lightFlag = !lightFlag;
        }

        headerFormat(out);
        out.print(rank);
        reset(out);
        out.print("\n");

    }

    private static void handlePawnRow(PrintStream out, String rank){
        if (rank.equals(" 2 ")){
            String pawn = EscapeSequences.SET_TEXT_COLOR_WHITE + EscapeSequences.WHITE_PAWN;
            out.print(pawn);
        } else if (rank.equals(" 7 ")){
            String pawn = EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.BLACK_PAWN;
            out.print(pawn);
        }else{
            out.print(EMPTY);
        }
    }

    private static void handleFirstRows(PrintStream out, String rowStartColor, String rank, int index){
        if(rank.equals("1")){

        }
    }

    private static void organizeFirstRows

    private static void headerFormat(PrintStream out){
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);
    }

    private static void reset(PrintStream out){
        out.print(EscapeSequences.RESET_BG_COLOR);
        out.print(EscapeSequences.RESET_TEXT_COLOR);
    }
}
