
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class UploadedTable extends AbstractNonogram {
    private static ArrayList<String> rowToPrint = new ArrayList<>();
    private static ArrayList<String> colToPrint = new ArrayList<>();
    private static ArrayList<String> columnsToCheck = new ArrayList<>();
    private static String filePath;
    private static NonogramSolver nonogramSolver;
    public UploadedTable(int SIZE, int rowLength, int colLength, int rowPos, int colPos, int[][] solution, int[][] board, ArrayList<String> rowToPrint, ArrayList<String> colToPrint, ArrayList<String> columnsToCheck) {
        super(SIZE, rowLength, colLength, rowPos, colPos, solution, board);
        this.rowToPrint = rowToPrint;
        this.colToPrint = colToPrint;
        this.columnsToCheck = columnsToCheck;
    }

    // Czytanie tablicę nonogramową z pliku
    public void readBoardFromFile() throws IOException, InterruptedException {
        boolean isCorrectFile;
        do {
            isCorrectFile=false;
            Scanner readFileName = new Scanner(System.in);
            System.out.println("podaj nazwę pliku (plik musi się znajdować w folderze Resources)");
            String filename = readFileName.nextLine();
            Nonogram.clearScreen();
            filePath = "Resources\\"+filename;
            try {
                Scanner scanner = new Scanner(new File(filePath));

                // Czytanie rozmiary planszu
                String sizeLine = scanner.nextLine();
                String[] sizeTokens = sizeLine.split(" ");
                int boardSize = Integer.parseInt(sizeTokens[1]);

                String[] input1 = ColumnsAndRowsFromFile.readFromFile(filePath)[0];
                String[] input2 = ColumnsAndRowsFromFile.readFromFile(filePath)[1];
                ArrayList<int []> colNumbers = ColumnsAndRowsFromFile.processColumns(input1);
                ArrayList<int []> rowNumbers = ColumnsAndRowsFromFile.processRows(input2);

                nonogramSolver = new NonogramSolver(boardSize,boardSize,rowNumbers,colNumbers);
                nonogramSolver.init();

                /*if(nonogramSolver.getSum()>1){
                    System.out.println("Więcej niż jedno rozwiązanie, plansza nie zostanie rozwiązana");
                }*/

                setSize(boardSize);
                NonogramGeneratedTable.setColNumbers(colNumbers);
                NonogramGeneratedTable.setRowNumbers(rowNumbers);
                //solution = nonogramSolver.getSolved();

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("RowToPrint:")) {
                        rowToPrint.add(line.substring("RowToPrint:".length())+" ");
                    } else if (line.startsWith("ColToPrint:")) {
                        colToPrint.add(line.substring("ColToPrint:".length()));
                    }
                }

                scanner.close();
            } catch (FileNotFoundException e) {
                isCorrectFile = true;
                System.out.println("Nie ma takiego pliku");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }while (isCorrectFile);
    }

    private void setSize(int size) {
        SIZE = size;
        solution = new int[SIZE][SIZE];
        board = new int[SIZE][SIZE];
        rowLength = 0;
        colLength = 0;
        rowPos = 0;
        colPos = 0;
    }

    public static void createSolution() {
        /*
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                solution[i][j] = rand.nextInt(2);
            }

        }
        solution[0][0] = 1;
        */
        createRowToPrint();
        createColToPrint();
        for(int i=0; i<SIZE; i++){
//            checkCol();
//            checkRow();
            colPos++;
            rowPos++;
        }
        colPos=0;
        rowPos=0;
    }

    public static void createRowToPrint() {
        ArrayList<String> rowPrintable = new ArrayList<>();
        solution=getAllSolutions().getFirst();
        for (int i = 0; i < SIZE; i++) {
            StringBuilder row = new StringBuilder("|");
            int temp = 0;
            boolean isEmpty = true;
            for (int j = 0; j < SIZE; j++) {
                if (solution[i][j] == 1)
                    temp++;
                else {
                    if (temp != 0) {
                        isEmpty = false;
                        row.append(temp).append("|");
                        temp = 0;
                    }
                }
            }
            if (temp != 0) {
                isEmpty = false;
                row.append(temp).append("|");
            }
            if (isEmpty)
                row.append("0|");
            row.append(" ");
            rowPrintable.add(row.toString());

            rowLength = Math.max(rowLength, row.toString().length());
        }
        //return rowPrintable;
    }

    public static void createColToPrint() {
        ArrayList<String> colPrintable = new ArrayList<>();
        solution=getAllSolutions().getFirst();
        for (int i = 0; i < SIZE; i++) {
            StringBuilder col = new StringBuilder("|");
            int temp = 0;
            boolean isEmpty = true;

            for (int j = 0; j < SIZE; j++) {
                if (solution[j][i] == 1) {
                    temp++;
                } else if (temp != 0) {
                    isEmpty = false;
                    col.append(temp).append("|");
                    temp = 0;
                }
            }

            if (temp != 0) {
                isEmpty = false;
                col.append(temp).append("|");
            }

            if (isEmpty)
                col.append("0|");
            colPrintable.add(col.toString());

        }

        colLength = colToPrint.stream().mapToInt(String::length).max().orElse(0);
        //return colPrintable;
    }


    public static void printBoard() {
        for (int i = 0; i < colToPrint.size(); i++) {
            for (int j = 0; j < rowLength; j++) {
                System.out.print(" ");
            }

            for (int j = 0; j < colToPrint.get(i).length(); j++) {
                if (2 * colPos + 2 >= j && 2 * colPos <= j)
                    System.out.print("\u001B[31m");
                System.out.print(colToPrint.get(i).charAt(j));
                if (2 * colPos + 2 >= j && 2 * colPos <= j)
                    System.out.print("\u001B[0m");
            }
            System.out.println();
        }
        for (int k = 0; k < rowLength; k++)
            System.out.print(" ");
        for (int k = 0; k < SIZE; k++)
            System.out.print("--");
        System.out.println("-");
        for (int i = 0; i < SIZE; i++) {
            if (rowToPrint.get(i).length() < rowLength) {
                for (int k = 0; k < rowLength - rowToPrint.get(i).length(); k++) {
                    System.out.print(" ");
                }
            }
            if (rowPos == i)
                System.out.print("\u001B[31m");
            System.out.print(rowToPrint.get(i));
            if (rowPos == i)
                System.out.print("\u001B[0m");
            System.out.print("|");
            for (int j = 0; j < SIZE; j++) {
                if (i == rowPos && j == colPos)
                    System.out.print("\u001B[31m");
                if (board[i][j] == 0) {
                    System.out.print("-");
                } else if (board[i][j] == 1) {
                    System.out.print("\u25A0");
                } else if (board[i][j] == 2) {
                    System.out.print("X");
                }
                if (i == rowPos && j == colPos)
                    System.out.print("\u001B[0m");
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println();
    }


    public static void checkRow() {
        StringBuilder rowNums = new StringBuilder();
        for (int i = 0; i < rowToPrint.get(rowPos).length(); i++) {
            if (rowToPrint.get(rowPos).charAt(i) != ' ' && rowToPrint.get(rowPos).charAt(i) != '|') {
                rowNums.append(rowToPrint.get(rowPos).charAt(i));
            }
        }
        String boardNums = "";
        int temp = 0;
        boolean isEmpty = true;
        for (int j = 0; j < SIZE; j++) {
            if (board[rowPos][j] == 1)
                temp++;
            else {
                if (temp != 0) {
                    isEmpty = false;
                    boardNums += temp;
                    temp = 0;
                }
            }
        }
        if (temp != 0) {
            isEmpty = false;
            boardNums += temp;
        }
        if (isEmpty)
            boardNums += "0";

        if (boardNums.contentEquals(rowNums)) {
            for (int i = 0; i < SIZE; i++) {
                if (board[rowPos][i] == 0) {
                    board[rowPos][i] = 2;
                }
            }
        }

    }

    public static void checkCol() {
        String colNums = "";
        for (int i = columnsToCheck.get(colPos).length()-1; i >= 0; i--) {
            if (columnsToCheck.get(colPos).charAt(i) != ' ' && columnsToCheck.get(colPos).charAt(i) != '|') {
                colNums += columnsToCheck.get(colPos).charAt(i);
            }
        }
        StringBuilder boardNums = new StringBuilder();
        int temp = 0;
        boolean isEmpty = true;
        for (int j = 0; j <
                SIZE; j++) {
            if (board[j][colPos] == 1)
                temp++;
            else {
                if (temp != 0) {
                    isEmpty = false;
                    boardNums.append(temp);
                    temp = 0;
                }
            }
        }
        if (temp != 0) {
            isEmpty = false;
            boardNums.append(temp);
        }
        if (isEmpty)
            boardNums.append("0");

        if (boardNums.toString().equals(colNums)) {
            for (int i = 0; i < SIZE; i++) {
                if (board[i][colPos] == 0) {
                    board[i][colPos] = 2;
                }
            }
        }

    }

    public static ArrayList <int[][]> getAllSolutions(){

        return nonogramSolver.getSolvedSolutions();

    }

    public static ArrayList<String> getRowToPrint() {
        return rowToPrint;
    }

    public static ArrayList<String> getColToPrint() {
        return colToPrint;
    }

    public static String getFilePath() {
        return filePath;
    }

}