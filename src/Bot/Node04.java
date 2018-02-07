package Bot;

import Backend.Board;

public class Node04 {

    private Node04 parent;
    private Board board;
    private int index;

    public Node04(Board board, Node04 parent, int index) {
        this.board = board;
        this.parent = parent;
        this.index = index;
    }

    public int getValue(int color) {
        return board.evaluateBoard(color);
    }

    public Node04 getParent() {
        return parent;
    }

    public Board getBoard() {
        return board;
    }
}
