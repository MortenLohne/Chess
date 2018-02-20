package BackendImprovement;

public class Board {

    public static final int WHITE = 0;
    public static final int BLACK = 1;

    public static final int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7;
    public static final byte NO_PIECE = 0;
    public static final byte PAWN = 6, KNIGHT = 5, BISHOP = 4, ROOK = 3, QUENN = 2, KING = 1;
    public static final byte BPAWN = -6, BKNIGHT = -5, BBISHOP = -4, BROOK = -3, BQUENN = -2, BKING = -1;

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
    public boolean[][] castling = new boolean[2][2];
    public boolean[] kingHasMoved = new boolean[2];

    public Board() {
        initStartPosition();
    }

    private boolean standardMoveCheck(int x1, int y1, int x2, int y2) {
        if (hasPiece(x2, y2) && !hasOppositeColor(x2, y2, getPiece(x1, y1))) {
            System.out.println("rfrbrb");
            return false;
        }
        if (x1 == x2 && y1 == y2) return false;
        return true;
    }

    public boolean isLegalMove(int x1, int y1, int x2, int y2) {
        byte piece = (byte) Math.abs(getPiece(x1, y1));
        if (!standardMoveCheck(x1, y1, x2, y2)) return false;
        switch (piece) {
            case PAWN : if(!Pawn.isValidMove(x1, y1, x2, y2, getPiece(x1, y1), this)) return false; break;
            case KNIGHT : if(!Knight.isValidMove(x1, x2, y1, y2)) return false; break;
            case BISHOP : if(!Bishop.isValidMove(x1, y1, x2, y2, this)) return false; break;
            case ROOK : if(!Rook.isValidMove(x1, y1, x2, y2, this)) return false; break;
            case QUENN : if(!Queen.isValidMove(x1, y1, x2, y2, this)) return false; break;
            case KING : if(!King.isValidMove(x1, y1, x2, y2, this)) return false; break;
            default: break;
        }
        return true;
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
        setPiece(a, 0, ROOK);
        setPiece(b, 0, KNIGHT);
        setPiece(c, 0, BISHOP);
        setPiece(d, 0, KING);
        setPiece(e, 0, QUENN);
        setPiece(f, 0, BISHOP);
        setPiece(g, 0, KNIGHT);
        setPiece(h, 0, ROOK);

        setPiece(a, 7, BROOK);
        setPiece(b, 7, BKNIGHT);
        setPiece(c, 7, BBISHOP);
        setPiece(d, 7, BKING);
        setPiece(e, 7, BQUENN);
        setPiece(f, 7, BBISHOP);
        setPiece(g, 7, BKNIGHT);
        setPiece(h, 7, BROOK);

        setPiece(a, 1, PAWN);
        setPiece(b, 1, PAWN);
        setPiece(c, 1, PAWN);
        setPiece(d, 1, PAWN);
        setPiece(e, 1, PAWN);
        setPiece(f, 1, PAWN);
        setPiece(g, 1, PAWN);
        setPiece(h, 1, PAWN);

        setPiece(a, 6, BPAWN);
        setPiece(b, 6, BPAWN);
        setPiece(c, 6, BPAWN);
        setPiece(d, 6, BPAWN);
        setPiece(e, 6, BPAWN);
        setPiece(f, 6, BPAWN);
        setPiece(g, 6, BPAWN);
        setPiece(h, 6, BPAWN);

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
    public boolean doMove(int x1, int y1, int x2, int y2) {

        if (!isLegalMove(x1, y1, x2, y2)) return false;

        byte piece = getPiece(x1, y1);
        setPiece(x2, y2, piece);
        removePiece(x1, y1);

        //An passant stuff
        if (Math.abs(piece) == PAWN) {
            int deltaX = x2 - x1;
            int yDirection;
            if (isBlack(piece)) {
                yDirection = 1;
            } else {
                yDirection = -1;
            }
            if (Pawn.anPassant(x1, y1, yDirection, deltaX, piece, this)) {
                removePiece(x2, y1);
            }
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
        if (Math.abs(piece) == KING) {
            kingHasMoved[getColor(piece)] = true;
            int deltaX = x2 - x1;
            if (King.castling(y1, y2, deltaX, piece, this)) {
                if (deltaX > 0) {
                    this.doMove(7, y1, 5, y1);
                } else {
                    this.doMove(0, y1, 3, y1);
                }
            }
        }

        return true;
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
