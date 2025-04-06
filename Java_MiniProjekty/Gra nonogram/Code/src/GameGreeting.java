import java.io.IOException;


public class GameGreeting {

    // ANSI color codes
    private static final String PURPLE_BACKGROUND = "\u001B[45m";
    private static final String RESET = "\u001B[0m";
    private static final String BLACK_TEXT = "\u001B[1;30m";


    public static void showGreetingAndStartGame() {
        printDecoratedWelcome();

        try {
            simulateLoading();
            startGame();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void simulateLoading() throws InterruptedException {
        System.out.print("Ładowanie");
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);
            System.out.print(".");
            System.out.flush();
        }
        System.out.println(" zakończone!");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void startGame() throws IOException, InterruptedException {
        Nonogram.startNonogramGame();
    }


    private static void printDecoratedWelcome() {
        int width = 100;

        printBorder(width);

        System.out.print(PURPLE_BACKGROUND+BLACK_TEXT);
        printCentered("Witaj w grze", width);
        printCentered("Nonogram !", width);
        System.out.print(RESET);

        printBottomBorder(width);
    }

    private static void printBorder(int width) {
        System.out.println(PURPLE_BACKGROUND+BLACK_TEXT +"╔" + "═".repeat(width - 2) + "╗"+ RESET);
    }

    private static void printCentered(String text, int width) {
        int padding = (width - text.length() - 2) / 2;
        String paddingStr = " ".repeat(padding);
        System.out.println(PURPLE_BACKGROUND+BLACK_TEXT +"║" + paddingStr + text + paddingStr + "║"+ RESET);
    }
    private static void printBottomBorder(int width) {
        System.out.println(PURPLE_BACKGROUND+BLACK_TEXT +"╚" + "═".repeat(width - 2) + "╝"+ RESET);
    }
}

