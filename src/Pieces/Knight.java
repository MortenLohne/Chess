package Pieces;

import Backend.Piece;
import Backend.Tile;

public class Knight extends Piece {

    public Knight(Tile tile, int color) {
        super(tile, color);
        iconIndex = 3;
        value = 30;
    }

    public Knight(Tile tile, int color, int numberOfMoves) {
        super(tile, color, numberOfMoves);
        iconIndex = 3;
        value = 30;
    }

    public boolean isValidMove(Tile newTile) {
        if (!super.isValidMove(newTile)) return false;
        if (Math.abs(getX() - newTile.getxPos()) == 2 && Math.abs(getY() - newTile.getyPos()) == 1) return true;
        if (Math.abs(getX() - newTile.getxPos()) == 1 && Math.abs(getY() - newTile.getyPos()) == 2) return true;
        return false;
    }
}
