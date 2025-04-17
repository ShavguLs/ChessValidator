import org.junit.Assert;
import org.junit.Test;

public class ChessTest {

    @Test
    public void testInitialBoardSetup() {
        ChessBoard board = new ChessBoard();

        Assert.assertEquals('R', board.getPiece(0, 0).getType());
        Assert.assertEquals('N', board.getPiece(1, 0).getType());
        Assert.assertEquals('B', board.getPiece(2, 0).getType());
        Assert.assertEquals('Q', board.getPiece(3, 0).getType());
        Assert.assertEquals('K', board.getPiece(4, 0).getType());
        Assert.assertEquals('B', board.getPiece(5, 0).getType());
        Assert.assertEquals('N', board.getPiece(6, 0).getType());
        Assert.assertEquals('R', board.getPiece(7, 0).getType());

        for (int file = 0; file < 8; file++) {
            Assert.assertEquals('P', board.getPiece(file, 1).getType());
            Assert.assertTrue(board.getPiece(file, 1).isWhite());
        }

        Assert.assertEquals('R', board.getPiece(0, 7).getType());
        Assert.assertEquals('N', board.getPiece(1, 7).getType());
        Assert.assertEquals('B', board.getPiece(2, 7).getType());
        Assert.assertEquals('Q', board.getPiece(3, 7).getType());
        Assert.assertEquals('K', board.getPiece(4, 7).getType());
        Assert.assertEquals('B', board.getPiece(5, 7).getType());
        Assert.assertEquals('N', board.getPiece(6, 7).getType());
        Assert.assertEquals('R', board.getPiece(7, 7).getType());

        for (int file = 0; file < 8; file++) {
            Assert.assertEquals('P', board.getPiece(file, 6).getType());
            Assert.assertFalse(board.getPiece(file, 6).isWhite());
        }

        for (int file = 0; file < 8; file++) {
            for (int rank = 2; rank < 6; rank++) {
                Assert.assertNull(board.getPiece(file, rank));
            }
        }
    }

    @Test
    public void testPawnMove() {
        ChessBoard board = new ChessBoard();
        ChessPiece whitePawn = board.getPiece(4, 1);

        Assert.assertTrue(whitePawn.isValidMove(4, 1, 4, 2, board));
        Assert.assertTrue(whitePawn.isValidMove(4, 1, 4, 3, board));
        Assert.assertFalse(whitePawn.isValidMove(4, 1, 4, 4, board));
        Assert.assertFalse(whitePawn.isValidMove(4, 1, 5, 2, board));
        board.makeMove(4, 1, 4, 3);
        whitePawn = board.getPiece(4, 3);
        Assert.assertTrue(whitePawn.isValidMove(4, 3, 4, 4, board));
        Assert.assertFalse(whitePawn.isValidMove(4, 3, 4, 5, board));
    }

    @Test
    public void testKnightMove() {
        ChessBoard board = new ChessBoard();

        ChessPiece whiteKnight = board.getPiece(1, 0);
        Assert.assertNotNull("Knight should exist at b1", whiteKnight);
        Assert.assertEquals('N', whiteKnight.getType());
        Assert.assertTrue(whiteKnight.isWhite());

        boolean canMoveToA3 = whiteKnight.isValidMove(1, 0, 0, 2, board);
        boolean canMoveToC3 = whiteKnight.isValidMove(1, 0, 2, 2, board);

        System.out.println("Knight at b1 can move to a3: " + canMoveToA3);
        System.out.println("Knight at b1 can move to c3: " + canMoveToC3);

        Assert.assertTrue("Knight should move from b1 to a3", canMoveToA3);
        Assert.assertTrue("Knight should move from b1 to c3", canMoveToC3);

        Assert.assertFalse("Knight should not move to b2",
                whiteKnight.isValidMove(1, 0, 1, 1, board));
        Assert.assertFalse("Knight should not move to c2",
                whiteKnight.isValidMove(1, 0, 2, 1, board));
    }

    @Test
    public void testCastling() {
        ChessBoard board = new ChessBoard();

        board.makeMove(5, 0, 5, 3);
        board.makeMove(6, 0, 6, 3);

        Assert.assertTrue(board.isValidCastling(4, 0, 6, 0, true));

        board.makeMove(4, 0, 6, 0);

        Assert.assertEquals('K', board.getPiece(6, 0).getType());
        Assert.assertEquals('R', board.getPiece(5, 0).getType());
        Assert.assertNull(board.getPiece(4, 0));
        Assert.assertNull(board.getPiece(7, 0));
    }

    @Test
    public void testEnPassant() {
        ChessBoard board = new ChessBoard();

        board.makeMove(4, 1, 4, 3);
        board.makeMove(3, 6, 3, 4);
        Assert.assertTrue(board.getPiece(4, 3).isValidMove(4, 3, 3, 4, board)); // e4xd5
        board.makeMove(4, 3, 3, 4);
        Assert.assertEquals('P', board.getPiece(3, 4).getType());
        Assert.assertTrue(board.getPiece(3, 4).isWhite());
        Assert.assertNull(board.getPiece(3, 3));
    }

    @Test
    public void testMoveValidation() {
        ChessGame game = new ChessGame();

        game.setMoves(java.util.Arrays.asList("e4", "e5", "Nf3", "Nc6", "Bb5", "a6", "Ba4", "Nf6", "O-O", "Be7"));

        Assert.assertTrue(game.validateSyntax());
        Assert.assertTrue(game.validate());

        game.setMoves(java.util.Arrays.asList("e4", "e5", "Qh5", "Nc6", "Bc4", "Nf6", "Qxf7#")); //shamati

        Assert.assertTrue(game.validateSyntax());
        Assert.assertTrue(game.validate());
    }

    @Test
    public void testSyntaxValidation() {
        ChessGame game = new ChessGame();
        Assert.assertTrue(game.isValidMoveNotation("e4"));
        Assert.assertTrue(game.isValidMoveNotation("Nf3"));
        Assert.assertTrue(game.isValidMoveNotation("Bxe5"));
        Assert.assertTrue(game.isValidMoveNotation("O-O"));
        Assert.assertTrue(game.isValidMoveNotation("O-O-O"));
        Assert.assertTrue(game.isValidMoveNotation("Qh4+"));
        Assert.assertTrue(game.isValidMoveNotation("e8=Q"));
        Assert.assertTrue(game.isValidMoveNotation("exd5"));
        Assert.assertFalse(game.isValidMoveNotation("e9"));
        Assert.assertFalse(game.isValidMoveNotation("Zf3"));
        Assert.assertFalse(game.isValidMoveNotation("e8=Z"));
        Assert.assertFalse(game.isValidMoveNotation("O-O-O-O"));
    }
}