package Bot;

import BackendImprovement.Board;

public class Node05 {

    private Node05 parent;
    private Board board;
    private int index;

    public Node05(Board board, Node05 parent, int index) {
        this.board = board;
        this.parent = parent;
        this.index = index;
    }

    public int getValue(int color) {
        return board.evaluateBoard(color);
    }

    public Node05 getParent() {
        return parent;
    }

    public Board getBoard() {
        return board;
    }

    public int getIndex() {
        return index;
    }

    public void setParent(Node05 newParent) {
        this.parent = newParent;
    }
}
