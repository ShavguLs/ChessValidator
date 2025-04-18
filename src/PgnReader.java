import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        moveText = moveText.replaceAll("\\{[^}]*\\}", " ");

//        System.out.println(moveText.substring(0, moveText.length()));

        moveText = moveText.replaceAll("\\{[^}]*\\}", " ");

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
}