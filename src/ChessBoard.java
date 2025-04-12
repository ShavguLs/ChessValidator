public class ChessBoard {
    private ChessPiece[][] board = new ChessPiece[8][8];

    public ChessBoard(){
        setupInitialPosition();
    }

    public void setupInitialPosition(){
        for (int file = 0; file < 8; file++) {
            board[file][1] = new ChessPiece('P', true);
            board[file][6] = new ChessPiece('P', false);
        }

        board[0][0] = new ChessPiece('R', true);
        board[7][0] = new ChessPiece('R', true);
        board[0][7] = new ChessPiece('R', false);
        board[7][7] = new ChessPiece('R', false);

        board[1][0] = new ChessPiece('N', true);
        board[6][0] = new ChessPiece('N', true);
        board[1][7] = new ChessPiece('N', false);
        board[6][7] = new ChessPiece('N', false);

        board[2][0] = new ChessPiece('B', true);
        board[5][0] = new ChessPiece('B', true);
        board[2][7] = new ChessPiece('B', false);
        board[5][7] = new ChessPiece('B', false);

        board[3][0] = new ChessPiece('Q', true);
        board[3][7] = new ChessPiece('Q', false);

        board[4][0] = new ChessPiece('K', true);
        board[4][7] = new ChessPiece('K', false);
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
        System.out.println("  a b c d e f g h");
        System.out.println("  ---------------");
        for (int rank = 7; rank >= 0; rank--) {
            System.out.println((rank + 1) + "|");
            for (int file = 0; file < 8; file++) {
                ChessPiece piece = board[file][rank];
                if (piece == null){
                    System.out.println(" ");
                }else {
                    char symbol = piece.getType();
                    if (!piece.isWhite()){
                        symbol = Character.toLowerCase(symbol);
                    }
                    System.out.println(symbol);
                }
                System.out.println(" ");
            }
            System.out.println("|" + (rank + 1));
        }
        System.out.println("  a b c d e f g h");
        System.out.println("  ---------------");
    }
}
