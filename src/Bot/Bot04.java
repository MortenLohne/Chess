package Bot;

import Backend.Board;
import Backend.Move;
import Backend.Piece;

import java.util.ArrayList;
import java.util.Random;

public class Bot04 {

    public static int originalColor;
    public static int CURRENT_DEPTH = 0;
    public static final int DEPTH = 3;

    public static void makeMove(Board board, int color) {

        originalColor = color;

        //Generate first move-generation
        ArrayList<Move> moves = board.getAllMoves(color);
        ArrayList<Node04> firstGeneration = new ArrayList<>();
        for (int i = 0; i < moves.size(); i++) {
            Board tempBoard = new Board(board);
            tempBoard.doMove(moves.get(i));
            Node04 node = new Node04(tempBoard, null, i);
            firstGeneration.add(node);
        }

        CURRENT_DEPTH++;

        //Get all the end nodes
        long t0 = System.currentTimeMillis();
        ArrayList<Node04> endNodes = getEndNodes(firstGeneration, getOppositeColor(color));
        long t1 = System.currentTimeMillis();
        double total = (double) (t1 - t0) / 1000;

        if ((int) total != 0) System.out.printf("Nodes processed: %d%n" +
                "Time (seconds): %.2f %n" +
                "Nodes/sec: %d%n" + "================================%n",
                endNodes.size(),
                total,
                endNodes.size() / (int) total);

        Move bestMove = moves.get(minmax(endNodes));
        bestMove.doMove();
        CURRENT_DEPTH = 0;
    }

    public static int minmax(ArrayList<Node04> endNodes) {
        ArrayList<Node04> currentGeneration = endNodes;
        for (int i = DEPTH; i > 1; i--) {
            if (i % 2 == 0) {
                currentGeneration = min(currentGeneration);
            } else {
                currentGeneration = max(currentGeneration);
            }
        }
        int bestMoveIndex;
        if (DEPTH % 2 == 0) {
            bestMoveIndex = getMinNode(currentGeneration, originalColor).getIndex();
        } else {
            bestMoveIndex = getMaxNode(currentGeneration, originalColor).getIndex();
        }
        return bestMoveIndex;
    }

    public static ArrayList<Node04> min(ArrayList<Node04> nodes) {
        ArrayList<ArrayList<Node04>> groups = getFamilies(nodes);
        ArrayList<Node04> selectedChildren = new ArrayList<>();
        for (ArrayList<Node04> list : groups) {
            Node04 child = getMinNode(list, originalColor);
            child.setParent(child.getParent().getParent());
            selectedChildren.add(child);
        }
        return selectedChildren;
    }

    public static ArrayList<Node04> max(ArrayList<Node04> nodes) {
        ArrayList<ArrayList<Node04>> groups = getFamilies(nodes);
        ArrayList<Node04> selectedChildren = new ArrayList<>();
        for (ArrayList<Node04> list : groups) {
            Node04 child = getMaxNode(list, originalColor);
            child.setParent(child.getParent().getParent());
            selectedChildren.add(child);
        }
        return selectedChildren;
    }

    public static ArrayList<ArrayList<Node04>> getFamilies(ArrayList<Node04> nodes) {
        ArrayList<ArrayList<Node04>> groups = new ArrayList<>();
        Node04 currentParent = nodes.get(0).getParent();
        ArrayList<Node04> currentGroup = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node04 parentOfThisNode = nodes.get(i).getParent();
            if (currentParent != parentOfThisNode) {
                groups.add(currentGroup);
                currentGroup = new ArrayList<>();
                currentParent = parentOfThisNode;
            }
            currentGroup.add(nodes.get(i));
            if (i == nodes.size() - 1) groups.add(currentGroup);
        }
        return groups;
    }

    public static Node04 getMaxNode(ArrayList<Node04> nodes, int color) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        ArrayList<Node04> equalNodes = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node04 currentNode = nodes.get(i);
            int value = currentNode.getValue(color);
            if (value > max) {
                max = value;
                index = i;
            }
        }
        for (Node04 node : nodes) {
            int value = node.getValue(color);
            if (value == max) equalNodes.add(node);
        }
        if (equalNodes.size() > 1) {
            Random r = new Random();
            Node04 randomNode = equalNodes.get(r.nextInt(equalNodes.size()));
            return randomNode;
        }
        return nodes.get(index);
    }

    public static Node04 getMinNode(ArrayList<Node04> nodes, int color) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < nodes.size(); i++) {
            Node04 currentNode = nodes.get(i);
            int value = currentNode.getValue(color);
            if (value < min) {
                min = value;
                index = i;
            }
        }
        ArrayList<Node04> equalNodes = new ArrayList<>();
        for (Node04 node : nodes) {
            int value = node.getValue(color);
            if (value == min) equalNodes.add(node);
        }
        if (equalNodes.size() > 1) {
            Random r = new Random();
            Node04 randomNode = equalNodes.get(r.nextInt(equalNodes.size()));
            return randomNode;
        }
        return nodes.get(index);
    }

    public static ArrayList<Node04> getEndNodes(ArrayList<Node04> currentGeneration, int color) {
        if (CURRENT_DEPTH == DEPTH) return currentGeneration;
        ArrayList<Node04> nextGeneration = new ArrayList<>();
        for (Node04 node : currentGeneration) {
            Board board = node.getBoard();
            ArrayList<Move> moves = board.getAllMoves(color);
            for (Move move : moves) {
                Board tempBoard = new Board(board);
                tempBoard.doMove(move);
                Node04 newNode = new Node04(tempBoard, node, node.getIndex());
                nextGeneration.add(newNode);
            }
        }
        CURRENT_DEPTH++;
        return getEndNodes(nextGeneration, getOppositeColor(color));
    }

    private static int getOppositeColor(int color) {
        if (color == Piece.WHITE) return Piece.BLACK;
        return Piece.WHITE;

    }
}
