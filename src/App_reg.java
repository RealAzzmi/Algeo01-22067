import java.util.Scanner;

import linearalgebra.Matrix;
import regression.Regression;

public class App_reg {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Masukkan matriks nya (x1,x2,..,xn) dan y_i:");
        int row_count, col_count;
        System.out.println("Jumlah baris:");
        row_count = input.nextInt();
        System.out.println("Jumlah kolom:");
        col_count = input.nextInt();

        System.out.println("Masukkan matriksnya:");
        Matrix augMatrix = new Matrix(row_count, col_count);
        for (int i = 0; i < row_count; ++i) {
            for (int j = 0; j < col_count; ++j) {
                augMatrix.matrix[i][j] = input.nextDouble();
            }
        }

        System.out.println("Masukkan matriks (x1,x2,..,xn) yang ingin ditaksir nilai y nya:");

        int col_count_est = col_count - 1;
        Matrix estimateMatrix = new Matrix(col_count_est, 1);
        for (int i = 0; i < col_count_est; i++) {
            for (int j = 0; j < 1; j++){
                estimateMatrix.matrix[i][j] = input.nextDouble();
            }
        }

        Regression reg = new Regression(augMatrix);
        Matrix coefficients = reg.calculateRegression();

        System.out.println("Persamaan regresi adalah:");
        reg.printRegressionEquation(coefficients);

        double y_estimate = Regression.estimateY(coefficients, estimateMatrix);
        System.out.println("Nilai taksiran y adalah: " + y_estimate);

        input.close();
    }
}
