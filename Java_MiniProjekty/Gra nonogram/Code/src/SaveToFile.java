import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SaveToFile {

    private static int size;

    private static int rowLength;
    private static ArrayList<String> rowToPrint;
    private static ArrayList<String> colToPrint;

    SaveToFile(int size, ArrayList<String> rowToPrint, ArrayList<String> colToPrint, int rowLength) {
        this.size = size;
        this.rowToPrint = rowToPrint;
        this.colToPrint = colToPrint;
        this.rowLength = rowLength;
    }

    public static void saveFile() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> colToSave = createColToSave();
        ArrayList<String> rowToSave = createRowToSave();
        String fileName;
        String filePath;
        Nonogram.clearScreen();
        System.out.println();
        System.out.println("Podaj nazwe pliku, do którego będzie zapisana plansza: ");
        fileName = scanner.next();
        filePath = "Resources\\" + fileName + ".txt";
        File file = new File(filePath);
        PrintWriter writer = new PrintWriter(file);
        writer.println("Scale:" + size + " " + size);
        for (int i = 0; i < colToSave.size(); i++) {
            writer.println("ColToPrint:" + colToSave.get(i));
        }
        for (int i = 0; i < rowToSave.size(); i++) {
            if (i == rowToSave.size() - 1) {
                writer.print("RowToPrint:" + rowToSave.get(i));
            } else {
                writer.println("RowToPrint:" + rowToSave.get(i));
            }
        }
        writer.close();
        Nonogram.clearScreen();
        System.out.println();

    }

    private static ArrayList<String> createColToSave() {
        ArrayList<String> colToSave = new ArrayList<>();
        for (int i = 0; i < colToPrint.size(); i++) {
            String line = colToPrint.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == ' ') {
                    line = line.substring(0, j) + '0' + line.substring(j + 1);
                }
            }
            colToSave.add(line);
        }
        return colToSave;
    }

    private static ArrayList<String> createRowToSave() {
        ArrayList<String> rowToSave = new ArrayList<>();
        for (int i = 0; i < rowToPrint.size(); i++) {
            String line = "";
            while (line.length() + rowToPrint.get(i).length() < rowLength) {
                line += "|0";
            }
            line += rowToPrint.get(i);
            line = line.substring(0, line.length() - 1);
            rowToSave.add(line);
        }
        return rowToSave;
    }

}
