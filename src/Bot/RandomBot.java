package Bot;

import Backend.Board;
import Backend.Move;
import Backend.Piece;

import java.util.ArrayList;
import java.util.Random;

public class RandomBot {

    private Random random = new Random();

    public void makeMove(Board board, int color) {
        ArrayList<Move> allMoves = board.getAllMoves(color);
        allMoves.get(random.nextInt(allMoves.size())).doMove();
    }
}
