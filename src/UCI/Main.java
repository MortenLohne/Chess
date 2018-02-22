package UCI;

import BackendImprovement.Board;
import BackendImprovement.Move;
import Bot.Bot05;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static final String ENGINE_NAME = "ENGINE_NAME 1.0";
    public static boolean ucimode = false;

    private boolean blackToMove = false;

    private Bot05 bot05;

    public static void main(String[] args) {

        new Main();



    }

    public Main() {
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();

        while (!command.equals("quit")) {
            switch (command) {

                case "uci" : command_uci(); break;
                case "isready" : command_isready(); break;
                case "ucinewgame" : command_ucinewgame(); break;
                case "go" : new Thread(() -> command_go()).start(); break;
                case "go infinite" : new Thread(() -> command_go_infinite()).start(); break;
                case "position startpos":  command_position_startpos(); break;
                case "stop" : command_stop(); break;

                default: //System.out.println("invalid command"); break;

            }


            if (command.length() > 22 && command.substring(0, 23).equals("position startpos moves")) {
                command_position_startpos_moves(command);
            }

            command = in.nextLine();
        }

        System.out.println("quitting...");
    }

    private void command_position_startpos_moves(String command) {
        bot05.setStartpos();
        blackToMove = false;
        ArrayList<String> stringMoves = new ArrayList<>();
        for (int i = 24; i <= command.length() - 4; i+=5) {
            stringMoves.add(command.substring(i, i + 4));
        }
        ArrayList<Move> moves = new ArrayList<>();
        for (String s : stringMoves) {
            moves.add(stringToMove(s));
            blackToMove = !blackToMove;
        }

        bot05.execMoves(moves);
    }

    private void command_position_startpos() {
        bot05.setStartpos();
    }

    private void command_ucinewgame() {
        bot05 = new Bot05();
    }

    private void command_stop() {
        Bot05.shouldStop = true;
    }

    private void command_go() {
        if (blackToMove) {
            Bot05.makeMove(bot05.botBoard, Board.BLACK);
        } else {
            Bot05.makeMove(bot05.botBoard, Board.WHITE);
        }
    }

    private void command_go_infinite() {
        if (blackToMove) {
            Bot05.makeMove(bot05.botBoard, Board.BLACK, 100);
        } else {
            Bot05.makeMove(bot05.botBoard, Board.WHITE, 100);
        }
    }

    private void command_isready() {
        bot05 = new Bot05();
        System.out.println("readyok");
    }

    private void command_uci() {
        ucimode = true;
        System.out.println("id name " + ENGINE_NAME);
        System.out.println("id author Petter Daae");
        System.out.println("uciok");
    }


    private Move stringToMove(String stringMove) {
        int x1, x2, y1, y2;
        char sx1 = stringMove.charAt(0);
        char sy1 = stringMove.charAt(1);
        char sx2 = stringMove.charAt(2);
        char sy2 = stringMove.charAt(3);
        x1 = letterToCoordinate(sx1);
        x2 = letterToCoordinate(sx2);
        y1 = Integer.parseInt(sy1 + "") - 1;
        y2 = Integer.parseInt(sy2 + "") - 1;
        Move m = new Move(x1, y1, x2, y2);
        return m;
    }


    private static int letterToCoordinate(char c) {
        switch (c) {
            case 'a' : return 0;
            case 'b' : return 1;
            case 'c' : return 2;
            case 'd' : return 3;
            case 'e' : return 4;
            case 'f' : return 5;
            case 'g' : return 6;
            case 'h' : return 7;
            default: break;
        }
        return -1;
    }

}
