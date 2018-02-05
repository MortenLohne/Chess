package Pieces;

import Backend.Chess;
import Backend.Piece;
import Backend.Tile;

public class Rook extends Piece {

    public Rook(Tile tile, int color) {
        super(tile, color);
        iconIndex = 2;
        value = 50;
    }

    public Rook(Tile tile, int color, int numberOfMoves) {
        super(tile, color, numberOfMoves);
        iconIndex = 2;
        value = 50;
    }

    /**
     * @param newTile the next tile
     * @return true if the move is valid
     */
    public boolean isValidMove(Tile newTile) {
        if (!super.isValidMove(newTile)) return false;
        //==================================
        int x1 = getX();
        int y1 = getY();
        int x2 = newTile.getxPos();
        int y2 = newTile.getyPos();
        int deltaX = x2 - x1;
        int deltaY = y1 - y2;
        //==================================
        /**
         * A rock can only move in one direction, either x or y.
         */
        if (deltaX != 0 && deltaY != 0) return false;
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
        return true;
    }
}
