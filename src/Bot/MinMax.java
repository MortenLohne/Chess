package Bot;

import Backend.Board;
import Backend.Move;
import Backend.Piece;

import java.util.ArrayList;
import java.util.Random;

public class MinMax {


    private static int currentDepth = 0;
    private static final int DEPTH = 3;

    public static void makeMove(Board mainBoard, int colorToMove) {
        ArrayList<Move> legalMoves = mainBoard.getAllMoves(colorToMove);
        ArrayList<Node2> firstGeneration = new ArrayList<>();
        for (int i = 0; i < legalMoves.size(); i++) {
            Board tempBoard = new Board(mainBoard);
            Move currentMove = legalMoves.get(i);
            tempBoard.getPiece(currentMove.getox(), currentMove.getoy()).setTile(tempBoard.getTile(currentMove.getnx(), currentMove.getny()));
            Node2 newNode = new Node2(tempBoard, i, colorToMove, null);
            firstGeneration.add(newNode);
        }
        currentDepth++;
        int bestMove = getBestMove(firstGeneration, getOppositeColor(colorToMove));
        Move move = legalMoves.get(bestMove);
        move.doMove();
    }


    public static int getBestMove(ArrayList<Node2> currentGeneration, int color) {
        ArrayList<Node2> nextGeneration = new ArrayList<>();
        for (Node2 currentNode : currentGeneration) {
            Board currentNodeBoard = currentNode.getBoard();
            ArrayList<Move> nextMoveGeneration = currentNodeBoard.getAllMoves(color);
            for (Move m : nextMoveGeneration) {
                Board tempBoard = new Board(currentNodeBoard);
                tempBoard.getPiece(m.getox(), m.getoy()).setTile(tempBoard.getTile(m.getnx(), m.getny()));
                Node2 newNode = new Node2(tempBoard, currentNode.getIndex(), color, currentNode);
                nextGeneration.add(newNode);
            }
        }

        currentDepth++;

        if (currentDepth >= DEPTH) {
            currentDepth = 0;
            return getBestNode(nextGeneration);
        } else {
            return getBestMove(nextGeneration, getOppositeColor(color));
        }
    }

    public static int getBestNode(ArrayList<Node2> nodes) {

        ArrayList<Integer> equalMoves = new ArrayList<>();

        int max = Integer.MIN_VALUE;
        int maxIndex = -1;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getValue() > max) {
                max = nodes.get(i).getValue();
                maxIndex = nodes.get(i).getIndex();
            }
            if (nodes.get(i).getValue() == max) equalMoves.add(new Integer(nodes.get(i).getIndex()));
        }


        if (!equalMoves.isEmpty()) {
            Random r = new Random();
            return equalMoves.get(r.nextInt(equalMoves.size()));
        }
        return maxIndex;
    }

    private static int getOppositeColor(int color) {
        if (color == Piece.WHITE) return Piece.BLACK;
        return Piece.WHITE;
    }
}
