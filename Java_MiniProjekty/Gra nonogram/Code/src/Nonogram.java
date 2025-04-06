
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Nonogram {

    private static boolean isSolver;

    public static void main(String[] args) {
        GameGreeting.showGreetingAndStartGame();
    }

    public static void startNonogramGame() throws IOException, InterruptedException {
        clearScreen();
        isSolver = false;
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> rowToPrint = new ArrayList<>();
        ArrayList<String> colToPrint = new ArrayList<>();
        ArrayList<String> columnsToCheck = new ArrayList<>();
        int rowPos = 0;
        int colPos = 0;
        int colLength = 0;
        int rowLength = 0;
        int gameMode = chooseGameMode();
        if (gameMode == 1) {
            //System.out.println("podaj nazwę pliku (plik musi się znajdować w folderze Resources");
            //String filename = scanner.nextLine();
            loadedGame(0, rowLength, colLength, rowPos, colPos, rowToPrint, colToPrint, columnsToCheck);
            finishGame(false);
        } else {
            generatedGame(rowLength, colLength, rowPos, colPos, rowToPrint, colToPrint, columnsToCheck);
            finishGame(true);
        }
    }

    private static int chooseGameMode() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int gameMode = 0;
        boolean wasChecked = false;
        do {
            if(wasChecked){
                clearScreen();
                System.out.println();
                System.out.println("Ups! Coś poszło nie tak. Wprowadź proszę numer trybu gry (1 lub 2).");
            }
            wasChecked=true;
            System.out.println();
            System.out.println("Wybierz tryb gry:");
            System.out.println("1 - wczytaj planszę z pliku");
            System.out.println("2 - wygeneruj planszę");
            System.out.print("Wybierz tryb: ");
            try {
                gameMode = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
                continue;
            }
        } while (gameMode != 1 && gameMode != 2);
        clearScreen();
        return gameMode;

    }

    public static void loadedGame(int SIZE, int rowLength, int colLength, int rowPos, int colPos, ArrayList<String> rowToPrint, ArrayList<String> colToPrint, ArrayList<String> columnsToCheck) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int[][] solution = new int[SIZE][SIZE];
        int[][] board = new int[SIZE][SIZE];
        ArrayList<int[][]> solvedSolution;
        UploadedTable nonogram = new UploadedTable(SIZE, rowLength, colLength, rowPos, colPos, solution, board, rowToPrint, colToPrint, columnsToCheck);
        nonogram.readBoardFromFile();
        NonogramPlayer.setIsWin(true);
        UploadedTable.createSolution();
        solvedSolution = UploadedTable.getAllSolutions();
        if (solvedSolution.isEmpty()) {
            clearScreen();
            System.out.println("Brak rozwiązań dla wczytanej planszy");
        } else {
            int fileGameType = 0;
            do {
                System.out.println();
                System.out.println("Co chcesz zrobić?");
                System.out.println("1 - zagraj");
                System.out.println("2 - skorzystaj z solvera");
                System.out.print("Wybierz tryb: ");
                try {
                    fileGameType = scanner.nextInt();
                } catch (InputMismatchException e) {
                    clearScreen();
                    System.out.println("Ups! Coś poszło nie tak. Wprowadź proszę numer trybu gry (1 lub 2).");
                    scanner.next();
                    continue;
                }
            } while (fileGameType != 1 && fileGameType != 2);
            //NonogramGeneratedTable.setColToPrint(nonogram.getColToPrint());
            //NonogramGeneratedTable.setRowToPrint(nonogram.getRowToPrint());

            NonogramGeneratedTable.setSolution(solvedSolution.getFirst());
            NonogramGeneratedTable.setColNumbers(NonogramGeneratedTable.createColToPrint());
            NonogramGeneratedTable.setRowNumbers(NonogramGeneratedTable.createRowToPrint());
            clearScreen();
            if (fileGameType == 2) {
                AbstractNonogram.setRowPos(-2);
                AbstractNonogram.setColPos(-2);
                String ok ="";
                do {
                    Nonogram.clearScreen();
                    System.out.println();
                    for (int i = 0; i < solvedSolution.size(); i++) {
                        AbstractNonogram.setBoard(solvedSolution.get(i));
                        System.out.print("Rozwiązanie nr. ");
                        System.out.println(i + 1);
                        System.out.println();
                        NonogramGeneratedTable.printBoard();
                    }
                    System.out.print("Wpisz \"ok\" aby kontynuować: ");
                    ok = scanner.next();

                }while(!ok.equals("ok"));

                isSolver = true;
            } else {
                if (solvedSolution.size() > 1) {
                    System.out.println();
                    System.out.println("Dla wczytanej planszy istnieje kilka rozwiązań");
                    System.out.println("W grze zostanie wykorzystane pierwsze z nich");
                    Thread.sleep(5000);
                    NonogramGeneratedTable.initializeBoard();
                }
                clearScreen();
                System.out.println();
                NonogramGeneratedTable.initializeBoard();
                NonogramGeneratedTable.setSolution(solvedSolution.getFirst());


                while (!NonogramPlayer.isGameComplete()) {
                    NonogramGeneratedTable.printBoard();
                    NonogramPlayer.readMove();
                }
                AbstractNonogram.setBoard(AbstractNonogram.getSolution());
                AbstractNonogram.setRowPos(-2);
                AbstractNonogram.setColPos(-2);
            }
        }
    }

    private static void generatedGame(int rowLength, int colLength, int rowPos, int colPos, ArrayList<String> rowToPrint, ArrayList<String> colToPrint, ArrayList<String> columnsToCheck) throws IOException, InterruptedException {

        NonogramLvl nonogramLvl = new NonogramLvl();
        NonogramPlayer.setIsWin(true);
        nonogramLvl.chooseLevel();
        int SIZE = nonogramLvl.getSIZE();
        int[][] solution = new int[SIZE][SIZE];
        int[][] board = new int[SIZE][SIZE];

        NonogramGeneratedTable nonogramGeneratedTable = new NonogramGeneratedTable(SIZE, rowLength, colLength, rowPos, colPos, solution, board, rowToPrint, colToPrint, columnsToCheck);
        NonogramPlayer nonogramPlayer = new NonogramPlayer(SIZE, rowLength, colLength, rowPos, colPos, solution, board);
        NonogramGeneratedTable.initializeBoard();
        NonogramGeneratedTable.createSolution();
        clearScreen();
        System.out.println();
        while (!NonogramPlayer.isGameComplete()) {
            NonogramGeneratedTable.printBoard();
            NonogramPlayer.readMove();
        }

        AbstractNonogram.setBoard(AbstractNonogram.getSolution());
        AbstractNonogram.setRowPos(-2);
        AbstractNonogram.setColPos(-2);
    }

    private static void finishGame(boolean isGenerated) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        char move;
        do {
            clearScreen();
            System.out.println();
            if (!isSolver) {
                if (NonogramPlayer.isIsWin()) {
                    System.out.println("Gratulacje, rozwiązałeś nonogram!");
                    System.out.println();
                } else {
                    System.out.println("Przegrałeś :(");
                    System.out.println("Poprawne rozwiązanie:");
                    System.out.println();
                }
                NonogramGeneratedTable.printBoard();

            }
            System.out.println();
            System.out.print("Co teraz?(r-jeszcze raz, q-koniec gry): ");
            move = scanner.next().charAt(0);
        } while (move != 'q' && move != 'r');
        if (move == 'r')
            startNonogramGame();
    }

    public static void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

}