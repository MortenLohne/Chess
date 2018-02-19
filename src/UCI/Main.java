package UCI;

import java.util.Scanner;

public class Main {

    // TODO: Finish UCI protocol -- http://wbec-ridderkerk.nl/html/UCIProtocol.html

    public static final String ENGINE_NAME = "ENGINE_NAME 1.0";
    public static boolean ucimode = false;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String command = in.nextLine();

        while (!command.equals("quit")) {
            switch (command) {

                case "uci" : command_uci(); break;

                default: System.out.println("invalid command"); break;

            }
            command = in.nextLine();
        }

        System.out.println("quitting...");
    }

    private static void command_uci() {
        ucimode = true;
        System.out.println("id name " + ENGINE_NAME);
        System.out.println("id author Petter Daae");
        System.out.println("uciok");
    }

}
