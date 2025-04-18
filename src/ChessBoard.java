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

    public int[] getEnPassantSquare(){
        return enPassantSquare;
    }

    public boolean isValidCastling(int fromFile, int fromRank, int toFile, int toRank, boolean isWhite) {
        if (fromFile != 4 || fromRank != (isWhite ? 0 : 7)) {
            return false;
        }
        if (toRank != fromRank || (toFile != 2 && toFile != 6)) {
            return false;
        }
        if (isSquareUnderAttack(fromFile, fromRank, !isWhite)) {
            return false;
        }
        int step = (toFile > fromFile) ? 1 : -1;
        int midFile = fromFile + step;
        if (isSquareUnderAttack(midFile, fromRank, !isWhite)) {
            return false;
        }
        if (toFile == 2) { //  (O-O-O)
            for (int f = 1; f < 4; f++) {
                if (f != fromFile && getPiece(f, fromRank) != null) {
                    return false;
                }
            }
            ChessPiece rook = getPiece(0, fromRank);
            return rook != null && rook.getType() == 'R' && rook.isWhite() == isWhite;
        } else { // (O-O)
            for (int f = 5; f < 7; f++) {
                if (getPiece(f, fromRank) != null) {
                    return false;
                }
            }
            ChessPiece rook = getPiece(7, fromRank);
            return rook != null && rook.getType() == 'R' && rook.isWhite() == isWhite;
        }
    }

    public boolean isSquareUnderAttack(int file, int rank, boolean byWhite) {
        int pawnDirection = byWhite ? 1 : -1;
        if (file > 0) {
            ChessPiece attacker = getPiece(file - 1, rank - pawnDirection);
            if (attacker != null && attacker.getType() == 'P' && attacker.isWhite() == byWhite) {
                return true;
            }
        }
        if (file < 7) {
            ChessPiece attacker = getPiece(file + 1, rank - pawnDirection);
            if (attacker != null && attacker.getType() == 'P' && attacker.isWhite() == byWhite) {
                return true;
            }
        }
        int[][] knightMoves = {
                {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };
        for (int[] move : knightMoves) {
            int f = file + move[0];
            int r = rank + move[1];
            if (f >= 0 && f < 8 && r >= 0 && r < 8) {
                ChessPiece attacker = getPiece(f, r);
                if (attacker != null && attacker.getType() == 'N' && attacker.isWhite() == byWhite) {
                    return true;
                }
            }
        }
        int[][] kingMoves = {
                {-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
                {0, 1}, {1, -1}, {1, 0}, {1, 1}
        };
        for (int[] move : kingMoves) {
            int f = file + move[0];
            int r = rank + move[1];
            if (f >= 0 && f < 8 && r >= 0 && r < 8) {
                ChessPiece attacker = getPiece(f, r);
                if (attacker != null && attacker.getType() == 'K' && attacker.isWhite() == byWhite) {
                    return true;
                }
            }
        }
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] dir : directions) {
            int f = file + dir[0];
            int r = rank + dir[1];
            while (f >= 0 && f < 8 && r >= 0 && r < 8) {
                ChessPiece attacker = getPiece(f, r);
                if (attacker != null) {
                    if (attacker.isWhite() == byWhite &&
                            (attacker.getType() == 'R' || attacker.getType() == 'Q')) {
                        return true;
                    }
                    break;
                }
                f += dir[0];
                r += dir[1];
            }
        }

        int[][] diagonals = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] dir : diagonals) {
            int f = file + dir[0];
            int r = rank + dir[1];
            while (f >= 0 && f < 8 && r >= 0 && r < 8) {
                ChessPiece attacker = getPiece(f, r);
                if (attacker != null) {
                    if (attacker.isWhite() == byWhite &&
                            (attacker.getType() == 'B' || attacker.getType() == 'Q')) {
                        return true;
                    }
                    break;
                }
                f += dir[0];
                r += dir[1];
            }
        }
        return false;
    }
}