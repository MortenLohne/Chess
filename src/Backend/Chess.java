package Backend;

public class Chess {

    public static boolean checkCheck = true;
    public static Board mainBoard;

    /**
     * Default constructor
     */
    public Chess() {
        setup();
    }

    /**
     * Creates a new Board-object
     */
    private void setup() {
        mainBoard = new Board();
    }

    /**
     * @param x position on board
     * @param y position on board
     * @return a Tile object from the tiles-array in the mainBoard object
     */
    public Tile getTile(int x, int y) {
        return mainBoard.getTile(x, y);
    }

    /**
     *
     * @param x position on board
     * @param y position on board
     * @return a Piece object associated with the Tile object at (x, y)
     */
    public Piece getPiece(int x, int y) {
        return getTile(x, y).getPiece();
    }

    /**
     * Executes a move
     * @param originalTile
     * @param nextTile
     */
    public void move(Tile originalTile, Tile nextTile) {
        Move move = new Move(originalTile, nextTile);
        move.doMove();
    }
}
