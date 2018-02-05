package Pieces;

import Backend.Chess;
import Backend.Piece;
import Backend.Tile;

public class Bishop extends Piece {

    public Bishop(Tile tile, int color) {
        super(tile, color);
        iconIndex = 4;
        value = 30;
    }

    public Bishop(Tile tile, int color, int numberOfMoves) {
        super(tile, color, numberOfMoves);
        iconIndex = 4;
        value = 30;
    }

    public boolean isValidMove(Tile newTile) {
        if (!super.isValidMove(newTile)) return false;
        //==============================
        int x1 = getX();
        int y1 = getY();
        int x2 = newTile.getxPos();
        int y2 = newTile.getyPos();
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        //==============================
        /*
         * The sprinter can only move diagonally.
         */
        if (Math.abs(deltaX) != Math.abs(deltaY)) return false;
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
                if (tileReference.getBoard().getTile(i ,j).hasPiece()) return false;
            }
        }
        return true;
    }
}
