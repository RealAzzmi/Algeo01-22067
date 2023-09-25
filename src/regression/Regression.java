package regression;

import linearalgebra.LinearSystem;
import linearalgebra.Matrix;

public class Regression {
    private Matrix inputMatrix;

    public Regression(Matrix inputMatrix) {
        this.inputMatrix = inputMatrix;
    }

    public void calculateRegression() {
        int row_count = inputMatrix.row();
        int col_count = inputMatrix.col();

        int reg_row = col_count;
        int reg_col = col_count + 1;

        Matrix regMatrix = new Matrix(reg_row, reg_col);

        for (int i = 0; i < reg_row; ++i) {
            for (int j = 0; j < reg_col; ++j) {
                double sum = 0;
                for (int k = 0; k < row_count; ++k) {
                    if (i == 0 && j == 0) {
                        sum += 1;
                    } else if (i == 0) {
                        sum += inputMatrix.matrix[k][j - 1];
                    } else if (j == 0) {
                        sum += inputMatrix.matrix[k][i - 1];
                    } else {
                        sum += inputMatrix.matrix[k][i - 1] * inputMatrix.matrix[k][j - 1];
                    }
                }
                regMatrix.matrix[i][j] = sum;
            }
        }

        LinearSystem ls = new LinearSystem(regMatrix);
        Matrix solution = ls.gauss();

        System.out.println("Persamaan regresi adalah:");
        printRegressionEquation(solution);
    }

    private void printRegressionEquation(Matrix solution) {
        int n = solution.row() - 1;
        StringBuilder equation = new StringBuilder("y = ");
    
        for (int i = 0; i <= n; ++i) {
            double coefficient = solution.matrix[i][0];
            if (coefficient != 0) {
                if (i == 0) {
                    equation.append(coefficient);
                } else {
                    equation.append(" + ").append(coefficient).append("x").append(i);
                }
            }
        }
    
        System.out.println(equation.toString());
    }
    
}
