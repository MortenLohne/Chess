package Bot;

import Backend.Board;
import Backend.Piece;

public class Node2 {

    private Board board;
    private int index;
    private int value;

    public Node2(Board board, int index, int color, Node2 previousNode) {
        this.board = board;
        this.index = index;
        if (previousNode != null) this.value = previousNode.getValue();
        this.value += board.evaluateBoard(color);
    }

    public int getValue() {
        return value;
    }

    public Board getBoard() {
        return board;
    }

    public int getIndex() {
        return index;
    }
}
