package Pieces;

import Backend.Piece;
import Backend.Tile;

public class King extends Piece {

    public King(Tile tile, int color) {
        super(tile, color);
        iconIndex = 0;
        value = 900;
    }

    public King(Tile tile, int color, int numberOfMoves) {
        super(tile, color, numberOfMoves);
        iconIndex = 0;
        value = 900;
    }

    /**
     * This piece needs its own setTile-method because of castling
     * @param newTile
     * @return
     */
    public boolean setTile(Tile newTile) {
        if (isValidMove(newTile)) {
            int deltaX = newTile.getxPos() - getX();
            int y1 = getY();
            if (castling(newTile, y1, deltaX)) {
                if (deltaX > 0 && tileReference.getBoard().getTile(7, y1).hasPiece()) {
                    tileReference.getBoard().getTile(7, y1).getPiece().moveWithoutChecking(tileReference.getBoard().getTile(5, y1));
                } else if (tileReference.getBoard().getTile(0, y1).hasPiece()) {
                    tileReference.getBoard().getTile(0, y1).getPiece().moveWithoutChecking(tileReference.getBoard().getTile(3, y1));
                }
            }
            Tile temp = tileReference;
            tileReference = newTile;
            newTile.setPiece(this);
            temp.setPiece(null);
            numberOfMoves++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param newTile
     * @return true if the move is valid
     */
    public boolean isValidMove(Tile newTile) {
        if (!super.isValidMove(newTile)) return false;
        //================================
        int x1 = getX();
        int y1 = getY();
        int x2 = newTile.getxPos();
        int y2 = newTile.getyPos();
        int deltaX = x2 - x1;
        int deltaY = y1 - y2;
        //================================
        boolean castlingIsValid = castling(newTile, y1, deltaX);
        if ((Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1) && !castlingIsValid) return false;
        return true;
    }

    /**
     * Checks if castling is valid
     * @param newTile
     * @param y1
     * @param deltaX
     * @return true if the move is a valid castling
     */
    public boolean castling(Tile newTile, int y1, int deltaX) {
        if (this.hasMoved()) return false;
        if (Math.abs(deltaX) != 2) return false;
        if (newTile.getyPos() != y1) return false;
        if (deltaX > 0 && !tileReference.getBoard().getTile(7, y1).hasPiece()) return false;
        if (deltaX < 0 && !tileReference.getBoard().getTile(0, y1).hasPiece()) return false;
        if (deltaX > 0 && tileReference.getBoard().getTile(7, y1).hasPiece()) {
            if (tileReference.getBoard().getTile(7, y1).getPiece().hasMoved()) return false;
            if (tileReference.getBoard().getTile(6, y1).hasPiece() ||
                    tileReference.getBoard().getTile(5, y1).hasPiece()) return false;
        } else if (tileReference.getBoard().getTile(0, y1).hasPiece()){
            if (tileReference.getBoard().getTile(0, y1).getPiece().hasMoved()) return false;
            if (tileReference.getBoard().getTile(1, y1).hasPiece() ||
                    tileReference.getBoard().getTile(2, y1).hasPiece() ||
                    tileReference.getBoard().getTile(3, y1).hasPiece()) return false;
        }
        return true;
    }
}
