
public class MoveParser {
    public static int[] parseMove(String move, ChessBoard board, boolean isWhiteMove){
        System.out.println("Parsing move: " + move);
        System.out.println("Board is null: " + (board == null));
        //small castling
        if (move.equals("O-O")){
            return isWhiteMove ? new int[]{4,0,6,0} : new int[]{4,7,6,7};
        }

        //big castling
        if (move.equals("O-O-O")){
            return isWhiteMove ? new int[]{4,0,2,0} : new int[]{4,7,2,7};
        }

        if (move.length() < 2) {
            return null;
        }

        if (move.endsWith("+") || move.endsWith("#")){
            move = move.substring(0, move.length() - 1);
        }

        char destFile = move.charAt(move.length()- 2);
        char destRank = move.charAt(move.length()- 1);

        if (destFile < 'a' || destFile > 'h' || destRank < '1' || destRank > '8'){
            return null;
        }

        int df = destFile - 'a';
        int dr = destRank - '1';

        //type of piece
        char pieceType = 'P'; //def
        if (Character.isUpperCase(move.charAt(0))){
            pieceType = move.charAt(0);
            move = move.substring(1);
        }

        boolean isCapture = move.contains("x");
        if (isCapture){
            move = move.replace("x", "");
        }

        int srcFile = -1;
        int srcRank = -1;

        if (move.length() > 2){
            char hint = move.charAt(0);
            if (hint >= 'a' && hint<= 'h'){
                srcFile = hint - 'a';
            }else if (hint >= '1' && hint <= '8'){
                srcRank = hint - '1';
            }
        }

        for (int f = 0; f < 8; f++) {
            if (srcFile!=-1 && f!=srcFile){
                continue;
            }

            for (int r = 0; r < 8; r++) {
                if (srcRank != -1 && r != srcRank){
                    continue;
                }
                ChessPiece p = board.getPiece(f, r);

                if (p != null && p.getType() == pieceType && p.isWhite() == isWhiteMove){
                    if (p.isValidMove(f, r, df, dr, board)){
                        return new int[]{f, r, df, dr};
                    }
                }
            }
        }

        // if it couldnt find a legal move TODO

        return null;
    }
}
