package BackendImprovement;

public class Pawn {


    public static boolean isValidMove(int x1, int y1, int x2, int y2, byte piece, Board board) {

        int deltaX = x2 - x1;
        int deltaY;
        int direction;
        if (board.isBlack(piece)) {
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
            if (deltaX == 0 && board.hasPiece(x2, y2)) return false;
            //Checks if the move is a legal an passant, and if not checks if there is a piece
            //to capture on the destination tile
            if (Math.abs(deltaX) == 1 && !board.hasPiece(x2, y2)) return false;
            if (deltaX == 1) {
                if (!anPassant(x1, y1, direction, 1, piece, board) && !board.hasPiece(x1 + 1, y1 + direction)) return false;
            } else if (deltaX == -1) {
                if (!anPassant(x1, y1, direction, -1, piece, board) && !board.hasPiece(x1 -1, y1 + direction)) return false;
            }

        } else if (deltaY == 2) {
            if (board.getColor(piece) == Board.WHITE) {
                if (y1 != 1) return false;
            } else {
                if (y1 != 6) return false;
            }
            //The pawn can not attack anyone on its first step if it takes two steps forward
            if (board.pawnHasMoved(piece, x1)) return false;
            if (deltaX != 0) return false;
            //The pawn cant jump over other pieces on its first step
            if (board.hasPiece(x1, y1 + direction)) return false;
            if (board.hasPiece(x2, y2)) return false;
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
    public static boolean anPassant(int x1, int y1, int yDirection, int xDirection, byte piece, Board board) {
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
        if (!board.hasOppositeColor(x1 + xDirection, y1, piece)) return false;
        if (board.hasPiece(x1 + xDirection, y1 + yDirection)) return false;
        // Cannot do an passant on other pieces than pawns
        if (board.getPiece(x1 + xDirection, y1) != Board.PAWN) return false;
        // Cant do an passant if the opponent piece has moved more than once
        if (!board.isValidAnPassant(piece, x1 + xDirection)) return false;

        // Only capture pieces that has moved once two tiles forward
        if (y1 + yDirection != 2 && y1 + yDirection != 5) return false;
        return true;
    }
}
