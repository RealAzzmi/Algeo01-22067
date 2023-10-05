import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------- \n \n");
        System.out.println(
    "        ███    ███  █████  ████████ ██████  ██ ██   ██      ██████  █████  ██       ██████ ██    ██ ██       █████  ████████  ██████  ██████  \n" +
    "        ████  ████ ██   ██    ██    ██   ██ ██  ██ ██      ██      ██   ██ ██      ██      ██    ██ ██      ██   ██    ██    ██    ██ ██   ██ \n" +
    "        ██ ████ ██ ███████    ██    ██████  ██   ███       ██      ███████ ██      ██      ██    ██ ██      ███████    ██    ██    ██ ██████  \n" +
    "        ██  ██  ██ ██   ██    ██    ██   ██ ██  ██ ██      ██      ██   ██ ██      ██      ██    ██ ██      ██   ██    ██    ██    ██ ██   ██ \n" +
    "        ██      ██ ██   ██    ██    ██   ██ ██ ██   ██      ██████ ██   ██ ███████  ██████  ██████  ███████ ██   ██    ██     ██████  ██   ██ \n \n"
        );
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------- \n");

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Sistem Persamaan Linear");
            System.out.println("2. Determinan");
            System.out.println("3. Matriks Balikan");
            System.out.println("4. Interpolasi Polinom");
            System.out.println("5. Regresi Linear Berganda");
            System.out.println("6. Interpolasi Bicubic Spline");
            System.out.println("7. Resize Image");
            System.out.println("8. Keluar");
            System.out.print("Pilih menu: ");

            int choice = userInput.nextInt();
            System.out.println();

            if (choice == 1) {
                Menu.solveLinearSystem(userInput);
            } else if (choice == 2) {
                Menu.solveDeterminant(userInput);
            } else if (choice == 3) {
                Menu.solveInverse(userInput);
            } else if (choice == 4) {
                Menu.solvePolynomial(userInput);
            } else if (choice == 5) {
                Menu.solveMultipleLinearRegression(userInput);
            } else if (choice == 6) {
                Menu.solveBicubicSplineInterpolation(userInput);
            } else if (choice == 7) {
                Menu.solveResizeImage(userInput);
            } else if (choice == 8) {
                System.out.println("Program telah keluar.");
                userInput.close();
                System.exit(0);
            } else {
                System.out.println("Pilihan tidak valid. Silakan pilih lagi.");
            }
        }
    }
}
