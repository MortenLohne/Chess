package Pieces;

import Backend.Piece;
import Backend.Tile;

public class Pawn extends Piece {

    /**
     * Default-constructor
     * @param tile
     * @param color
     */
    public Pawn(Tile tile, int color) {
        super(tile, color);
        this.iconIndex = 5;
        value = 10;
    }

    /**
     * Used for copying the state of the piece
     * @param tile
     * @param color
     * @param numberOfMoves
     */
    public Pawn(Tile tile, int color, int numberOfMoves) {
        super(tile, color, numberOfMoves);
        this.iconIndex = 5;
        value = 10;
    }

    /**
     * The pawn needs a unique setTile-method because of the "an passant"-move
     * @param newTile the destination-tile
     * @return true if the move was legal and if the tile was moved
     */
    public boolean setTile(Tile newTile) {
        if (isValidMove(newTile)) {
            int deltaX = newTile.getxPos() - getX();
            int yDirection;
            if (getColor() == Piece.WHITE) {
                yDirection = -1;
            } else {
                yDirection = 1;
            }
            if (anPassant(getX(), getY(), yDirection, deltaX)) {
                tileReference.getBoard().getTile(newTile.getxPos(), getY()).killPiece();
            }
            Tile temp = tileReference;
            tileReference = newTile;
            newTile.setPiece(this);
            temp.setPiece(null);
            numberOfMoves++;
            //Queen stuff
            if (newTile.getyPos() == 7 || newTile.getyPos() == 0) {
                newTile.setPiece(new Queen(newTile, getColor()));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param newTile the next Tile
     * @return true if the next Tile is a valid destination
     */
    @Override
    public boolean isValidMove(Tile newTile) {
        if (!super.isValidMove(newTile)) return false;
        //==========================================
        int x1 = getX();
        int y1 = getY();
        int x2 = newTile.getxPos();
        int y2 = newTile.getyPos();
        int deltaX = x2 - x1;
        int deltaY;
        int direction;
        if (getColor() == Piece.WHITE) {
            deltaY = y1 - y2;
            direction = -1;
        } else {
            deltaY = y2 - y1;
            direction = 1;
        }
        //System.out.println("DeltaY = " + deltaY + " DeltaX = " + deltaX);
        //The pawn can never move directly sideways or back
        if (deltaY <= 0) return false;
        //The pawn can never move more than one in xDirection
        if (Math.abs(deltaX) > 1) return false;
        //So that the pawn can never move further than 2 in y-direction
        if (deltaY > 2) return false;
        if (deltaY == 1) {
            //The pawn cant 'walk' into other pieces
            if (deltaX == 0 && tileReference.getBoard().getTile(x2, y2).hasPiece()) return false;
            //Checks if the move is a legal an passant, and if not checks if there is a piece
            //to capture on the destination tile
            if (deltaX == 1) {
                if (!anPassant(x1, y1, direction, 1) && !tileReference.getBoard().getTile(x1 + 1, y1 + direction).hasPiece()) return false;
            } else if (deltaX == -1) {
                if (!anPassant(x1, y1, direction, -1) && !tileReference.getBoard().getTile(x1 - 1, y1 + direction).hasPiece()) return false;
            }
        } else if (deltaY == 2) {
            //The pawn can not attack anyone on its first step if it takes two steps forward
            if (this.hasMoved()) return false;
            if (deltaX != 0) return false;
            //The pawn cant jump over other pieces on its first step
            if (tileReference.getBoard().getTile(x1, y1 + direction).hasPiece()) return false;
        }
        return true;
    }

    /**
     * Used for checking if the move is an 'an passant' in the validation and move methods
     * @param x1
     * @param y1
     * @param yDirection
     * @param xDirection
     * @return
     */
    private boolean anPassant(int x1, int y1, int yDirection, int xDirection) {
        //System.out.println(System.currentTimeMillis() + " X1 = " + x1 + " Y1 = " + y1 + " yDirection = " + yDirection + " xDirection = " + xDirection);
        //Some simple checks for the setPiece method:
        if (Math.abs(xDirection) != 1 || Math.abs(yDirection) != 1) return false;
        /*
         * We know from before:
         * - deltaX = 1
         * - deltaY = 1
         */
        //Out of bounds
        if (xDirection == 1 && x1 == 7) return false;
        if (xDirection == -1 && x1 == 0) return false;
        //Checks that the eventual target doesn't have the same color
        try {
            if (!tileReference.getBoard().getTile(x1 + xDirection, y1).hasOppositeColor(getColor())) return false;
        } catch (NullPointerException e) {}
        // Cant take two pieces in one move
        if (tileReference.getBoard().getTile(x1 + xDirection, y1 + yDirection).hasPiece()) return false;
        // Cannot do an passant on other pieces than pawns
        if (!(tileReference.getBoard().getTile(x1 + xDirection, y1).getPiece() instanceof Pawn)) return false;
        // Cant do an passant if the opponent piece has moved more than once
        if (!tileReference.getBoard().getTile(x1 + xDirection, y1).getPiece().hasMovedOnce()) return false;
        // Only capture pieces that has moved once two tiles forward
        if (y1 + yDirection != 2 && y1 + yDirection != 5) return false;
        return true;
    }
}
