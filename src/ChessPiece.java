public class ChessPiece {
    private char type;
    private boolean isWhite;
    private ChessBoard board;

    public ChessPiece(char type, boolean isWhite){
        this.type = type;
        this.isWhite = isWhite;
    }

    public char getType(){
        return type;
    }

    public boolean isWhite(){
        return isWhite;
    }

    public boolean isValidMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard){
        ChessPiece targetPiece = chessBoard.getPiece(toFile, toRank); // !!!!!!!!!!!!
        if (targetPiece != null && targetPiece.isWhite() == this.isWhite){
            return false;
        }

        switch (type){
            case 'P': return isValidPawnMove(fromFile, fromRank, toFile, toRank, chessBoard);
            case 'N': return isValidKnightMove(fromFile, fromRank, toFile, toRank, chessBoard);
            case 'B': return isValidBishopMove(fromFile, fromRank, toFile, toRank, chessBoard);
            case 'R': return isValidRookMove(fromFile, fromRank, toFile, toRank, chessBoard);
            case 'Q': return isValidQueenMove(fromFile, fromRank, toFile, toRank, chessBoard);
            case 'K': return isValidKingMove(fromFile, fromRank, toFile, toRank, chessBoard);
            default: return false;
        }
    }

    private boolean isValidKingMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard) {
        int fileDiff = Math.abs(fromFile - toFile);
        int rankDiff = Math.abs(fromRank - toRank);

        //castling check
        if (rankDiff == 0 && fileDiff == 2){
            return chessBoard.isValidCastling(fromFile, fromRank, toFile, toRank, isWhite);
        }

        return fileDiff <= 1 && rankDiff <= 1;
    }

    private boolean isValidQueenMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard) {
        //queen = rook || bishop

        return isValidRookMove(fromFile, fromRank, toFile, toRank, chessBoard) ||
                isValidBishopMove(fromFile, fromRank, toFile, toRank, chessBoard);
    }

    private boolean isValidRookMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard) {
        if (fromFile != toFile && fromRank != toRank){
            return false;
        }

        //horizontally moving
        if (fromRank == toRank){
            int start = Math.min(fromFile, toFile) + 1;
            int end = Math.max(fromFile, toFile);

            for (int f = start; f < end; f++) {
                if (chessBoard.getPiece(f, fromRank) != null){
                    return false;
                }
            }
        }else { // verticaly moving
            int start = Math.min(fromRank, toRank) + 1;
            int end = Math.max(fromRank, toRank);

            for (int r = start; r < end; r++) {
                if (chessBoard.getPiece(fromFile, r) != null){
                    return false;
                }
            }
         }

        return true;
    }

    private boolean isValidBishopMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard) {
        int df = Math.abs(fromFile - toFile);
        int dr = Math.abs(fromRank - toRank);

        if (df != dr) return false;

        int fStep = (toFile > fromFile) ? 1 : -1;
        int rStep = (toRank > fromRank) ? 1 : -1;

        int f= fromFile + fStep;
        int r = fromRank + rStep;

        while (f != toFile){
            if (chessBoard.getPiece(f, r) != null){
                return false;
            }
            f += fStep;
            r += rStep;
        }

        return true;
    }

    private boolean isValidKnightMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard) {
        int df = Math.abs(fromFile - toFile);
        int dr = Math.abs(fromRank - toRank);

        return (df == 2 && dr == 1) || (df == 1 && dr == 2);
    }

    private boolean isValidPawnMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard) {
        int forward = isWhite ? 1 : -1;

        boolean isCapture = chessBoard.getPiece(toFile, toRank) != null;

        if (fromFile == toFile && !isCapture){
            if (toRank == fromRank + forward){
                return true;
            }

            if ((isWhite && fromRank == 1 && toRank == 3) || ((!isWhite && fromRank == 6 && toRank == 4))){
                int midRank = fromRank + forward;
                return chessBoard.getPiece(fromFile, midRank) == null;
            }
        }

        if (isCapture && Math.abs(fromFile - toFile) == 1 && toRank == fromRank + forward){
            return true;
        }

        return false;
    }
}
