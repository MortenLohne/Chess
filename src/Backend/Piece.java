package Backend;

public class Piece {

    //Frontend ===========================
    protected int iconIndex;
    public static final int BLACK = 0;
    public static final int WHITE = 1;
    //====================================

    //Backend ============================
    protected Tile tileReference;
    private int color;
    protected int numberOfMoves = 0;
    //====================================

    //Bot-stuff===========================
    protected int value = 0;
    //====================================

    /**
     * Default constructor
     * @param tile the tile of the piece (position on board)
     * @param color the color of the piece
     */
    public Piece(Tile tile, int color) {
        tileReference = tile;
        this.color = color;
    }

    /**
     * Used for copying the state of the board (and the pieces of course)
     * @param tile
     * @param color
     * @param numberOfMoves
     */
    public Piece(Tile tile, int color, int numberOfMoves) {
        this(tile, color);
        this.numberOfMoves = numberOfMoves;
    }

    public Tile getTile() {
        return tileReference;
    }

    /**
     * used (amongst other things) for implementing castling
     * @param nextTile
     */
    public void moveWithoutChecking(Tile nextTile) {
        Tile temp = tileReference;
        tileReference = nextTile;
        nextTile.setPiece(this);
        temp.setPiece(null);
        numberOfMoves++;
    }

    /**
     * Moves the piece to another Tile if it is a legal move
     * @param nextTile
     */
    public boolean setTile(Tile nextTile) {
        if (isValidMove(nextTile)) {
            Tile temp = tileReference;
            tileReference = nextTile;
            nextTile.setPiece(this);
            temp.setPiece(null);
            numberOfMoves++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true if the piece has moved
     */
    public boolean hasMoved() {
        return numberOfMoves > 0;
    }

    /**
     * @return true if the piece has moved once, used for "en passant"
     */
    public boolean hasMovedOnce() {
        return numberOfMoves == 1;
    }

    /**
     * @return the Tile-x
     */
    public int getX() {
        return tileReference.getxPos();
    }

    /**
     * @return the Tile-y
     */
    public int getY() {
        return tileReference.getyPos();
    }

    public boolean isValidMove(Tile newTile) {
        if (newTile.hasPiece() && !newTile.hasOppositeColor(color)) return false;
        if (this.tileReference == newTile) return false;

        if (Chess.checkCheck) {
            Chess.checkCheck = false;
            if (movePutsKingInCheck(newTile, color)) {
                //System.out.println("Cant move because the King is or will be checked!");
                Chess.checkCheck = true;
                return false;
            }
            Chess.checkCheck = true;
        }

        return true;
    }

    /**
     * @param newTile
     * @return true if the king is checked after the move
     */
    public boolean movePutsKingInCheck(Tile newTile, int color) {
        Board tempBoard = new Board(tileReference.getBoard());
        tempBoard.getPiece(getX(), getY()).moveWithoutChecking(tempBoard.getTile(newTile.getxPos(), newTile.getyPos()));
        //System.out.println(tempBoard.toString());
        boolean isChecked;
        if (color == BLACK) {
            isChecked = tempBoard.isChecked(WHITE);
        } else {
            isChecked = tempBoard.isChecked(BLACK);
        }
        return isChecked;
    }

    /** @return the color index for the chess-piece-icon array, frontend */
    public int getColor() {
        return color;
    }

    /** @return the icon index for the chess-piece-icon array, frontend */
    public int getIconIndex() {
        return iconIndex;
    }

    /**
     * @return the value of the piece
     */
    public int getValue() {
        return value;
    }

    /**
     * @return the number of moves the piece has made
     */
    public int getNumberOfMoves() {
        return numberOfMoves;
    }
}
