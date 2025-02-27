import java.util.ArrayList;

public abstract class AbstractNonogram {
    protected static int SIZE;
    protected static int rowLength;
    protected static int colLength;
    protected static int rowPos;
    protected static int colPos;
    protected static int[][] solution;
    protected static int[][] board;

    public AbstractNonogram(int SIZE,int rowLength,int colLength,int rowPos,int colPos,int[][] solution,int[][] board){
        AbstractNonogram.SIZE = SIZE;
        AbstractNonogram.rowLength = rowLength;
        AbstractNonogram.colLength = colLength;
        AbstractNonogram.rowPos = rowPos;
        AbstractNonogram.colPos = colPos;
        AbstractNonogram.solution = solution;
        AbstractNonogram.board = board;

    }

    public static int getSIZE() {
        return SIZE;
    }

    public static void setSIZE(int SIZE) {
        AbstractNonogram.SIZE = SIZE;
    }

    public static int getRowLength() {
        return rowLength;
    }

    public static void setRowLength(int rowLength) {
        AbstractNonogram.rowLength = rowLength;
    }

    public static int getColLength() {
        return colLength;
    }

    public static void setColLength(int colLength) {
        AbstractNonogram.colLength = colLength;
    }

    public static int getRowPos() {
        return rowPos;
    }

    public static void setRowPos(int rowPos) {
        AbstractNonogram.rowPos = rowPos;
    }

    public static int getColPos() {
        return colPos;
    }

    public static void setColPos(int colPos) {
        AbstractNonogram.colPos = colPos;
    }

    public static int[][] getSolution() {
        return solution;
    }

    public static void setSolution(int[][] solution) {
        AbstractNonogram.solution = solution;
    }

    public static int[][] getBoard() {
        return board;
    }

    public static void setBoard(int[][] board) {
        AbstractNonogram.board = board;
    }
}
