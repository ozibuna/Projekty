import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NonogramSolver {
    private static int sum;
    private static int rows;
    private static int columns;
    private static int[][] board;
    //private static int[][] solved;
    private static ArrayList<int[][]> solvedSolutions;
    private static ArrayList<int[]> rowNumbers;
    private static ArrayList<int[]> colNumbers;

    public NonogramSolver(int rows, int columns, ArrayList<int[]> rowNumbers, ArrayList<int[]> colNumbers) {
        NonogramSolver.rows = rows;
        NonogramSolver.columns = columns;
        NonogramSolver.rowNumbers = rowNumbers;
        NonogramSolver.colNumbers = colNumbers;
    }

    /*public static void main(String[] args) {
        sum = 0;
        setBoard();
        board = new int[rows][columns];

        knownRows = generateKnownRows();
        board = new int[rows][columns];
        tryColor(0, board);
        System.out.println(sum);
    }*/
    public static void init() {
        sum = 0;
        solvedSolutions = new ArrayList<>();
        board = new int[rows][columns];
        setBoard();

        ArrayList<String> knownRows = generateKnownRows();
        board = new int[rows][columns];
        //solved = new int[rows][columns];
        tryColor(0, board);

    }


    public static void tryColor(int row, int[][] plansza) {
        int[] cyfry = rowNumbers.get(row);
        int dlugoscRzedu = columns;
        List<String> wyniki = generujKombinacje(cyfry, dlugoscRzedu);

        ArrayList<int[]> cyfrowe = new ArrayList<>();

        for (String kombinacja : wyniki) {
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < kombinacja.length(); j++) {
                if (Character.isDigit(kombinacja.charAt(j))) {
                    int a = kombinacja.charAt(j);
                    numbers.add(a - 48);
                }
            }
            int[] num = new int[numbers.size()];
            for (int k = 0; k < numbers.size(); k++) {
                num[k] = numbers.get(k);
            }
            cyfrowe.add(num);
        }
        for (int[] kombinacje : cyfrowe) {

            if (columns >= 0) System.arraycopy(kombinacje, 0, plansza[row], 0, columns);
            if (!isCorrect(plansza)) {
                for (int l = 0; l < rows; l++) {
                    plansza[row][l] = 0;
                }
                continue;
            }
            if (row + 1 < rows) {
                tryColor(row + 1, plansza);
            } else {
                int[][] solved = new int[rows][columns];
                for(int i= 0; i<rows; i++){
                    if (columns >= 0) System.arraycopy(plansza[i], 0, solved[i], 0, columns);
                }
                solvedSolutions.add(solved);
                sum++;
            }
            for (int l = 0; l < columns; l++) {
                plansza[row][l] = 0;
            }
        }
    }

    public static List<String> generujKombinacje(int[] cyfry, int dlugoscRzedu) {
        List<String> kombinacje = new ArrayList<>();
        char[] obecnaKombinacja = new char[dlugoscRzedu];
        Arrays.fill(obecnaKombinacja, '0');

        generujKombinacjeRekurencyjnie(cyfry, obecnaKombinacja, 0, 0, kombinacje);

        return kombinacje;
    }

    private static void generujKombinacjeRekurencyjnie(int[] cyfry, char[] obecnaKombinacja, int indeksCyfry, int indeksRzedu, List<String> kombinacje) {
        if (indeksCyfry == cyfry.length) {
            kombinacje.add(new String(obecnaKombinacja));
            return;
        }
        for (int i = indeksRzedu; i <= obecnaKombinacja.length - cyfry[indeksCyfry]; i++) {
            Arrays.fill(obecnaKombinacja, i, i + cyfry[indeksCyfry], '1');
            generujKombinacjeRekurencyjnie(cyfry, obecnaKombinacja, indeksCyfry + 1, i + cyfry[indeksCyfry] + 1, kombinacje);
            Arrays.fill(obecnaKombinacja, i, i + cyfry[indeksCyfry], '0');
        }
    }

    private static ArrayList<String> generateKnownRows(){
        List<String> kombinacje;
        ArrayList<String> known = new ArrayList<>();
        String[] knownRows = new String[rows];
        for(int i=0; i<rows; i++){
            knownRows[i]="";
        }
        for(int i=0; i<columns; i++){

            kombinacje = generujKombinacje(colNumbers.get(i), rows);
            for(int j=0; j<rows; j++){
                if(isKnown(kombinacje, j)){
                    knownRows[j] += "1";
                }
                else{
                    knownRows[j] += "0";
                }
            }
        }
        for(int i=0; i<rows; i++){
            known.add(knownRows[i]);
        }
        return known;
    }

    private static boolean isKnown(List<String> kombinacje, int pos){
        for (String s : kombinacje) {
            if (s.charAt(pos) == '0') {
                return false;
            }
        }
        return true;
    }

    public static boolean isCorrect(int[][] plansza) {
        ArrayList<int[]> currentCol;
        currentCol = crateColNumbers(plansza);
        for (int i = 0; i < columns; i++) {
            int[] expected = colNumbers.get(i);
            int[] current = currentCol.get(i);
            if (current.length > expected.length) {
                return false;
            }
            for (int j = 0; j < current.length; j++) {
                if (current[j] > expected[j]) {
                    return false;
                } else if (current[j] < expected[j] && current.length > j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void setBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = 0;
            }
        }
    }

    public static void show(int[][] board) {
        for (int[] ints : board) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    public static ArrayList<int[]> crateColNumbers(int[][] plansza) {
        ArrayList<int[]> columnsToCheck = new ArrayList<>();
        for (int i = 0; i < columns; i++) {
            ArrayList<Integer> columns = new ArrayList<>();
            int temp = 0;
            boolean isEmpty = true;
            for (int j = 0; j < rows; j++) {
                if (plansza[j][i] == 1)
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
            columnsToCheck.add(col);
        }
        return columnsToCheck;
    }

    public static int getSum() {
        return sum;
    }
    public static int[][] getBoard() {
        return board;
    }
    public static ArrayList<int[][]> getSolvedSolutions() {
        return solvedSolutions;
    }
}