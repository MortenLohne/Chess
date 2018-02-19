package BackendImprovement;


public class Board {

    public static final int WHITE = 0;
    public static final int BLACK = 1;

    public static final int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7;
    public static final byte NO_PIECE = 0;
    public static final byte PAWN = 1, KNIGHT = 2, BISHOP = 3, ROOK = 4, QUENN = 5, KING = 6;
    public static final byte BPAWN = -1, BKNIGHT = -2, BBISHOP = -3, BROOK = -4, BQUENN = -5, BKING = -6;

    /**
     *      0: no piece
     *      1: pawn
     *      2: knight
     *      3: bishop
     *      4: rook
     *      5: queen
     *      6 king
     *
     *      white pieces: positive values
     *      black pieces: negative values
     */
    private byte[][] pieces = new byte[8][8];
    private boolean[][] anPassant = new boolean[2][8];
    private boolean[][] castling = new boolean[2][2];



    public boolean isLegalMove(int x1, int y1, int x2, int y2) {
        byte piece = (byte) Math.abs(getPiece(x1, y1));
        switch (piece) {
            case PAWN : if(Pawn.isValidMove(x1, y1, x2, y2, getPiece(x1, y1), this)) return true; break;
            case KNIGHT : break;
            case BISHOP : break;
            case ROOK : break;
            case QUENN : break;
            case KING : break;
            default: break;
        }
        return false;
    }

    public boolean pawnHasMoved(byte piece, int x1) {
        return !anPassant[getColor(piece)][x1];
    }

    public boolean isValidAnPassant(byte piece, int x2) {
        return anPassant[getOppositeColor(piece)][x2];
    }

    public int getOppositeColor(byte piece) {
        if (isBlack(piece)) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    /**
     * @param x2
     * @param y2
     * @param piece
     * @return
     */
    public boolean hasOppositeColor(int x2, int y2, byte piece) {
        return getPiece(x2, y2) - piece != 0;
    }

    /**
     * Initiates the starting position
     */
    public void initStartPosition() {
        setPiece(a, 1, ROOK);
        setPiece(b, 1, KNIGHT);
        setPiece(c, 1, BISHOP);
        setPiece(d, 1, QUENN);
        setPiece(e, 1, KING);
        setPiece(f, 1, BISHOP);
        setPiece(g, 1, KNIGHT);
        setPiece(h, 1, ROOK);

        setPiece(a, 1, BROOK);
        setPiece(b, 1, BKNIGHT);
        setPiece(c, 1, BBISHOP);
        setPiece(d, 1, BQUENN);
        setPiece(e, 1, BKING);
        setPiece(f, 1, BBISHOP);
        setPiece(g, 1, BKNIGHT);
        setPiece(h, 1, BROOK);

        setPiece(a, 2, PAWN);
        setPiece(b, 2, PAWN);
        setPiece(c, 2, PAWN);
        setPiece(d, 2, PAWN);
        setPiece(e, 2, PAWN);
        setPiece(f, 2, PAWN);
        setPiece(g, 2, PAWN);
        setPiece(h, 2, PAWN);

        setPiece(a, 2, BPAWN);
        setPiece(b, 2, BPAWN);
        setPiece(c, 2, BPAWN);
        setPiece(d, 2, BPAWN);
        setPiece(e, 2, BPAWN);
        setPiece(f, 2, BPAWN);
        setPiece(g, 2, BPAWN);
        setPiece(h, 2, BPAWN);

        for (int i = 0; i < anPassant.length; i++) {
            for (int j = 0; j < anPassant[i].length; j++) {
                anPassant[i][j] = true;
            }
        }

        for (int i = 0; i < castling.length; i++) {
            for (int j = 0; j < castling[i].length; j++) {
                castling[i][j] = true;
            }
        }
    }

    /**
     * Performs a move
     * Updates anPassant array
     * @param x1 "a letter"
     * @param y1 "a number"
     * @param x2 "a letter"
     * @param y2 "a number"
     */
    public void doMove(int x1, int y1, int x2, int y2) {
        byte piece = getPiece(x1, y1);
        setPiece(x2, y2, piece);
        removePiece(x1, y1);

        //An passant stuff
        if (Math.abs(piece) == PAWN) {
            int deltaY = Math.abs(y1 - y2);
            if (deltaY == 1) {
                if (isBlack(piece)) anPassant[BLACK][x1] = false;
                else anPassant[WHITE][x1] = false;
            }
        }

        //Castling stuff
        if (Math.abs(piece) == ROOK) {
            int index;
            if (x1 == a) index = 0;
            else index = 1;
            castling[getColor(piece)][index] = false;
        }
    }

    /**
     * @param piece
     * @return the color of the piece
     */
    public int getColor(byte piece) {
        if (isBlack(piece)) return 0;
        else return 1;
    }

    /**
     * @param x "a letter"
     * @param y "a number"
     * @return true if there is a piece at x, y
     */
    public boolean hasPiece(int x, int y) {
        return getPiece(x, y) != 0;
    }

    /**
     * @param piece
     * @return true if the piece is black
     */
    public boolean isBlack(int piece) {
        return piece < 0;
    }

    /**
     *
     * @param x1 "a letter"
     * @param y1 "a number"
     * @return the byte at x1, y1 in pieces
     */
    public byte getPiece(int x1, int y1) {
        return pieces[x1][y1];
    }

    /**
     * @param xPos "a letter"
     * @param yPos "a number"
     * @param piece "the new piece
     */
    public void setPiece(int xPos, int yPos, byte piece) {
        yPos--;
        pieces[xPos][yPos] = piece;
    }

    /**
     * Sets the piece value of a tile to 0
     * @param xPos "a letter"
     * @param yPos "a number"
     */
    public void removePiece(int xPos, int yPos) {
        setPiece(xPos, yPos, NO_PIECE);
    }
}
