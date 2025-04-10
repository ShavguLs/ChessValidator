import java.util.ArrayList;
import java.util.List;

public class ChessGame {
    private List<String> moves = new ArrayList<>();
    private String error;

    public void setMoves(List<String> moves){
        this.moves = moves;
    }

    public String getError(){
        return error;
    }

    public List<String> getMoves(){
        return moves;
    }

    public boolean validate(){
        // TODO need here chessboard inst,
        //  need to detect move is valid or not

        return true;
    }
}
