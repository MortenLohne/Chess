package BackendImprovement;

public class King {
    public boolean isValidMove(int x1, int y1, int x2, int y2, Board board) {
        int deltaX = x2 - x1;
        int deltaY = y1 - y2;
        //================================
        boolean castlingIsValid = castling(y1, y2, deltaX, board);
        if ((Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1) && !castlingIsValid) return false;
        return true;
    }

    /**
     * Checks if castling is valid
     * @param newTile
     * @param y1
     * @param deltaX
     * @return true if the move is a valid castling
     */
    public boolean castling(int y1, int y2, int deltaX, Board board) {
        if (this.hasMoved()) return false;
        if (Math.abs(deltaX) != 2) return false;
        if (y2 != y1) return false;
        if (deltaX > 0 && !board.hasPiece(7, y1)) return false;
        if (deltaX < 0 && !board.hasPiece(0, y1)) return false;
        if (deltaX > 0 && !board.hasPiece(7, y1)) {
            if (tileReference.getBoard().getTile(7, y1).getPiece().hasMoved()) return false;
            if (board.hasPiece(6, y1) ||
                    board.hasPiece(5, y1)) return false;
        } else if (!board.hasPiece(0, y1)){
            if (tileReference.getBoard().getTile(0, y1).getPiece().hasMoved()) return false;
            if (board.hasPiece(1, y1) ||
                    board.hasPiece(2, y1) ||
                    board.hasPiece(3, y1)) return false;
        }
        return true;
    }
}
