import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* This reads chess game files in PGN format
It splits up multiple games from one file
It removes extra stuff like comments and variations
Then it gives us just the moves in a clean format */

public class PgnReader {
    private File file;

    public PgnReader(File file) {
        this.file = file;
    }

    public List<ChessGame> readGames() throws IOException {
        List<ChessGame> games = new ArrayList<>();
        String content = new String(Files.readAllBytes(file.toPath()));
        String[] gameTexts = content.split("\\[Event ");

        for (int i = 1; i < gameTexts.length; i++) {
            String gameText = "[Event " + gameTexts[i];
            ChessGame game = parseGame(gameText);
            games.add(game);
        }
        return games;
    }

    private ChessGame parseGame(String gameText) {
        ChessGame game = new ChessGame();
        gameText = removeComments(gameText);

        Pattern moveStartPattern = Pattern.compile("(^|\\s)1\\s*\\.");
        Matcher matcher = moveStartPattern.matcher(gameText);

        if (!matcher.find()) {
            Pattern anyMovePattern = Pattern.compile("(^|\\s)\\d+\\s*\\.");
            matcher = anyMovePattern.matcher(gameText);

            if (!matcher.find()) {
                return game;
            }
        }

        int moveTextStart = matcher.start();
        String moveText = gameText.substring(moveTextStart);

        moveText = moveText.replaceAll("1-0", "");
        moveText = moveText.replaceAll("0-1", "");
        moveText = moveText.replaceAll("1/2-1/2", "");
        moveText = moveText.replaceAll("\\*", "");
        moveText = removeVariations(moveText);
        String[] tokens = moveText.split("\\s+");
        List<String> moves = new ArrayList<>();

        for (String token : tokens) {
            if (token == null || token.isEmpty()) {
                continue;
            }

            if (token.matches("\\d+\\..*") ||
                    token.equals("(") || token.equals(")") ||
                    token.equals("1-0") || token.equals("0-1") ||
                    token.equals("1/2-1/2") || token.equals("*")) {
                continue;
            }

            moves.add(token);
        }
        game.setMoves(moves);
        return game;
    }

    private String removeComments(String text) {
        StringBuilder result = new StringBuilder();
        int commentLevel = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '{') {
                commentLevel++;
            } else if (c == '}' && commentLevel > 0) {
                commentLevel--;
            } else if (commentLevel == 0) {
                result.append(c);
            }
        }

        return result.toString();
    }

    private String removeVariations(String text) {
        StringBuilder result = new StringBuilder();
        int parenthesisLevel = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '(') {
                parenthesisLevel++;
            } else if (c == ')' && parenthesisLevel > 0) {
                parenthesisLevel--;
            } else if (parenthesisLevel == 0) {
                result.append(c);
            }
        }

        return result.toString();
    }
}