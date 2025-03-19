package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Chessboard {

    //Board dimensions
    private static final int BOARD_SIZE_IN_SQUARES = 10;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 1;

    //Padded characters (Note: Chess piece padded characters are in EscapeSequences.java)
    private static final String EMPTY = "   ";
    private static final String[] FILE_LABELS = {EMPTY, " a ", " b "," c ", " d "," e ", " f "," g ", " h ", EMPTY};

    public static void main(){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

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

        out.print(EscapeSequences.RESET_BG_COLOR + "\n");
    }

    private static void headerFormat(PrintStream out){
        out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
        out.print(EscapeSequences.SET_TEXT_COLOR_BLACK);
    }
}
