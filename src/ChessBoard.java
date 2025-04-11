public class ChessBoard {
    private ChessPiece[][] board = new ChessPiece[8][8];

    public ChessBoard(){
        setupInitialPosition();
    }

    public void setupInitialPosition(){
        // TODO starting positions of pieces
    }

    public ChessPiece getPiece(int file, int rank){
        if (file < 0 || file > 7 || rank < 0 || rank > 7){
            return null;
        }
        return board[file][rank];
    }

    public void makeMove(int fromFile, int fromRank, int toFile, int toRank){
        board[toFile][toRank] = board[fromFile][fromRank];
        board[fromFile][fromRank] = null;
    }

    public void printBoard(){
        // TODO need to print current state of the board
    }
}
