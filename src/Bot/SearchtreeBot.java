package Bot;

import Backend.Board;
import Backend.Move;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;

public class SearchtreeBot {

    private int currentDepth = 0;
    private final int DEPTH = 3;

    private int nodesProssesed = 0;

    public void makeMove(Board board, int color) {
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Move> allMoves = board.getAllMoves(color);

        for (int i = 0; i < allMoves.size(); i++) {
            Board b = new Board(board);
            Move m = allMoves.get(i);
            b.getPiece(m.getox(), m.getoy()).setTile(b.getTile(m.getnx(), m.getny()));
            Node node = new Node(b, i, color);
            nodes.add(node);
        }
        int bestMove = getBestMove(nodes, color);
        allMoves.get(bestMove).doMove();
    }

    public int getBestMove(ArrayList<Node> nodes, int color) {
        ArrayList<Node> nextGeneration = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {

            System.out.println("Nodes prossesed: " + nodesProssesed);
            nodesProssesed++;

            Node current = nodes.get(i);
            Board nodeBoard = new Board(current.getBoard());
            ArrayList<Move> moves = nodeBoard.getAllMoves(color);

            for (Move m : moves) {
                Board newBoard = new Board(nodeBoard);
                newBoard.getPiece(m.getox(), m.getoy()).setTile(newBoard.getTile(m.getnx(), m.getny()));
                Node node = new Node(newBoard, current.getIndex(), color);
                nextGeneration.add(node);
            }
        }
        currentDepth++;
        if (currentDepth >= DEPTH) {
            currentDepth = 0;
            nodesProssesed = 0;
            return getBestNode(nextGeneration);
        } else {
            return getBestMove(nextGeneration, color);
        }
    }

    public int getBestNode(ArrayList<Node> nodes) {
        int max = Integer.MIN_VALUE;
        int maxIndex = -1;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getValue() > max) {
                max = nodes.get(i).getValue();
                maxIndex = nodes.get(i).getIndex();
            }
        }
        return maxIndex;
    }

    public int getBestMove(Board board, int color) {
        ArrayList<Move> moves = board.getAllMoves(color);
        ArrayList<Board> newBoards = new ArrayList<>();
        for (Move m : moves) {
            Board tempBoard = new Board(board);
            tempBoard.getPiece(m.getox(), m.getoy()).setTile(tempBoard.getTile(m.getnx(), m.getny()));
            newBoards.add(tempBoard);
        }
        return getMaxIndex(newBoards, color);
    }

    public int getMaxIndex(ArrayList<Board> boards, int color) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < boards.size(); i++) {
            int value = boards.get(i).evaluateBoard(color);
            if (value > max) {
                max = value;
                index = i;
            }
        }
        return index;
    }
    /*
    public int getMaxIndex(ArrayList<Integer> numbers) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) > max) {
                max = numbers.get(i);
                index = i;
            }
        }
        return index;
    }
    */
}
