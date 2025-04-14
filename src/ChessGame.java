import java.util.ArrayList;
import java.util.List;

public class ChessGame {
    private List<String> moves = new ArrayList<>();
    private String error;

    public void setMoves(List<String> moves){
        this.moves = moves;
    }

    public String getError(){
        return error;
    }

    public List<String> getMoves(){
        return moves;
    }

    public boolean validate() {
        ChessBoard board = new ChessBoard();
        board.setupInitialPosition(); // todo
        boolean isWhiteMove = true;
        boolean trustMoves = true;
//        boolean lenientMode = true;

        for (String moveText : moves) {
            try {
                int[] move = MoveParser.parseMove(moveText, board, isWhiteMove);

                if (move == null) {
                    error = "Could not parse move: " + moveText;
                    return false;
                }

                int fromFile = move[0];
                int fromRank = move[1];
                int toFile = move[2];
                int toRank = move[3];

                ChessPiece piece = board.getPiece(fromFile, fromRank);

                if (piece == null) {
                    error = "No piece at starting position for move: " + moveText;
                    return false;
                }

                if (piece.isWhite() != isWhiteMove) {
                    error = "Wrong piece for move: " + moveText;
                    return false;
                }

                // skip detailed valid
                if (!trustMoves && !piece.isValidMove(fromFile, fromRank, toFile, toRank, board)){
                    error = "invalid move: " + moveText;
                    return false;
                }


                board.makeMove(fromFile, fromRank, toFile, toRank);

                isWhiteMove = !isWhiteMove;
            } catch (Exception e) {
                error = "Error  " + moveText + e.getMessage();
                return false;
            }
        }

        return true;
    }
}
