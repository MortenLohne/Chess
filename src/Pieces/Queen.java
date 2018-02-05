package Pieces;

import Backend.Chess;
import Backend.Piece;
import Backend.Tile;

public class Queen extends Piece {

    public Queen(Tile tile, int color) {
        super(tile, color);
        iconIndex = 1;
        value = 90;
    }

    public Queen(Tile tile, int color, int numberOfMoves) {
        super(tile, color, numberOfMoves);
        iconIndex = 1;
        value = 90;
    }

    public boolean isValidMove(Tile newTile) {
        if(!super.isValidMove(newTile)) return false;
        //===============================
        int x1 = getX();
        int y1 = getY();
        int x2 = newTile.getxPos();
        int y2 = newTile.getyPos();
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        //===============================
        /*
         * The sprinter can only move diagonally.
         */
        if ((Math.abs(deltaX) != Math.abs(deltaY)) && (deltaX != 0 && deltaY != 0)) return false;


        if (Math.abs(deltaX) == Math.abs(deltaY)) {
            if (deltaX > 0 && deltaY > 0) {
                for (int i = x1 + 1, j = y1 + 1; i < x2; i++, j++) {
                    if (tileReference.getBoard().getTile(i, j).hasPiece()) return false;
                }
            }
            if (deltaX < 0 && deltaY > 0) {
                for (int i = x1 - 1, j = y1 + 1; j < y2; i--, j++) {
                    if (tileReference.getBoard().getTile(i, j).hasPiece()) return false;
                }
            }
            if (deltaX < 0 && deltaY < 0) {
                for (int i = x1 - 1, j = y1 - 1; i > x2; i--, j--) {
                    if (tileReference.getBoard().getTile(i, j).hasPiece()) return false;
                }
            }
            if (deltaX > 0 && deltaY < 0) {
                for (int i = x1 + 1, j = y1 - 1; i < x2; i++, j--) {
                    if (tileReference.getBoard().getTile(i, j).hasPiece()) return false;
                }
            }
        } else {
            deltaY = y1 - y2;
            /**
             * A rock can only move in one direction, either x or y.
             */
            /**
             * East.
             */
            if (deltaX > 0) {
                for (int i = x1 + 1; i < x2; i++) {
                    boolean hasAPiece = tileReference.getBoard().getTile(i, y1).hasPiece();
                    if (hasAPiece) return false;
                }
            }
            /**
             * West.
             */
            if (deltaX < 0) {
                for (int i = x2 + 1; i < x1; i++) {
                    boolean hasAPiece = tileReference.getBoard().getTile(i, y1).hasPiece();
                    if (hasAPiece) return false;
                }
            }
            /**
             * North.
             */
            if (deltaY > 0) {
                for (int i = y1 - 1; i > y2; i--) {
                    boolean hasAPiece = tileReference.getBoard().getTile(x1, i).hasPiece();
                    if (hasAPiece) return false;
                }
            }
            /**
             * South.
             */
            if (deltaY < 0) {
                for (int i = y1 + 1; i < y2; i++) {
                    boolean hasAPiece = tileReference.getBoard().getTile(x1, i).hasPiece();
                    if (hasAPiece) return false;
                }
            }
        }
        return true;
    }
}
