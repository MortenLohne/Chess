package BackendImprovement;

public class Knight {

    public static boolean isValidMove(int x1, int y1, int x2, int y2) {
        if (Math.abs(x1 - x2) == 2 && Math.abs(y1 - y2) == 1) return true;
        if (Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 2) return true;
        return false;
    }


}
