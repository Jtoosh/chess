import chess.*;
import dataaccess.DatabaseManager;
import server.Server;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.createDatabase();
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);

        Server server = new Server();
        server.run(8080);
    }
}