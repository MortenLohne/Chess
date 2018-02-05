package Backend;

import Pieces.King;

public class Move {

    private Tile originalTile;
    private Tile destinationTile;

    /**
     * Default constructor
     * @param originalTile
     * @param destinationTile
     */
    public Move(Tile originalTile, Tile destinationTile) {
        this.originalTile = originalTile;
        this.destinationTile = destinationTile;
    }

    /**
     * Changes the Tile of the Piece that is being moved
     */
    public void doMove() {
        if (originalTile.hasPiece()) {
            originalTile.getPiece().setTile(destinationTile);
        }
    }

    /**
     * @return true if the destinationTile is a king
     */
    public boolean moveKillsAKing() {
        return destinationTile.getPiece() instanceof King;
    }

    public String toString() {
        return String.format("ox: %d oy: %d nx: %d ny %d",
                originalTile.getxPos(), originalTile.getyPos(), destinationTile.getxPos(), destinationTile.getyPos());
    }

    public int getox() {
        return originalTile.getxPos();
    }

    public int getoy() {
        return originalTile.getyPos();
    }

    public int getnx() {
        return destinationTile.getxPos();
    }

    public int getny() {
        return destinationTile.getyPos();
    }
}
