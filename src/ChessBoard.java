// ChessBoard.java - Fixed
public class ChessBoard {
    private ChessPiece[][] board = new ChessPiece[8][8];
    private int[] enPassantSquare = null;

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
        ChessPiece movingPiece = board[fromFile][fromRank];
        if (movingPiece.getType() == 'P' && fromFile != toFile && board[toFile][toRank] == null) {
            board[toFile][fromRank] = null;
        }
        if (movingPiece.getType() == 'P' && Math.abs(fromRank - toRank) == 2) {
            enPassantSquare = new int[]{fromFile, (fromRank + toRank) / 2};
        } else {
            enPassantSquare = null;
        }
        if (movingPiece.getType() == 'K' && Math.abs(fromFile - toFile) == 2) {
            int rookFromFile = (toFile > fromFile) ? 7 : 0;
            int rookToFile = (toFile > fromFile) ? 5 : 3;

            board[rookToFile][fromRank] = board[rookFromFile][fromRank];
            board[rookFromFile][fromRank] = null;
        }

        board[toFile][toRank] = board[fromFile][fromRank];
        board[fromFile][fromRank] = null;
    }
}