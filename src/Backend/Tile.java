package Backend;

import Pieces.*;

public class Tile {

    private int xPos;
    private int yPos;
    private Piece piece;
    private Board boardReference;

    /**
     * Default constructor
     * @param x position on board
     * @param y position on board
     */
    public Tile(int x, int y) {
        xPos = x;
        yPos = y;
    }

    /**
     * Alternative constructor
     * @param x position on board
     * @param y position on board
     * @param piece piece to be assigned to the Tile
     */
    public Tile(int x, int y, Piece piece) {
        this(x, y);
        setPiece(piece);
    }

    public void setBoard(Board board) {
        this.boardReference = board;
    }

    public Board getBoard() {
        return boardReference;
    }

    /**
     * Copy constructor
     * @param tile the Tile to copy
     */
    public Tile(Tile tile) {
        this.xPos = tile.getxPos();
        this.yPos = tile.getyPos();
        Piece pieceRef = tile.getPiece();
        if (pieceRef != null) {
            if (pieceRef instanceof Pawn) {
                this.piece = new Pawn(this, pieceRef.getColor(), pieceRef.getNumberOfMoves());
            } else if (pieceRef instanceof Knight) {
                this.piece = new Knight(this, pieceRef.getColor(), pieceRef.getNumberOfMoves());
            } else if (pieceRef instanceof Bishop) {
                this.piece = new Bishop(this, pieceRef.getColor(), pieceRef.getNumberOfMoves());
            } else if (pieceRef instanceof Rook) {
                this.piece = new Rook(this, pieceRef.getColor(), pieceRef.getNumberOfMoves());
            } else if (pieceRef instanceof Queen) {
                this.piece = new Queen(this, pieceRef.getColor(), pieceRef.getNumberOfMoves());
            } else if (pieceRef instanceof King) {
                this.piece = new King(this, pieceRef.getColor(), pieceRef.getNumberOfMoves());
            }
        } else {
            setPiece(null);
        }
    }

    public boolean hasOppositeColor(int color) {
        return getPiece().getColor() != color;
    }

    public void killPiece() {
        setPiece(null);
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    /**
     * Sets the Piece object to be referenced with the Tile
     * @param newPiece
     */
    public void setPiece(Piece newPiece) {
        piece = newPiece;
    }

    /**
     * @return the Piece object associated with the Tile
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * @return true if the Tile has a Piece
     */
    public boolean hasPiece() {
        return piece != null;
    }
}
