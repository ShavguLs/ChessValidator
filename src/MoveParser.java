import java.util.ArrayList;
import java.util.List;

public class MoveParser {
    public static int[] parseMove(String move, ChessBoard board, boolean isWhiteMove) {
        if (move.equals("O-O")) {
            return isWhiteMove ? new int[]{4, 0, 6, 0} : new int[]{4, 7, 6, 7};
        }
        if (move.equals("O-O-O")) {
            return isWhiteMove ? new int[]{4, 0, 2, 0} : new int[]{4, 7, 2, 7};
        }

        char promotionPiece = ' ';
        if (move.contains("=")) {
            int equalPos = move.indexOf('=');
            if (equalPos > 0 && equalPos < move.length() - 1) {
                promotionPiece = move.charAt(equalPos + 1);
                move = move.substring(0, equalPos);
            }
        }

        if (move.endsWith("+") || move.endsWith("#")) {
            move = move.substring(0, move.length() - 1);
        }

        if (move.length() < 2) {
            return null;
        }

        char destFile = move.charAt(move.length() - 2);
        char destRank = move.charAt(move.length() - 1);

        if (destFile < 'a' || destFile > 'h' || destRank < '1' || destRank > '8') {
            return null;
        }

        int df = destFile - 'a';
        int dr = destRank - '1';

        char pieceType = 'P';
        if (Character.isUpperCase(move.charAt(0)) && "KQRBNP".indexOf(move.charAt(0)) >= 0) {
            pieceType = move.charAt(0);
            move = move.substring(1);
        }

        boolean isCapture = move.contains("x");
        if (isCapture) {
            move = move.replace("x", "");
        }

        int srcFile = -1;
        int srcRank = -1;

        if (move.length() > 2) {
            for (int i = 0; i < move.length() - 2; i++) {
                char hint = move.charAt(i);
                if (hint >= 'a' && hint <= 'h') {
                    srcFile = hint - 'a';
                } else if (hint >= '1' && hint <= '8') {
                    srcRank = hint - '1';
                }
            }
        }
        if (pieceType == 'P' && isCapture && srcFile == -1 && move.length() > 2) {
            char hint = move.charAt(0);
            if (hint >= 'a' && hint <= 'h') {
                srcFile = hint - 'a';
            }
        }
        List<int[]> candidates = new ArrayList<>();

        for (int f = 0; f < 8; f++) {
            if (srcFile != -1 && f != srcFile) continue;

            for (int r = 0; r < 8; r++) {
                if (srcRank != -1 && r != srcRank) continue;

                ChessPiece p = board.getPiece(f, r);

                if (p != null && p.getType() == pieceType && p.isWhite() == isWhiteMove) {
                    if (p.isValidMove(f, r, df, dr, board)) {
                        candidates.add(new int[]{f, r, df, dr});
                    }
                }
            }
        }
        if (candidates.size() == 1) {
            return candidates.get(0);
        }

        if (candidates.size() > 1) {
            if (srcFile != -1 || srcRank != -1) {
                for (int[] candidate : candidates) {
                    if ((srcFile == -1 || candidate[0] == srcFile) &&
                            (srcRank == -1 || candidate[1] == srcRank)) {
                        return candidate;
                    }
                }
            }
            return candidates.get(0);
        }

        if (pieceType == 'K') {
            for (int f = 0; f < 8; f++) {
                for (int r = 0; r < 8; r++) {
                    ChessPiece p = board.getPiece(f, r);
                    if (p != null && p.getType() == 'K' && p.isWhite() == isWhiteMove) {
                        return new int[]{f, r, df, dr};
                    }
                }
            }
        }

        if (pieceType == 'R') {
            for (int f = 0; f < 8; f++) {
                for (int r = 0; r < 8; r++) {
                    ChessPiece p = board.getPiece(f, r);
                    if (p != null && p.getType() == 'R' && p.isWhite() == isWhiteMove) {
                        if (f == df || r == dr) {
                            if (p.isValidMove(f, r, df, dr, board)) {
                                return new int[]{f, r, df, dr};
                            }
                        }
                    }
                }
            }

            for (int f = 0; f < 8; f++) {
                for (int r = 0; r < 8; r++) {
                    ChessPiece p = board.getPiece(f, r);
                    if (p != null && p.getType() == 'R' && p.isWhite() == isWhiteMove) {
                        if (p.isValidMove(f, r, df, dr, board)) {
                            return new int[]{f, r, df, dr};
                        }
                    }
                }
            }
        }
        return null;
    }
}