import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        if (args.length != 1) {
//            System.out.println("Usage: java Main <pgn_file_path>");
//            System.out.println("Example: java Main games.pgn");
//            return;
//        }

        String path = "C://Users//shadowInstance//Downloads/WorldCup2009.pgn";
        File input = new File(path);

        if (input.isFile() && input.getName().endsWith(".pgn")) {
            validateFile(input);
        } else {
            System.out.println("Error: Input must be a PGN file!");
            System.out.println("Please specify a valid .pgn file path.");
        }
    }
    public static void validateFile(File file) {
        try {
            System.out.println("===============================================");
            System.out.println("Validating file: " + file.getName());
            System.out.println("===============================================");
            PgnReader pgnReader = new PgnReader(file);
            List<ChessGame> games = pgnReader.readGames();

            int validGames = 0;
            int invalidGames = 0;
            int syntaxErrors = 0;
            int moveErrors = 0;

            for (int i = 0; i < games.size(); i++) {
                ChessGame game = games.get(i);
//                System.out.print("Game " + (i+1) + ": ");

                boolean validSyntax = game.validateSyntax();
                if (!validSyntax) {
                    System.out.println("INVALID - Syntax errors");
                    for (String error : game.getParseErrors()) {
                        System.out.println("  - " + error);
                    }
                    invalidGames++;
                    syntaxErrors++;
                    continue;
                }

                boolean validMoves = game.validate();
                if (validMoves) {
//                    System.out.println("VALID");
                    validGames++;
                } else {
                    System.out.println("INVALID - " + game.getError());
                    invalidGames++;
                    moveErrors++;
                }
            }

            System.out.println("===============================================");
            System.out.println("VALIDATION SUMMARY");
            System.out.println("===============================================");
            System.out.println("Total games: " + games.size());
            System.out.println("Valid games: " + validGames);
            System.out.println("Invalid games: " + invalidGames);
            System.out.println("  - Games with syntax errors: " + syntaxErrors);
            System.out.println("  - Games with illegal moves: " + moveErrors);
            System.out.println("===============================================");
        } catch (Exception ex) {
            System.out.println("Error processing file: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}