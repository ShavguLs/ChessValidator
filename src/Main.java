import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        if (args.length != 1){
//            System.out.println("java main <path>");
//            return;
//        }

        String path = "C:/Users/shadowInstance/Downloads/WorldCup2009.pgn";
        File input = new File(path);

        if(input.isFile() && input.getName().endsWith(".pgn")){
            validateFile(input);
        }else{
            System.out.println("WE HAVE PROBLEM: Input must be a PGN file!!!");
        }
    }

    public static void validateFile(File file){
        try{
            System.out.println("------------------ Validating file: " + file.getName() + " ------------------");
            PgnReader pgnReader = new PgnReader(file);
            List<ChessGame> games = pgnReader.readGames();

            int ok = 0;
            int bad = 0;

            for (int i = 0; i < games.size(); i++) {
                ChessGame g = games.get(i);

                boolean valid = g.validate();

                if (valid){
                    ok++;
//                    System.out.println("Game " + (i+1) + " is valid!");
                }else {
                    bad++;
                    System.out.println("Game " + (i+1) + " is invalid! [" + g.getError() + "]");
                }
//                System.out.println("ln: " + i);
            }

            System.out.println("------------------ Done validating! ------------------");

            System.out.println("------------ RESULT ------------");
            System.out.println("Total: " + games.size() + " games");
            System.out.println("Valid: " + ok + " games");
            System.out.println("Invalid: " + bad + " games");
            System.out.println("------------ RESULT ------------");
        }catch (Exception ex){
            System.out.println("Error in processing file: " + ex.getMessage());
        }
    }
}
