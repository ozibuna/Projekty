
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class NonogramLvl {
    private int SIZE;
    private final Scanner scanner;

    public NonogramLvl() {
        scanner = new Scanner(System.in);
    }


    public void chooseLevel() throws IOException, InterruptedException {
        int lvl = 0;
        do {
            System.out.println();
            System.out.println("Poziomy trudności:");
            System.out.println("1 - łatwy");
            System.out.println("2 - średni");
            System.out.println("3 - trudny");
            System.out.print("Wybierz poziom: ");

            try {
                lvl = scanner.nextInt();
                if (lvl < 1 || lvl > 3) {
                    Nonogram.clearScreen();
                    System.out.println();
                    System.out.println("Oho! Taki poziom trudności nie istnieje. Wybierz 1, 2 lub 3.");
                }
            } catch (InputMismatchException e) {
                Nonogram.clearScreen();
                System.out.println();
                System.out.println("Hm... To chyba nie jest numer poziomu trudności.");
                scanner.next();
                continue;
            }
        } while (lvl != 1 && lvl != 2 && lvl != 3);
        Nonogram.clearScreen();
        System.out.println();
        setSIZE(lvl);
    }

    private void setSIZE(int lvl) {
        switch (lvl) {
            case 1:
                SIZE = 5;
                break;
            case 2:
                SIZE = 8;
                break;
            case 3:
                SIZE = 13;
                break;
        }
    }
    public int getSIZE() {
        return SIZE;
    }
}

