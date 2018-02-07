package Backend;

import Pieces.*;
import java.util.ArrayList;

public class Board {

    private Tile[][] tiles;

    /**
     * Default constructor
     * Initiates the tiles-array and fills it with pieces
     */
    public Board() {
        tiles = new Tile[8][8];
        setup();
        initPieces();
    }

    /**
     * Copy-constructor
     * @param board the board that is coppied
     */
    public Board(Board board) {
        tiles = new Tile[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                tiles[x][y] = new Tile(board.getTile(x, y));
                tiles[x][y].setBoard(this);
            }
        }
    }

    /**
     * Fills the board with the starting-pieces
     */
    private void initPieces() {
        //Pawns
        for (int x = 0; x < 8; x++) {
            getTile(x, 1).setPiece(new Pawn(getTile(x, 1), Piece.BLACK));
            getTile(x, 6).setPiece(new Pawn(getTile(x, 6), Piece.WHITE));
        }
        //Rooks
        getTile(0, 7).setPiece(new Rook(getTile(0, 7), Piece.WHITE));
        getTile(7, 7).setPiece(new Rook(getTile(7, 7), Piece.WHITE));
        getTile(0, 0).setPiece(new Rook(getTile(0, 0), Piece.BLACK));
        getTile(7, 0).setPiece(new Rook(getTile(7, 0), Piece.BLACK));
        //Bishops
        getTile(2, 7).setPiece(new Bishop(getTile(2, 7), Piece.WHITE));
        getTile(5, 7).setPiece(new Bishop(getTile(5, 7), Piece.WHITE));
        getTile(2, 0).setPiece(new Bishop(getTile(2, 0), Piece.BLACK));
        getTile(5, 0).setPiece(new Bishop(getTile(5, 0), Piece.BLACK));
        //Kings
        getTile(4, 7).setPiece(new King(getTile(4, 7), Piece.WHITE));
        getTile(4, 0).setPiece(new King(getTile(4, 0), Piece.BLACK));
        //Knight
        getTile(1, 7).setPiece(new Knight(getTile(1, 7), Piece.WHITE));
        getTile(6, 7).setPiece(new Knight(getTile(6, 7), Piece.WHITE));
        getTile(1, 0).setPiece(new Knight(getTile(1, 0), Piece.BLACK));
        getTile(6, 0).setPiece(new Knight(getTile(6, 0), Piece.BLACK));
        //Queens
        getTile(3, 7).setPiece(new Queen(getTile(3, 7), Piece.WHITE));
        getTile(3, 0).setPiece(new Queen(getTile(3, 0), Piece.BLACK));
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                getTile(x, y).setBoard(this);
            }
        }
    }

    /**
     * Fills the board with empty tiles
     */
    private void setup() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                tiles[x][y] = new Tile(x, y);
            }
        }
    }

    /**
     *
     * @param color
     * @return checks if the color specified checks the opponent
     */
    public boolean isChecked(int color) {
        ArrayList<Move> moves = getAllMoves(color);
        for (Move m : moves) {
            if (m.moveKillsAKing()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param color
     * @return an array of all the moves for all the pieces with a specified color
     */
    public ArrayList<Move> getAllMoves(int color) {
        ArrayList<Piece> allPieces = getAllPieces(color);
        ArrayList<Move> allMoves = new ArrayList<>();
        for (Piece p : allPieces) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    if (p.isValidMove(getTile(x, y))) {
                        allMoves.add(new Move(p.getTile(), getTile(x, y)));
                    }
                }
            }
        }
        return allMoves;
    }

    /**
     * Gets all the pieces in on color from the board object
     * @param color the color of the pieces
     * @return an array of all the pieces with color=color
     */
    public ArrayList<Piece> getAllPieces(int color) {
        ArrayList<Piece> allPieces = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (getTile(x, y).hasPiece() && getPiece(x, y).getColor() == color) {
                    allPieces.add(getPiece(x, y));
                }
            }
        }
        return allPieces;
    }

    /**
     * @param x position on board
     * @param y position on board
     * @return a Piece object at (x, y)
     */
    public Piece getPiece(int x, int y) {
        return getTile(x, y).getPiece();
    }

    /**
     * Check if there is a piece on the coordinate before calling this method
     * @param x index in array
     * @param y index in array
     * @return a Tiles from the tiles-array
     */
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    /**
     * Evaluates the strength of the board depending on the color
     * @return
     */
    public int evaluateBoard(int color) {
        int sum = 0;
        if (color == Piece.WHITE) {
            sum += getValueOfPieces(Piece.WHITE) - getValueOfPieces(Piece.BLACK);
        } else {
            sum += getValueOfPieces(Piece.BLACK) - getValueOfPieces(Piece.WHITE);
        }
        return sum;
    }

    /**
     * Gets the value of one "side" of the board
     * @param color
     * @return
     */
    private int getValueOfPieces(int color) {
        int sum = 0;
        ArrayList<Piece> pieces = getAllPieces(color);
        for (Piece p : pieces) sum += p.getValue();
        return sum;
    }

    /**
     * Generates a String for viewing the board in the commandline
     * @return
     */
    public String toString() {
        String ret = "";
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (!getTile(x, y).hasPiece()) {
                    ret += " |";
                } else {
                    ret += "P|";
                }
            }
            ret += "\n-------------------\n";
        }
        return ret;
    }

    public void doMove(Move m) {
        getPiece(m.getox(), m.getoy()).setTile(getTile(m.getnx(), m.getny()));
    }
}
