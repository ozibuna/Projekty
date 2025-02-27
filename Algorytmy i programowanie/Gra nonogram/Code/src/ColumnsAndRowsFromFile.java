

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ColumnsAndRowsFromFile {

    public static String[][] readFromFile(String filePath) throws IOException {
        List<String> columnList = new ArrayList<>();
        List<String> rowList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ColToPrint:")) {
                    columnList.add(line);
                }
                if (line.startsWith("RowToPrint:")) {
                    rowList.add(line);
                }
            }
        }
        String[][] result = {columnList.toArray(new String[0]), rowList.toArray(new String[0])};
        return result;
    }

    static ArrayList<int[]> processColumns(String[] input) {
        ArrayList<int[]> columns = new ArrayList<>();
        int check=0;
        for (String line : input) {
            List<Integer> numbers = extractNumbers(line);

            for (int i = 0; i < numbers.size(); i++) {
                if (i >= columns.size()) {
                    columns.add(new int[]{});
                }
                if(numbers.get(i)!=0){
                    columns.set(i, addToArray(columns.get(i), numbers.get(i)));
                }
                else if(check+1==input.length){
                    columns.set(i, addToArray(columns.get(i), numbers.get(i)));
                }
            }
            check++;
        }

        return columns;
    }


    static ArrayList<int[]> processRows(String[] input) {
        ArrayList<int[]> rows = new ArrayList<>();

        for (String line : input) {
            List<Integer> numbers = extractNumbers(line);
            if(numbers.size()>1){
                for(int i=0; i<numbers.size(); i++){
                    if(numbers.get(i)==0){
                        numbers.remove(i);
                        i--;
                    }
                }
            }
            int[] rowArray = new int[numbers.size()];

            for (int i = 0; i < numbers.size(); i++) {
                rowArray[i] = numbers.get(i);
            }

            rows.add(rowArray);
        }

        return rows;
    }

    private static int[] addToArray(int[] array, int value) {
        int[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = value;
        return newArray;
    }
    private static List<Integer> extractNumbers(String line) {
        List<Integer> numbers = new ArrayList<>();

        String[] parts = line.split("\\|");
        for (String part : parts) {
            try {
                int number = Integer.parseInt(part);
                numbers.add(number);
            } catch (NumberFormatException e) {
                //
            }
        }

        return numbers;
    }

    static void checkSumConstraint(int[] column) {
        int size = column.length;
        int sum = 0;
        for (int n : column)
            sum+=n;
        if (size == 1) {
            if (sum > size) {
                System.out.println("Jedna cyfra musi mieć sumę równą albo mniejszą rozmiarowi planszy..");
            }
        } else if (size == 2) {
            if (sum != size - 1) {
                System.out.println("Dwie cyfry muszą mieć sumę o jeden mniejszą niż rozmiar planszy.");
            }
        } else if (size == 3) {
            if (sum != size - 2) {
                System.out.println("Suma cyfr przekracza rozmiar planszy.");
            }
        }
    }
}
