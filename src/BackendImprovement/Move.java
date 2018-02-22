package BackendImprovement;

public class Move {

    public int x1;
    public int y1;
    public int x2;
    public int y2;

    public Move(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public String toString() {
        String r = "" + intToChar(x1) + (y1+1) + intToChar(x2) + (y2+1);
        return r;
    }

    private String intToChar(int i) {
        switch (i) {
            case 0 : return "a";
            case 1 : return "b";
            case 2 : return "c";
            case 3 : return "d";
            case 4 : return "e";
            case 5 : return "f";
            case 6 : return "g";
            case 7 : return "h";
            default:break;
        }
        return "å";
    }
}
