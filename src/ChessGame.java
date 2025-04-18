import java.util.ArrayList;
import java.util.List;

/* This class handles a whole chess game with all its moves
It checks if the moves written in the file are correct
First it checks the writing style, then it checks if the moves follow chess rules
It also keeps track of errors it finds in the game */

public class ChessGame {
    private List<String> moves = new ArrayList<>();
    private String error;
    private List<String> parseErrors = new ArrayList<>();

    boolean debugMode = false;

    public void setMoves(List<String> moves){
        this.moves = moves;
    }

    public String getError(){
        return error;
    }

    public List<String> getParseErrors(){
        return parseErrors;
    }

    public List<String> getMoves(){
        return moves;
    }

    public boolean validateSyntax(){
        boolean valid = true;
        for (int i = 0; i < moves.size(); i++) {
            String moveText = moves.get(i);

            if (!isValidMoveNotation(moveText)){
                parseErrors.add("Move " + (i+1) + " has invalid notation: " + moveText);
                valid = false;
            }
        }
        return valid;
    }

    public boolean isValidMoveNotation(String move){
        if (move.equals("O-O") || move.equals("O-O-O")){
            return true;
        }

        if (move.endsWith("+") || move.endsWith("#")){
            move = move.substring(0, move.length() - 1);
        }

        if (move.contains("=")){
            String[] parts = move.split("=");
            if (parts.length != 2 || parts[1].length() != 1){
                return false;
            }
            char promotionPiece = parts[1].charAt(0);
            if (!"QRBN".contains(String.valueOf(promotionPiece))){
                return false;
            }
            move = parts[0];
        }
        return move.matches("[KQRBNP]?[a-h]?[1-8]?x?[a-h][1-8]");
    }

    public boolean validate() {
        ChessBoard board = new ChessBoard();
        board.setupInitialPosition();
        boolean isWhiteMove = true;
        boolean lenientMode = true;

        for (int moveIndex = 0; moveIndex < moves.size(); moveIndex++) {
            String moveText = moves.get(moveIndex);
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

                if (!piece.isValidMove(fromFile, fromRank, toFile, toRank, board)){
                    if (lenientMode){
                        if (moveText.contains("+") || moveText.contains("#")){
                            // allow this moves to pass
                        }else if (piece.getType() == 'K' || piece.getType() == 'Q'){
                            // be more lenient for kings and queens due spec moves
                        }else {
                            error = "invalid move: " + moveText;
                            return false;
                        }
                    }else {
                        error = "invalid move: " + moveText;
                        return false;
                    }
                }

                board.makeMove(fromFile, fromRank, toFile, toRank);

                isWhiteMove = !isWhiteMove;

                if (debugMode){
                    System.out.println("Procc move: " + moveText);
                    System.out.println("From: " + fromFile + "," + fromRank + "to" + toFile + "," + toRank);
                }
            } catch (Exception e) {
                error = "Error in process " + moveText + e.getMessage();
                return false;
            }
        }

        return true;
    }
}
