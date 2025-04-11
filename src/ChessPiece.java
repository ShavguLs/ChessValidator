public class ChessPiece {
    private char type;
    private boolean isWhite;

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

    // TODO movements of pieces
    private boolean isValidKingMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard) {
        return false;
    }

    private boolean isValidQueenMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard) {
        return false;
    }

    private boolean isValidRookMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard) {
        return false;
    }

    private boolean isValidBishopMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard) {
        return false;
    }

    private boolean isValidKnightMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard) {
        return false;
    }

    private boolean isValidPawnMove(int fromFile, int fromRank, int toFile, int toRank, ChessBoard chessBoard) {
        return false;
    }


}
