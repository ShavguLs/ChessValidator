import java.io.File;

public class Main {
    public static void main(String[] args) {
//        if (args.length != 1){
//            System.out.println("java main <path>");
//            return;
//        }

        String path = "C:/Users/shadowInstance/Downloads/Tbilisi2015.pgn";
        File input = new File(path);

        if(input.isFile() && input.getName().endsWith(".pgn")){
            validateFile(input);
        }else{
            System.out.println("WE HAVE PROBLEM: Input must be a PGN file!!!");
        }
    }

    public static void validateFile(File file){
        try{
            System.out.println("Validating file: " + file.getName());
            PgnReader pgnReader = new PgnReader(file);
            pgnReader.readGames();

            System.out.println(file);
        }catch (Exception ex){
            System.out.println("Error in processing file: " + ex.getMessage());
        }
    }
}