import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class GameValidationTest {
    @Test
    public void testValidMoveNotation() {
        ChessGame game = new ChessGame();

        Assert.assertTrue("e4 should be valid", game.isValidMoveNotation("e4"));
        Assert.assertTrue("Nf3 should be valid", game.isValidMoveNotation("Nf3"));
        Assert.assertTrue("Bxc4 should be valid", game.isValidMoveNotation("Bxc4"));
        Assert.assertTrue("O-O should be valid", game.isValidMoveNotation("O-O"));
        Assert.assertTrue("O-O-O should be valid", game.isValidMoveNotation("O-O-O"));
        Assert.assertTrue("exd5 should be valid", game.isValidMoveNotation("exd5"));
        Assert.assertTrue("Qxf7+ should be valid", game.isValidMoveNotation("Qxf7+"));
        Assert.assertTrue("e8=Q should be valid", game.isValidMoveNotation("e8=Q"));
        Assert.assertTrue("Rbe1 should be valid", game.isValidMoveNotation("Rbe1"));
        Assert.assertTrue("R1e4 should be valid", game.isValidMoveNotation("R1e4"));
        Assert.assertFalse("e9 should be invalid (rank out of range)", game.isValidMoveNotation("e9"));
        Assert.assertFalse("i4 should be invalid (file out of range)", game.isValidMoveNotation("i4"));
        Assert.assertFalse("Zf3 should be invalid (invalid piece)", game.isValidMoveNotation("Zf3"));
        Assert.assertFalse("e8=Z should be invalid (invalid promotion piece)", game.isValidMoveNotation("e8=Z"));
        Assert.assertFalse("O-O-O-O should be invalid (invalid castling)", game.isValidMoveNotation("O-O-O-O"));
    }

    @Test
    public void testValidGame() {
        ChessGame game = new ChessGame();
        // basic opening sequence
        List<String> validMoves = Arrays.asList("e4", "e5", "Nf3", "Nc6", "Bb5", "a6", "Ba4", "Nf6", "O-O", "Be7", "Re1", "b5", "Bb3", "O-O"
        );

        game.setMoves(validMoves);

        Assert.assertTrue("Syntax should be valid", game.validateSyntax());
        Assert.assertTrue("Game should be valid", game.validate());
    }

    @Test
    public void testSyntaxErrorInGame() {
        ChessGame game = new ChessGame();
        // syntax error game
        List<String> movesWithSyntaxError = Arrays.asList("e4", "e5", "Nf3", "Nc6", "Bb5", "a6", "Ba4", "N@6", "O-O", "Be7");

        game.setMoves(movesWithSyntaxError);

        Assert.assertFalse("Syntax should be invalid", game.validateSyntax());
        Assert.assertFalse("Game should be invalid", game.validate());
        Assert.assertTrue("Parse errors should be reported", game.getParseErrors().size() > 0);
    }

    @Test
    public void testIllegalMoveInGame() {
        ChessGame game = new ChessGame();

        // game with illegal move
        List<String> movesWithIllegalMove = Arrays.asList("e4", "e5", "Nf3", "Nc6", "Bb5", "a6", "Ba4", "Nf6", "O-O", "Be7", "Re1", "b5", "Qh5" // illegal move
        );

        game.setMoves(movesWithIllegalMove);

        Assert.assertTrue("Syntax should be valid", game.validateSyntax());
        Assert.assertFalse("Game should be invalid", game.validate());
        Assert.assertNotNull("Error should be reported", game.getError());
    }

    @Test
    public void testEmptyGame() {
        ChessGame game = new ChessGame();

        Assert.assertTrue("Empty game syntax should be valid", game.validateSyntax());
        Assert.assertTrue("Empty game should be valid", game.validate());
    }

    @Test
    public void testPawnCapture() {
        ChessGame game = new ChessGame();
        List<String> movesWithCapture = Arrays.asList("e4", "d5", "exd5", "Qxd5", "Nc3", "Qa5");

        game.setMoves(movesWithCapture);

        Assert.assertTrue("Syntax should be valid", game.validateSyntax());
        Assert.assertTrue("Game should be valid", game.validate());
    }

    @Test
    public void testEnPassant() {
        ChessGame game = new ChessGame();
        List<String> movesWithEnPassant = Arrays.asList("e4", "Nf6", "e5", "d5", "exd6");

        game.setMoves(movesWithEnPassant);
        Assert.assertTrue("Syntax should be valid", game.validateSyntax());
        Assert.assertTrue("Game with en passant should be valid", game.validate());
    }
}