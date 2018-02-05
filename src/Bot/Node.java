package Bot;

import Backend.Board;

public class Node {

    private Board board;
    private int index;
    private int value;
    private int color;

    public Node(Board board, int index, int color) {
        this.board = board;
        this.index = index;
        this.color = color;
    }

    private void calculateValue() {
        value = board.evaluateBoard(color);
    }

    public int getValue() {
        calculateValue();
        return value;
    }

    public Board getBoard() {
        return board;
    }

    public int getIndex() {
        return index;
    }
}
