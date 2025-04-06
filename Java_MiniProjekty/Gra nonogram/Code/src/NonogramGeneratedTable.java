import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class NonogramGeneratedTable extends AbstractNonogram {
    private static ArrayList<String> rowToPrint;
    private static ArrayList<String> colToPrint;
    private static ArrayList<String> columnsToCheck;

    private static ArrayList<int[]> rowNumbers;
    private static ArrayList<int[]> colNumbers;

    public NonogramGeneratedTable(int SIZE, int rowLength, int colLength, int rowPos, int colPos, int[][] solution, int[][] board, ArrayList<String> rowToPrint, ArrayList<String> colToPrint, ArrayList<String> columnsToCheck) {
        super(SIZE, rowLength, colLength, rowPos, colPos, solution, board);
        this.rowToPrint = rowToPrint;
        this.colToPrint = colToPrint;
        this.columnsToCheck = columnsToCheck;
    }

    public static void createSolution() {
        Random rand = new Random();
        NonogramSolver solver;
        /*solution = new int[][]{
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}};*/
        do {
            rowLength = 0;
            colLength = 0;
            int random;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    solution[i][j] = rand.nextInt(2);
                }
            }

            /*solution = new int[][]{{0, 0, 1, 0, 0},
                    {0, 1, 1, 1, 1},
                    {0, 0, 0, 1, 0},
                    {1, 1, 1, 0, 1},
                    {1, 0, 1, 0, 1}};*/
            rowNumbers = createRowToPrint();
            colNumbers = createColToPrint();
            System.out.println("Generowanie planszy");

            solver = new NonogramSolver(SIZE, SIZE, rowNumbers, colNumbers);
            solver.init();
        } while (solver.getSum() != 1);


        for (int i = 0; i < SIZE; i++) {
            checkCol();
            checkRow();
            colPos++;
            rowPos++;
        }
        colPos = 0;
        rowPos = 0;
    }

    public static ArrayList<int[]> createRowToPrint() {
        rowToPrint = new ArrayList<>();
        rowNumbers = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            ArrayList<Integer> rows = new ArrayList<>();
            StringBuilder row = new StringBuilder("|");
            int temp = 0;
            boolean isEmpty = true;
            for (int j = 0; j < SIZE; j++) {
                if (solution[i][j] == 1)
                    temp++;
                else {
                    if (temp != 0) {
                        rows.add(temp);
                        isEmpty = false;
                        row.append(temp).append("|");
                        temp = 0;
                    }
                }
            }
            if (temp != 0) {
                rows.add(temp);
                isEmpty = false;
                row.append(temp).append("|");
            }
            if (isEmpty) {
                rows.add(0);
                row.append("0|");
            }
            int[] row1 = new int[rows.size()];
            for (int k = 0; k < rows.size(); k++) {
                row1[k] = rows.get(k);
            }
            rowNumbers.add(row1);
            //row = row.substring(0, row.length() - 1);
            row.append(" ");
            rowToPrint.add(row.toString());
            //System.out.println(row);
            if (row.length() > rowLength) {
                rowLength = row.length();
            }
        }
        return rowNumbers;
    }

    public static ArrayList<int[]> createColToPrint() {
        columnsToCheck = new ArrayList<>();
        colToPrint = new ArrayList<>();
        colNumbers = new ArrayList<>();
        colLength = 0;
        ArrayList<int[]> colHelp = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            ArrayList<Integer> columns = new ArrayList<>();
            StringBuilder toCheck = new StringBuilder();
            int temp = 0;
            boolean isEmpty = true;
            for (int j = SIZE - 1; j >= 0; j--) {
                if (solution[j][i] == 1)
                    temp++;
                else {
                    if (temp != 0) {
                        isEmpty = false;
                        columns.add(temp);
                        toCheck.append(temp);
                        temp = 0;
                    }
                }
            }
            if (temp != 0) {
                isEmpty = false;
                columns.add(temp);
                toCheck.append(temp);
            }
            if (isEmpty) {
                columns.add(0);
                toCheck.append("0");
            }
            int[] col = new int[columns.size()];
            for (int k = 0; k < columns.size(); k++) {
                col[k] = columns.get(k);
            }
            colHelp.add(col);
            //colNumbers.add(col);

            int[] col1 = new int[columns.size()];
            for (int k = 0; k < columns.size(); k++) {
                col1[columns.size() - 1 - k] = columns.get(k);
            }
            colNumbers.add(col1);

            /*for (int k = 0; k < columns.size(); k++) {
                System.out.print(col[k] + " ");
            }*/
            if (columns.size() > colLength)
                colLength = columns.size();
            //System.out.println();
            columnsToCheck.add(toCheck.toString());
        }
        for (int i = 1; i <= colLength; i++) {
            StringBuilder line = new StringBuilder("|");
            for (int j = 0; j < SIZE; j++) {
                int[] col = colHelp.get(j);
                if (col.length > colLength - i) {
                    line.append(col[colLength - i]).append("|");
                } else {
                    line.append(" |");
                }
            }
            //System.out.println(line);
            colToPrint.add(line.toString());
        }
        return colNumbers;
    }

    public static void show() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(solution[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }
    }

    public static void printBoard() {

        for (String s : colToPrint) {
            for (int j = 0; j < rowLength; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < s.length(); j++) {
                if (2 * colPos + 2 >= j && 2 * colPos <= j)
                    System.out.print("\u001B[31m");
                System.out.print(s.charAt(j));
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
        /*String rowNums = "";
        for (int i = 0; i < rowToPrint.get(rowPos).length(); i++) {
            if (rowToPrint.get(rowPos).charAt(i) != ' ' && rowToPrint.get(rowPos).charAt(i) != '|') {
                rowNums += rowToPrint.get(rowPos).charAt(i);
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

        //row = row.substring(0, row.length() - 1);
        if (boardNums.equals(rowNums)) {
            for (int i = 0; i < SIZE; i++) {
                if (board[rowPos][i] == 0) {
                    board[rowPos][i] = 2;
                }
            }
        }*/

        ArrayList<Integer> rows = new ArrayList<>();
        int temp = 0;
        boolean isEmpty = true;
        for (int j = 0; j < SIZE; j++) {
            if (board[rowPos][j] == 1)
                temp++;
            else {
                if (temp != 0) {
                    isEmpty = false;
                    rows.add(temp);
                    temp = 0;
                }
            }
        }
        if (temp != 0) {
            isEmpty = false;
            rows.add(temp);
        }
        if (isEmpty) {
            rows.add(0);
        }
        int[] row = new int[rows.size()];
        for (int k = 0; k < rows.size(); k++) {
            row[k] = rows.get(k);
        }

        boolean isSame = false;
        if (rowNumbers.get(rowPos).length == row.length) {
            isSame = true;
            for (int i = 0; i < row.length; i++) {
                if (rowNumbers.get(rowPos)[i] != row[i]) {
                    isSame = false;
                }
            }
        }
        if (isSame) {
            for (int i = 0; i < SIZE; i++) {
                if (board[rowPos][i] == 0) {
                    board[rowPos][i] = 2;
                }
            }
        }

    }

    public static void checkCol() {
        /*String colNums = "";
        for (int i = columnsToCheck.get(colPos).length() - 1; i >= 0; i--) {
            if (columnsToCheck.get(colPos).charAt(i) != ' ' && columnsToCheck.get(colPos).charAt(i) != '|') {
                colNums += columnsToCheck.get(colPos).charAt(i);
            }
        }
        String boardNums = "";
        int temp = 0;
        boolean isEmpty = true;
        for (int j = 0; j < SIZE; j++) {
            if (board[j][colPos] == 1)
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

        //row = row.substring(0, row.length() - 1);
        if (boardNums.equals(colNums)) {
            for (int i = 0; i < SIZE; i++) {
                if (board[i][colPos] == 0) {
                    board[i][colPos] = 2;
                }
            }
        }*/
        ArrayList<Integer> columns = new ArrayList<>();
        int temp = 0;
        boolean isEmpty = true;
        for (int j = 0; j < SIZE; j++) {
            if (board[j][colPos] == 1)
                temp++;
            else {
                if (temp != 0) {
                    isEmpty = false;
                    columns.add(temp);
                    temp = 0;
                }
            }
        }
        if (temp != 0) {
            isEmpty = false;
            columns.add(temp);
        }
        if (isEmpty) {
            columns.add(0);
        }
        int[] col = new int[columns.size()];
        for (int k = 0; k < columns.size(); k++) {
            col[k] = columns.get(k);
        }

        boolean isSame = false;
        if (colNumbers.get(colPos).length == col.length) {
            isSame = true;
            for (int i = 0; i < col.length; i++) {
                if (colNumbers.get(colPos)[i] != col[i]) {
                    isSame = false;
                }
            }
        }
        if (isSame) {
            for (int i = 0; i < SIZE; i++) {
                if (board[i][colPos] == 0) {
                    board[i][colPos] = 2;
                }
            }
        }

    }


    public static void setRowToPrint(ArrayList<String> rowToPrint) {
        NonogramGeneratedTable.rowToPrint = rowToPrint;
    }

    public static void setColToPrint(ArrayList<String> colToPrint) {
        NonogramGeneratedTable.colToPrint = colToPrint;
    }

    public static void setRowNumbers(ArrayList<int[]> rowNumbers) {
        NonogramGeneratedTable.rowNumbers = rowNumbers;
    }

    public static void setColNumbers(ArrayList<int[]> colNumbers) {
        NonogramGeneratedTable.colNumbers = colNumbers;
    }
    public static ArrayList<String> getRowToPrint() {
        return rowToPrint;
    }

    public static ArrayList<String> getColToPrint() {
        return colToPrint;
    }
}
