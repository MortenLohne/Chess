package Bot;

import Backend.Board;

public class Node03 {

    private Board board;
    private int index;

    public Node03(Board board, int index) {
        this.board = board;
        this.index = index;
    }

    public int evaluateBoard(int color) {
        return board.evaluateBoard(color);
    }

    public Board getBoard() {
        return board;
    }

    public int getIndex() {
        return index;
    }
}
