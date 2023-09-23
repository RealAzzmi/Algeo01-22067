import java.util.Scanner;

import linearalgebra.LinearSystem;
import linearalgebra.Matrix;

public class Application {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int row_count, col_count;
        row_count = input.nextInt();
        col_count = input.nextInt();

        Matrix augMatrix = new Matrix(row_count, col_count);
        for (int i = 0; i < row_count; ++i) {
            for (int j = 0; j < col_count; ++j) {
                augMatrix.matrix[i][j] = input.nextDouble();
            }
        }
        Matrix minor = Matrix.minor(augMatrix,0,0);
        
        minor.print();

        LinearSystem ls = new LinearSystem(augMatrix);
        // ls.augmentedMatrix.print();
        // System.out.println();
        // System.out.println();

        ls.gaussJordan();
        Matrix solution = ls.gaussJordan();
        solution.print();

        Matrix cramerMatrix = ls.cramer();
        cramerMatrix.print();

        input.close();
    }
}
