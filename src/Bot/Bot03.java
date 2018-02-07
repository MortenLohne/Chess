package Bot;

import Backend.Board;
import Backend.Move;
import Backend.Piece;

import java.util.ArrayList;
import java.util.Random;

public class Bot03 {

    public static int originalColor;

    public static int CURRENT_DEPTH = 0;
    public static final int DEPTH = 2;
    //Odds are max

    public static void makeMove(Board board, int color) {
        originalColor = color;
        ArrayList<Move> allLegalMoves = board.getAllMoves(color);
        ArrayList<Node03> firstGeneration = new ArrayList<>();
        for (int i = 0; i < allLegalMoves.size(); i++) {
            Board tempBoard = new Board(board);
            tempBoard.doMove(allLegalMoves.get(i));
            Node03 n = new Node03(tempBoard, i);
            firstGeneration.add(n);
        }
        CURRENT_DEPTH++;
        int bestMove = getBestMove(firstGeneration, getOppositeColor(color));

        Move m = allLegalMoves.get(bestMove);

        board.doMove(m);
    }


    private static int getBestMove(ArrayList<Node03> currentGeneration, int color) {
        if (CURRENT_DEPTH == DEPTH) {
            CURRENT_DEPTH = 0;
            if (color == originalColor) {
                return min(currentGeneration, getOppositeColor(originalColor));
            } else {
                return max(currentGeneration, originalColor);
            }
        }
        ArrayList<Node03> nextGeneration = new ArrayList<>();
        for (int i = 0; i < currentGeneration.size(); i++) {
            Node03 currentNode = currentGeneration.get(i);
            Board currentBoard = currentNode.getBoard();
            ArrayList<Move> nextMoveGeneration = currentBoard.getAllMoves(color);
            for (Move m : nextMoveGeneration) {
                Board tempBoard = new Board(currentBoard);
                tempBoard.doMove(m);
                Node03 n = new Node03(tempBoard, currentNode.getIndex());
                nextGeneration.add(n);
            }
        }
        CURRENT_DEPTH++;
        return getBestMove(nextGeneration, getOppositeColor(color));
    }

    public static int max(ArrayList<Node03> boards, int color) {
        ArrayList<Node03> equalNodes = new ArrayList<>();

        int bestIndex = -1;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < boards.size(); i++) {
            Node03 currentNode = boards.get(i);
            int value = currentNode.evaluateBoard(color);
            if (value > max) {
                max = value;
                bestIndex = currentNode.getIndex();
            }
        }

        for (int i = 0; i < boards.size(); i++) {
            Node03 currentNode = boards.get(i);
            if (currentNode.evaluateBoard(color) == max) {
                equalNodes.add(currentNode);
            }
        }

        if (equalNodes.size() > 1) {
            return equalNodes.get(new Random().nextInt(equalNodes.size())).getIndex();
        }
        return bestIndex;
    }

    public static int min(ArrayList<Node03> boards, int color) {

        ArrayList<Node03> equalNodes = new ArrayList<>();

        int bestIndex = -1;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < boards.size(); i++) {
            Node03 currentNode = boards.get(i);
            int value = currentNode.evaluateBoard(color);
            if (value < min) {
                min = value;
                bestIndex = currentNode.getIndex();
            }
        }

        for (int i = 0; i < boards.size(); i++) {
            Node03 currentNode = boards.get(i);
            if (currentNode.evaluateBoard(color) == min) {
                equalNodes.add(currentNode);
            }
        }

        if (equalNodes.size() > 1) {
            return equalNodes.get(new Random().nextInt(equalNodes.size())).getIndex();
        }
        return bestIndex;
    }

    public static int getBestBoard(ArrayList<Node03> boards, int color) {

        ArrayList<Node03> equalNodes = new ArrayList<>();

        int bestIndex = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < boards.size(); i++) {
            Node03 currentNode = boards.get(i);
            int value = currentNode.evaluateBoard(color);
            if (value >= max) {
                max = value;
                bestIndex = currentNode.getIndex();
                equalNodes.add(currentNode);
            }
        }

        if (equalNodes.size() > 1) {
            return equalNodes.get(new Random().nextInt(equalNodes.size())).getIndex();
        }

        return bestIndex;
    }

    private static int getOppositeColor(int color) {
        if (color == Piece.WHITE) return Piece.BLACK;
        return Piece.WHITE;
    }
}
