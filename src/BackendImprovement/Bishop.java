package BackendImprovement;

public class Bishop {
    public static boolean isValidMove(int x1, int y1, int x2, int y2, Board board) {
        //==============================
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        //==============================
        /*
         * The sprinter can only move diagonally.
         */
        if (Math.abs(deltaX) != Math.abs(deltaY)) return false;
        if (deltaX > 0 && deltaY > 0) {
            for (int i = x1 + 1, j = y1 + 1; i < x2; i++, j++) {
                if (board.hasPiece(i, j)) return false;
            }
        }
        if (deltaX < 0 && deltaY > 0) {
            for (int i = x1 - 1, j = y1 + 1; j < y2; i--, j++) {
                if (board.hasPiece(i, j)) return false;
            }
        }
        if (deltaX < 0 && deltaY < 0) {
            for (int i = x1 - 1, j = y1 - 1; i > x2; i--, j--) {
                if (board.hasPiece(i, j)) return false;
            }
        }
        if (deltaX > 0 && deltaY < 0) {
            for (int i = x1 + 1, j = y1 - 1; i < x2; i++, j--) {
                if (board.hasPiece(i, j)) return false;
            }
        }
        return true;
    }
}
