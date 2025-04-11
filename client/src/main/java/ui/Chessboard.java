package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;


public class Chessboard {

    private static Logger logger;
    static{
        initLog();
    }

    //Board dimensions
    private static final int BOARD_SIZE_IN_SQUARES = 10;
    private static final int BOARD_ROWS = 8;

    //Team colors
    private static final ChessGame.TeamColor WHITE = ChessGame.TeamColor.WHITE;

    //Constants for which color square to make/start with
    private static final String LIGHT = "light";
    private static final String DARK = "dark";

    //Padded characters (Note: Chess piece padded characters are in EscapeSequences.java)
    private static final String EMPTY = "   ";
    private static final ArrayList<String> FILE_LABLES= new ArrayList<>(List.of(" a ", " b "," c ", " d "," e ", " f "," g ", " h "));
    private static  ArrayList<String> fileLablesInUse;

    //Indexes, ChessPiece and ArrayList for highlighting
    private static int[] indexes = {9,9};
    private static ChessPiece targetPiece;
    private static ArrayList<ChessMove> movesToHighlight = new ArrayList<>();

    private static String startColorGlobal;

    //Logger setup
    private static void initLog(){
        logger = Logger.getLogger("Chessboard");
        Level logLevel = Level.INFO;
        logger.setLevel(logLevel);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(logLevel);
        consoleHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(consoleHandler);
    }

    public static void draw(String startColorArg, ChessBoard board, String pieceToHighlight, boolean isGameStart){
        // parameter checks
        if (!startColorArg.equals(LIGHT) && !startColorArg.equals(DARK)){
            throw new IllegalArgumentException("The startColorArg parameter must have a value \"light\" or \"dark\"");
        }
        startColorGlobal = startColorArg;

        //Clear out old highlight data
        movesToHighlight.clear();
        targetPiece = null;
        indexes =new int[]{9, 9};

        ChessPiece[][] boardMatrix = board.getBoardMatrix();
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        //Determines the color to make the first square, which direction the file labes will go
        boolean lightFlag = true;
        if (!startColorArg.equals(LIGHT)){
            fileLablesInUse = new ArrayList<>(FILE_LABLES.reversed()) ;
        } else{
            fileLablesInUse =FILE_LABLES;
        }
        int rankNumber;

        //finds the indexes for the piece to highlight by parsing the string arguement
        if (pieceToHighlight != null){
            indexes = findSquareIndexes(pieceToHighlight);
            targetPiece = boardMatrix[indexes[0]][indexes[1]];
        }
        //Sets the piece to highlight
        if (targetPiece != null){
            movesToHighlight = (ArrayList<ChessMove>) targetPiece.pieceMoves(board, new ChessPosition(indexes[0] + 1, indexes[1] + 1 ));
        }

        drawHeaderRow(out);
        //Body rows loop
        for (int i = 0; i < BOARD_ROWS; i++){
            rankNumber = (startColorArg.equals(LIGHT)) ? 7-i : i;
            if (lightFlag){
                ChessPiece[] rowToDraw = boardMatrix[rankNumber];
                drawBoardRow(out, LIGHT, String.format(" %s ", rankNumber + 1), rowToDraw, rankNumber);
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
        for (String label : fileLablesInUse){
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
        if (startColorGlobal.equals(DARK)){
            for (int j = 7; j >= 0; j-- ){
                boardRowLoop(out, i, j, lightFlag, row);
                lightFlag = !lightFlag;
            }
        } else{
            for (int j = 0; j < BOARD_SIZE_IN_SQUARES - 2; j++){
                boardRowLoop(out, i, j, lightFlag, row);
                lightFlag = !lightFlag;
            }
        }
        //Formats for the rank column, prints the rank, resets formatting, then goes to a new line
        headerFormat(out);
        out.print(rank);
        reset(out);
        out.print("\n");

    }

    //Determines which piece type goes on a square
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

    public static int[] findSquareIndexes(String boardSquare){
        String[] parts = boardSquare.split("");
        int col = fileLablesInUse.indexOf(" " + parts[0] + " ");
        int row = Integer.parseInt(parts[1]) - 1;
      return new int[]{row, col};
    }

    private static boolean checkSquareForHighlight(int row, int col) {
        ChessPosition startPosition=new ChessPosition(indexes[0] + 1, indexes[1] + 1);
        ChessPosition endPosition=new ChessPosition(row + 1, col + 1);
        return movesToHighlight.contains(new ChessMove(startPosition, endPosition, null));
    }

    private static void boardRowLoop(PrintStream out, int rankNumber, int fileNumber, boolean lightFlag, ChessPiece[] row){
        if(rankNumber == indexes[0] && fileNumber == indexes[1]){
            out.print(EscapeSequences.SET_BG_COLOR_YELLOW);
        } else if(targetPiece != null && checkSquareForHighlight(rankNumber, fileNumber)){
            out.print(EscapeSequences.SET_BG_COLOR_MAGENTA);
        }
        else if (lightFlag){
            out.print(EscapeSequences.SET_BG_COLOR_TAN);
        } else{
            out.print(EscapeSequences.SET_BG_COLOR_DARK_BROWN);
        }
        out.print(evaluateSquare(row[fileNumber]));

    }



}
