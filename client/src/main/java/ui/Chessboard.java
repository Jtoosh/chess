package ui;

import chess.*;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;


public class Chessboard {

    private static Logger logger;
    static{
      try {
        initLog();
      } catch (IOException e) {
        System.out.println("Could not initialize logger due to: " + e.getMessage());
      }
    }

    //Board dimensions
    private static final int BOARD_SIZE_IN_SQUARES = 10;
    private static final int BOARD_ROWS = 8;

    //Team colors
    private static final ChessGame.TeamColor WHITE = ChessGame.TeamColor.WHITE;

    private static final String LIGHT = "light";
    private static final String DARK = "dark";

    //Padded characters (Note: Chess piece padded characters are in EscapeSequences.java)
    private static final String EMPTY = "   ";
    private static ArrayList<String> fileLables = new ArrayList<>(List.of(" a ", " b "," c ", " d "," e ", " f "," g ", " h "));

    //Indexes for highlighting
    private static int[] indexes = {9,9};
    private static ChessPiece targetPiece;
    private static ArrayList<ChessMove> movesToHighlight;

    private static void initLog() throws IOException {
        logger = Logger.getLogger("Chessboard");
        Level logLevel = Level.INFO;
        logger.setLevel(logLevel);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(logLevel);
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);

    }

    public static void draw(String startColorArg, ChessBoard board, String pieceToHighlight){
        ChessPiece[][] boardMatrix = board.getBoardMatrix();
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        assert board != null;
        assert startColorArg.equals(LIGHT) || startColorArg.equals(DARK);

        boolean lightFlag = startColorArg.equals(LIGHT);
        if (!lightFlag){
            fileLables = new ArrayList<>(fileLables.reversed()) ;}
        int rankNumber;


        if (pieceToHighlight != null){
            indexes = findSquareIndexes(pieceToHighlight);
            targetPiece = boardMatrix[indexes[0]][indexes[1]];
        }

        if (targetPiece != null){
            movesToHighlight = (ArrayList<ChessMove>) targetPiece.pieceMoves(board, new ChessPosition(indexes[0] + 1, indexes[1] + 1 ));
        }

        drawHeaderRow(out);
        for (int i = 0; i < BOARD_ROWS; i++){
            rankNumber = (startColorArg.equals(LIGHT)) ? 7-i : i;
            if (lightFlag){
                drawBoardRow(out, LIGHT, String.format(" %s ", rankNumber + 1), boardMatrix[rankNumber], rankNumber);
            } else {
                drawBoardRow(out, DARK, String.format(" %s ", rankNumber + 1), boardMatrix[rankNumber], rankNumber);
            }
            lightFlag = !lightFlag;
        }
        drawHeaderRow(out);

    }

    private static void drawHeaderRow(PrintStream out){
        headerFormat(out);
        out.print(EMPTY);
        for (String label : fileLables){
            out.print(label);
        }
        out.print(EMPTY);
        reset(out);
        out.print("\n");
    }

    private static void drawBoardRow(PrintStream out, String rowStartColor, String rank, ChessPiece[] row, int i){
        boolean lightFlag = rowStartColor.equals(LIGHT);
        headerFormat(out);
        out.print(rank);

        out.print(EscapeSequences.RESET_TEXT_COLOR);
        for (int j = 0; j < BOARD_SIZE_IN_SQUARES - 2; j++){
            if(i == indexes[0] && j == indexes[1]){
//                System.out.println("Entered piece highlight block");
                out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
            } else if(targetPiece != null && checkSquareForHighlight(i, j)){
                out.print(EscapeSequences.SET_BG_COLOR_MAGENTA);
            }
            else if (lightFlag){
                out.print(EscapeSequences.SET_BG_COLOR_TAN);
            } else{
                out.print(EscapeSequences.SET_BG_COLOR_DARK_BROWN);
            }
            out.print(evaluateSquare(row[j]));
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

    private static int[] findSquareIndexes(String boardSquare){
        String[] parts = boardSquare.split("");
        int col = fileLables.indexOf(" " + parts[0] + " ");
        int row = Integer.parseInt(parts[1]) - 1;
      return new int[]{row, col};
    }

    private static boolean checkSquareForHighlight(int row, int col){
        ChessPosition startPosition = new ChessPosition(indexes[0] + 1, indexes[1] + 1);
        ChessPosition endPosition = new ChessPosition(row + 1, col + 1);
        if (movesToHighlight.contains(new ChessMove(startPosition, endPosition, null))){
            return true;
        }
        else {return false;}
    }
}
