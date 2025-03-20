package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chessboard {

    //Board dimensions
    private static final int BOARD_SIZE_IN_SQUARES = 10;
    private static final int BOARD_ROWS = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 1;

    //Padded characters (Note: Chess piece padded characters are in EscapeSequences.java)
    private static final String EMPTY = "   ";
    private static ArrayList<String> FILE_LABELS = new ArrayList<>(List.of(EMPTY, " a ", " b "," c ", " d "," e ", " f "," g ", " h ", EMPTY));

    public static void main(String startColorArg){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        boolean lightFlag = (startColorArg.equals("light")) ? true : false;
        if (!lightFlag){FILE_LABELS = new ArrayList<>(FILE_LABELS.reversed()) ;}
        int rankNumber = 0;
        drawHeaderRow(out);

        for (int i = 0; i < BOARD_ROWS; i++){
            rankNumber = (startColorArg.equals("light")) ? 8-i : i + 1;
            if (lightFlag){
                drawBoardRow(out, "light", String.format(" %s ", rankNumber));
            } else {
                drawBoardRow(out, "dark", String.format(" %s ", rankNumber));
            }
            lightFlag = !lightFlag;
        }

        drawHeaderRow(out);

        /* pseudocode:
        drawHeaderRow(){
            drawBorderSquare * 10
         }
        8 times -> drawBodyRow(){
            drawBorderSquare()
            drawBoardSquare(starting color) * 8
            drawBorderSquare()
         }
        drawHeaderRow()
        *
        * */
    }

    private static void drawHeaderRow(PrintStream out){
        headerFormat(out);
        for (String label : FILE_LABELS){
            out.print(label);
        }
        reset(out);
        out.print("\n");
    }

    private static void drawBoardRow(PrintStream out, String startColor, String rank){
        boolean lightFlag = (startColor.equals("light")) ? true : false;
        headerFormat(out);
        out.print(rank);

        out.print(EscapeSequences.RESET_TEXT_COLOR);
        for (int i = 0; i < BOARD_SIZE_IN_SQUARES - 2; i++){
            if (lightFlag){
                out.print(EscapeSequences.SET_BG_COLOR_TAN);
            } else{
                out.print(EscapeSequences.SET_BG_COLOR_DARK_BROWN);
            }
            if (rank.equals(" 1 ")){
                String Pawn = (startColor.equals("light")) ? EscapeSequences.WHITE_PAWN : EscapeSequences.BLACK_PAWN;
                out.print(Pawn);
            } else if (rank.equals(" 8 ")){
                String Pawn = (startColor.equals("black")) ? EscapeSequences.BLACK_PAWN : EscapeSequences.WHITE_PAWN;
                out.print(Pawn);
            }else{
                out.print(EMPTY);
            }

            lightFlag = !lightFlag;
        }

        headerFormat(out);
        out.print(rank);
        reset(out);
        out.print("\n");

    }

    private static void headerFormat(PrintStream out){
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);
    }

    private static void reset(PrintStream out){
        out.print(EscapeSequences.RESET_BG_COLOR);
        out.print(EscapeSequences.RESET_TEXT_COLOR);
    }
}
