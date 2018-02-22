package Bot;



import BackendImprovement.Board;
import BackendImprovement.Move;
import Frontend.Graphcis2;

import java.util.ArrayList;
import java.util.Random;

public class Bot05 {

    public static int originalColor;
    public static int CURRENT_DEPTH = 0;
    public static int DEPTH = 4;

    public static boolean shouldStop = false;

    public Board botBoard;

    public static long t0;

    public Bot05() {
        botBoard = new Board();
    }

    public static void makeMove(Board board, int color, int depth) {
        DEPTH = depth;
        makeMove(board, color);
        DEPTH = 4;
    }

    public static void makeMove(Board board, int color) {

        originalColor = color;

        //Generate first move-generation
        ArrayList<Move> moves = board.getAllMoves(color);
        ArrayList<Node05> firstGeneration = new ArrayList<>();
        for (int i = 0; i < moves.size(); i++) {
            Board tempBoard = new Board(board);
            tempBoard.doMove(moves.get(i));
            Node05 node = new Node05(tempBoard, null, i);
            firstGeneration.add(node);
        }

        CURRENT_DEPTH++;

        System.out.println("info depth " + CURRENT_DEPTH + " nodes " + firstGeneration.size() + " time 0");

        //Get all the end nodes
        t0 = System.currentTimeMillis();
        ArrayList<Node05> endNodes = getEndNodes(firstGeneration, getOppositeColor(color));
        long t1 = System.currentTimeMillis();
        double total = (double) (t1 - t0) / 1000;

        /*
        if ((int) total != 0) System.out.printf("Nodes processed: %d%n" +
                        "Time (seconds): %.2f %n" +
                        "Nodes/sec: %d%n" + "================================%n",
                endNodes.size(),
                total,
                endNodes.size() / (int) total);
        */



        Move bestMove = moves.get(minmax(endNodes));
        System.out.println("bestmove " + bestMove.toString());

        /*
        TODO: Remove this for guiplay
        board.doMove(bestMove);
        */
        CURRENT_DEPTH = 0;
    }

    public static int minmax(ArrayList<Node05> endNodes) {
        ArrayList<Node05> currentGeneration = endNodes;
        for (int i = CURRENT_DEPTH; i > 1; i--) {
            if (i % 2 == 0) {
                currentGeneration = min(currentGeneration);
            } else {
                currentGeneration = max(currentGeneration);
            }
        }
        int bestMoveIndex;


        bestMoveIndex = getMaxNode(currentGeneration, originalColor).getIndex();

        /*
        if (originalColor == Board.BLACK) {
            if (DEPTH % 2 == 0) {
                bestMoveIndex = getMinNode(currentGeneration, originalColor).getIndex();
            } else {
                bestMoveIndex = getMaxNode(currentGeneration, originalColor).getIndex();
            }
        } else {
            if (DEPTH % 2 == 0) {
                bestMoveIndex = getMinNode(currentGeneration, originalColor).getIndex();
            } else {
                bestMoveIndex = getMinNode(currentGeneration, originalColor).getIndex();
            }
        }
        */
        return bestMoveIndex;
    }

    public static ArrayList<Node05> min(ArrayList<Node05> nodes) {
        ArrayList<ArrayList<Node05>> groups = getFamilies(nodes);
        ArrayList<Node05> selectedChildren = new ArrayList<>();
        for (ArrayList<Node05> list : groups) {
            Node05 child = getMinNode(list, originalColor);
            child.setParent(child.getParent().getParent());
            selectedChildren.add(child);
        }
        return selectedChildren;
    }

    public static ArrayList<Node05> max(ArrayList<Node05> nodes) {
        ArrayList<ArrayList<Node05>> groups = getFamilies(nodes);
        ArrayList<Node05> selectedChildren = new ArrayList<>();
        for (ArrayList<Node05> list : groups) {
            Node05 child = getMaxNode(list, originalColor);
            child.setParent(child.getParent().getParent());
            selectedChildren.add(child);
        }
        return selectedChildren;
    }

    public static ArrayList<ArrayList<Node05>> getFamilies(ArrayList<Node05> nodes) {
        ArrayList<ArrayList<Node05>> groups = new ArrayList<>();
        Node05 currentParent = nodes.get(0).getParent();
        ArrayList<Node05> currentGroup = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node05 parentOfThisNode = nodes.get(i).getParent();
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

    public static Node05 getMaxNode(ArrayList<Node05> nodes, int color) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        ArrayList<Node05> equalNodes = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node05 currentNode = nodes.get(i);
            int value = currentNode.getValue(color);
            if (value > max) {
                max = value;
                index = i;
            }
        }
        for (Node05 node : nodes) {
            int value = node.getValue(color);
            if (value == max) equalNodes.add(node);
        }
        if (equalNodes.size() > 1) {
            Random r = new Random();
            Node05 randomNode = equalNodes.get(r.nextInt(equalNodes.size()));
            return randomNode;
        }
        return nodes.get(index);
    }

    public static Node05 getMinNode(ArrayList<Node05> nodes, int color) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < nodes.size(); i++) {
            Node05 currentNode = nodes.get(i);
            int value = currentNode.getValue(color);
            if (value < min) {
                min = value;
                index = i;
            }
        }
        ArrayList<Node05> equalNodes = new ArrayList<>();
        for (Node05 node : nodes) {
            int value = node.getValue(color);
            if (value == min) equalNodes.add(node);
        }
        if (equalNodes.size() > 1) {
            Random r = new Random();
            Node05 randomNode = equalNodes.get(r.nextInt(equalNodes.size()));
            return randomNode;
        }
        return nodes.get(index);
    }

    public static ArrayList<Node05> getEndNodes(ArrayList<Node05> currentGeneration, int color) {
        if (CURRENT_DEPTH == DEPTH) return currentGeneration;
        ArrayList<Node05> nextGeneration = new ArrayList<>();
        for (Node05 node : currentGeneration) {
            Board board = node.getBoard();
            ArrayList<Move> moves = board.getAllMoves(color);
            for (Move move : moves) {
                if (shouldStop) {
                    shouldStop = false;
                    return currentGeneration;
                }
                Board tempBoard = new Board(board);
                tempBoard.doMove(move);
                Node05 newNode = new Node05(tempBoard, node, node.getIndex());
                nextGeneration.add(newNode);
            }
        }
        CURRENT_DEPTH++;
        System.out.println("info depth " + CURRENT_DEPTH + " nodes " + nextGeneration.size() +
            " time " + (System.currentTimeMillis() - t0));
        return getEndNodes(nextGeneration, getOppositeColor(color));
    }

    private static int getOppositeColor(int color) {
        if (color == Board.WHITE) return Board.BLACK;
        return Board.WHITE;
    }

    public void setStartpos() {
        botBoard = new Board();
    }

    public void execMove(Move m) {
        botBoard.doMove(m);
    }

    public void execMoves(ArrayList<Move> moves) {
        for (Move m : moves) {
            execMove(m);
        }
    }
}
