
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class NonogramPlayer extends AbstractNonogram{

    private static boolean isWin = true;
    public NonogramPlayer(int SIZE,int rowLength,int colLength,int rowPos,int colPos,int[][] solution,int[][] board){
        super(SIZE, rowLength, colLength, rowPos, colPos, solution, board);
    }
    static void readMove() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("(w,a,s,d - przemieszczanie się, z - komórka pełna, x - komórka pusta, q - poddaj się, save - zapis planszy)");
        System.out.print("Wykonaj ruch: ");
        String move = "";
        try {
            move = scanner.next();
            Nonogram.clearScreen();
            System.out.println();
        } catch (InputMismatchException e) {
            System.out.println("Oho! To nie jest dobry ruch. Spróbuj ponownie.");
            scanner.next();
            readMove();
        }
        switch (move) {
            case "a":
                moveLeft();
                break;
            case "s":
                moveDown();
                break;
            case "d":
                moveRight();
                break;
            case "w":
                moveUp();
                break;
            case "z":
                fillSquare();
                NonogramGeneratedTable.checkCol();
                NonogramGeneratedTable.checkRow();
                break;
            case "x":
                clearSquare();
                break;
            case "q":
                endGame();
                break;
            case "save":
                saveFile();
                break;
        }
    }
    private static void moveUp(){
        if (isValidMove(rowPos - 1, colPos)) {
            rowPos--;
        } else {
            rowPos = SIZE - 1;
        }
    }
    private static void moveDown(){
        if (isValidMove(rowPos + 1, colPos)) {
            rowPos++;
        } else {
            rowPos = 0;
        }
    }
    private static void moveRight(){
        if (isValidMove(rowPos, colPos + 1)) {
            colPos++;
        } else {
            colPos = 0;
        }
    }
    private static void moveLeft() {
        if (isValidMove(rowPos, colPos - 1)) {
            colPos--;
        } else {
            colPos = SIZE - 1;
        }
    }
    private static void clearSquare(){
        if (board[rowPos][colPos] == 2) {
            board[rowPos][colPos] = 0;
        } else {
            board[rowPos][colPos] = 2;
//            if(isGenerated) {
//                NonogramGeneratedTable.checkRow();
//                NonogramGeneratedTable.checkCol();
//            }else {
//                GivenTable.checkRow();
//                GivenTable.checkCol();
//            }
        }
    }
    private static void fillSquare(){
        if (board[rowPos][colPos] == 1) {
            board[rowPos][colPos] = 0;
        } else {
            board[rowPos][colPos] = 1;
        }
    }

    private static void endGame(){
        isWin = false;
        for(int i=0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                board[i][j]=solution[i][j];
            }
        }
    }

    private static void saveFile() throws IOException, InterruptedException {
        SaveToFile saver = new SaveToFile(SIZE, NonogramGeneratedTable.getRowToPrint(), NonogramGeneratedTable.getColToPrint(), rowLength);
        saver.saveFile();
    }
    private static void makeMove() {
        Scanner scanner = new Scanner(System.in);
        int current = board[rowPos][colPos];
        switch (current) {
            case 0:
                board[rowPos][colPos] = 1;
                break;
            case 1:
                board[rowPos][colPos] = 2;
                break;
            case 2:
                board[rowPos][colPos] = 0;
                break;
        }
    }

    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    static boolean isGameComplete() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (solution[i][j] == 1 && board[i][j] != 1) {
                    return false;
                }
                if (solution[i][j] == 0 && board[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isIsWin() {
        return isWin;
    }

    public static void setIsWin(boolean isWin) {
        NonogramPlayer.isWin = isWin;
    }
}
