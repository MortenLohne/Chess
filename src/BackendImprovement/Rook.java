package BackendImprovement;

public class Rook {
    public static boolean isValidMove(int x1, int y1, int x2, int y2, Board board) {
        int deltaX = x2 - x1;
        int deltaY = y1 - y2;
        //==================================
        /**
         * A rock can only move in one direction, either x or y.
         */
        if (deltaX != 0 && deltaY != 0) return false;
        /**
         * East.
         */
        if (deltaX > 0) {
            for (int i = x1 + 1; i < x2; i++) {
                boolean hasAPiece = board.hasPiece(i, y1);
                if (hasAPiece) return false;
            }
        }
        /**
         * West.
         */
        if (deltaX < 0) {
            for (int i = x2 + 1; i < x1; i++) {
                boolean hasAPiece = board.hasPiece(i, y1);;
                if (hasAPiece) return false;
            }
        }
        /**
         * North.
         */
        if (deltaY > 0) {
            for (int i = y1 - 1; i > y2; i--) {
                boolean hasAPiece = board.hasPiece(x1, i);
                if (hasAPiece) return false;
            }
        }
        /**
         * South.
         */
        if (deltaY < 0) {
            for (int i = y1 + 1; i < y2; i++) {
                boolean hasAPiece = board.hasPiece(x1, i);
                if (hasAPiece) return false;
            }
        }
        return true;
    }
}
