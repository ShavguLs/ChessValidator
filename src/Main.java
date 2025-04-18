import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingUtilities;

/* This is the starting point of the program
It shows a window where you can pick a PGN file
Then it checks all the games in that file
At the end it prints a summary of valid and invalid games */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select PGN File");

            FileNameExtensionFilter filter = new FileNameExtensionFilter("Chess PGN Files (*.pgn)", "pgn");
            fileChooser.setFileFilter(filter);

            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                validateFile(selectedFile);
            } else {
                System.out.println("No file selected. Exiting.");
            }
        });
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