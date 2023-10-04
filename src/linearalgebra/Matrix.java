package linearalgebra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Matrix {
    public double[][] matrix;

    public Matrix(int row, int col) {
        this.matrix = new double[row][col];
    }

    public Matrix(double[][] newMatrix) {
        this.matrix = newMatrix;
    }

    public void setMatrix(double[][] newMatrix) {
        this.matrix = newMatrix;
    }

    public int row() {
        return this.matrix.length;
    }

    public int col() {
        return this.matrix[0].length;
    }

    public void addRowFromRow(int toRow, int fromRow, double multiple) {
        for (int i = 0; i < this.col(); ++i) {
            this.matrix[toRow][i] += multiple * this.matrix[fromRow][i];
        }
    }

    public void subtractRowFromRow(int toRow, int fromRow, double multiple) {
        addRowFromRow(toRow, fromRow, -multiple);
    }

    public void addColumnFromColumn(int toColumn, int fromColumn, double multiple) {
        for (int i = 0; i < this.row(); ++i) {
            this.matrix[i][toColumn] += multiple * this.matrix[i][fromColumn];
        }
    }

    public void subtractColumnFromColumn(int toColumn, int fromColumn, double multiple) {
        addColumnFromColumn(toColumn, fromColumn, -multiple);
    }

    public void divideRow(int row, double num) {
        for (int i = 0; i < this.col(); ++i) {
            this.matrix[row][i] /= num;
        }
    }

    public void divideColumn(int col, double num) {
        for (int i = 0; i < this.row(); ++i) {
            this.matrix[i][col] /= num;
        }
    }

    public void swapRow(int firstRow, int secondRow) {
        if (firstRow == secondRow)
            return;
        for (int i = 0; i < this.col(); ++i) {
            double temp = this.matrix[firstRow][i];
            this.matrix[firstRow][i] = this.matrix[secondRow][i];
            this.matrix[secondRow][i] = temp;
        }
    }

    public void swapColumn(int firstColumn, int secondColumn) {
        if (firstColumn == secondColumn)
            return;
        for (int i = 0; i < this.row(); ++i) {
            double temp = this.matrix[i][firstColumn];
            this.matrix[i][firstColumn] = this.matrix[i][secondColumn];
            this.matrix[i][secondColumn] = temp;
        }
    }

    public static Matrix deepCopy(Matrix original) {
        double[][] result = new double[original.row()][];
        for (int r = 0; r < original.row(); r++) {
            result[r] = original.matrix[r].clone();
        }
        Matrix copy = new Matrix(original.row(), original.col());
        copy.matrix = result;
        return copy;

    }

    public Matrix multiplyByNum(double num) {
        Matrix result = new Matrix(this.row(), this.col());

        for (int i = 0; i < this.row(); i++) {
            for (int j = 0; j < this.col(); j++) {
                result.matrix[i][j] = this.matrix[i][j] * num;
            }
        }

        return result;
    }

    public Solution determinantByCofactor() {
        if (this.row() != this.col()) {
            return new Solution(SolutionType.UNDEFINED);
        }

        int n = this.matrix.length;
        if (n == 1) {
            return new Solution(SolutionType.OTHER, this.matrix[0][0]);
        } else if (n == 2) {
            return new Solution(SolutionType.OTHER,
                    this.matrix[0][0] * this.matrix[1][1] - this.matrix[0][1] * this.matrix[1][0]);
        }

        double det = 0;
        for (int i = 0; i < n; i++) {
            Matrix subMatrix = new Matrix(n - 1, n - 1);
            for (int j = 1; j < n; j++) {
                int subCol = 0;
                for (int k = 0; k < n; k++) {
                    if (k == i) {
                        continue;
                    }
                    subMatrix.matrix[j - 1][subCol++] = this.matrix[j][k];
                }
            }
            int sign = (i % 2 == 0) ? 1 : -1;
            det += sign * this.matrix[0][i] * subMatrix.determinantByCofactor().value;
        }
        return new Solution(SolutionType.OTHER, det);
    }

    public static boolean isLowerTriangular(Matrix m) {
        if (m.row() < 2) {
            return false;
        }
        for (int i = 0; i < m.row(); i++) {
            for (int j = 0; j < i; j++) {
                if (m.matrix[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isUpperTriangular(Matrix m) {
        if (m.row() < 2) {
            return false;
        }
        for (int i = 0; i < m.row(); i++) {
            for (int j = i + 1; j < m.col(); j++) {
                if (m.matrix[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public Solution determinantByReduction() {
        if (this.row() != this.col()) {
            return new Solution(SolutionType.UNDEFINED);
        }
        if (isUpperTriangular(this) || isLowerTriangular(this)) {
            double det = 1;
            for (int i = 0; i < this.row(); i++) {
                det *= this.matrix[i][i];
            }
            return new Solution(SolutionType.OTHER, det);
        } else {
            double det = 1;
            for (int j = 0; j < this.col() - 1; j++) {
                for (int i = this.row() - 1; i >= j; i--) {
                    for (int k = this.matrix.length - 1; k >= j; k--) {
                        double tmp1 = this.matrix[i][j];
                        double tmp2 = this.matrix[k][j];
                        if (Math.abs(tmp1) < Math.abs(tmp2)) {
                            this.swapRow(i, k);
                            det = -det;
                        }
                    }
                }
                for (int l = this.row() - 1; l > j; l--) {
                    if (this.matrix[l][j] == 0) {
                        continue;
                    }
                    this.subtractRowFromRow(l, l - 1, this.matrix[l][j] / this.matrix[l - 1][j]);
                }
            }
            for (int i = 0; i < this.col(); i++) {
                det *= this.matrix[i][i];
            }
            return new Solution(SolutionType.OTHER, det);
        }
    }

    public Solution adjoint() {
        if (this.row() != this.col()) {
            return new Solution(SolutionType.UNDEFINED);
        }
        Matrix tempMatrix = new Matrix(this.row(), this.col());
        for (int i = 0; i < this.row(); i++) {
            for (int j = 0; j < this.col(); j++) {
                tempMatrix.matrix[i][j] = Math.pow(-1, i + j) * this.minor(i, j).determinantByCofactor().value;
            }
        }
        tempMatrix.transpose();
        return new Solution(SolutionType.OTHER, tempMatrix);
    }

    public Solution inverse() {
        if (this.row() != this.col())
            return new Solution(SolutionType.UNDEFINED);
        // Isi augmented menjadi [A | I]
        int order = this.matrix.length;
        Matrix augmented = new Matrix(order, 2 * order);
        for (int i = 0; i < order; ++i) {
            for (int j = 0; j < order; ++j) {
                augmented.matrix[i][j] = this.matrix[i][j];
                if (i == j)
                    augmented.matrix[i][j + order] = 1;
            }
        }

        // Forward phase (Row echelon)
        int currentRow = 0;
        for (int currentColumn = 0; currentColumn < order; ++currentColumn) {
            int firstRow = currentRow;

            while (firstRow < order && augmented.matrix[firstRow][currentColumn] == 0)
                ++firstRow;
            if (firstRow >= order)
                continue;

            augmented.swapRow(firstRow, currentRow);
            augmented.divideRow(currentRow, augmented.matrix[currentRow][currentColumn]);

            for (int below = currentRow + 1; below < order; ++below) {
                if (augmented.matrix[below][currentColumn] == 0)
                    continue;
                double multiple = augmented.matrix[below][currentColumn] / augmented.matrix[currentRow][currentColumn];
                augmented.subtractRowFromRow(below, currentRow, multiple);
            }
            ++currentRow;
        }
        // Backward phase (Reduced row echelon)
        for (int currentColumn = 0; currentColumn < order; ++currentColumn) {
            int lastRow = order - 1;

            while (lastRow >= 0 && augmented.matrix[lastRow][currentColumn] != 1)
                --lastRow;
            if (lastRow < 0)
                continue;

            for (int above = 0; above < lastRow; ++above) {
                if (augmented.matrix[above][currentColumn] == 0)
                    continue;
                double multiple = augmented.matrix[above][currentColumn] / augmented.matrix[lastRow][currentColumn];
                augmented.subtractRowFromRow(above, lastRow, multiple);
            }
        }
        // Cek jika [I | A]. Jika tidak, maka matriks singular
        boolean isValid = true;
        for (int i = 0; i < order && isValid; ++i) {
            for (int j = 0; j < order && isValid; ++j) {
                if ((i == j && augmented.matrix[i][j] != 1) || (i != j && augmented.matrix[i][j] != 0)) isValid = false;
            }
        }
        if (!isValid) return new Solution(SolutionType.SINGULAR);

        // Jika iya, return inversenya.
        Matrix result = new Matrix(order, order);
        for (int i = 0; i < order; ++i) {
            for (int j = 0; j < order; ++j) {
                result.matrix[i][j] = augmented.matrix[i][j + order];
            }
        }
        return new Solution(SolutionType.INVERTIBLE, result);
    }

    public Solution inverseByAdjoint() {
        // Pre kondisi : Matriks harus matriks persegi
        if (this.row() != this.col()) return new Solution(SolutionType.UNDEFINED);
        if (this.row() == 1) return new Solution(SolutionType.OTHER, "Matriks 1 x 1 tidak dapat diselesaikan dengan metode inverse dengan adjoint walaupun memiliki inverse");
        double det = this.determinantByCofactor().value;
        if (det == 0) {
            // Matriks tidak punya invers
            return new Solution(SolutionType.SINGULAR);
        } else {
            Matrix adjoint = this.adjoint().solution;
            Matrix inverseMatrix = adjoint.multiplyByNum(1.0 / det);
            return new Solution(SolutionType.INVERTIBLE, inverseMatrix);
        }
    }

    public void transpose() {
        // Diasumsikan matriks persegi
        for (int i = 0; i < this.row() - 1; i++) {
            for (int j = i + 1; j < this.col(); j++) {
                double temp = this.matrix[i][j];
                this.matrix[i][j] = this.matrix[j][i];
                this.matrix[i][j] = temp;
            }
        }
    }

    public Matrix minor(int col, int row) {
        Matrix mMinor = new Matrix(this.row() - 1, this.col() - 1);
        int x = 0;
        for (int i = 0; i < this.row(); i++) {
            if (i == row) {
                x = 1;
                continue;
            }
            int y = 0;
            for (int j = 0; j < this.col(); j++) {
                if (j == col) {
                    y = 1;
                    continue;
                }
                mMinor.matrix[i - x][j - y] = this.matrix[i][j];
            }
        }
        return mMinor;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < this.row(); ++i) {
            for (int j = 0; j < this.col(); ++j) {
                result.append(String.format("%5.2f ", this.matrix[i][j]));
            }
            result.append("\n");
        }

        return result.toString();
    }

    public void print() {
        for (int i = 0; i < this.row(); ++i) {
            for (int j = 0; j < this.col(); ++j) {
                System.out.printf("%5.2f ", this.matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static Matrix multiplyMatrix(Matrix m1, Matrix m2) {
        // prekondisi kolom m1 dan baris m2 sama
        Matrix m3 = new Matrix(m1.row(), m2.col());
        for (int i = 0; i < m1.row(); i++) {
            for (int j = 0; j < m2.col(); j++) {
                m3.matrix[i][j] = 0;
                for (int k = 0; k < m1.col(); k++) {
                    m3.matrix[i][j] += m1.matrix[i][k] * m2.matrix[k][j];
                }
            }
        }
        return m3;

    }

    public static Matrix getInputMatrixFromUser(Scanner userInput) {
        System.out.println("Masukkan jumlah baris dan kolom matriks augmented: ");
        int row_count = userInput.nextInt();
        int col_count = userInput.nextInt();

        System.out.println("Masukkan matriks augmented: ");
        Matrix augMatrix = new Matrix(row_count, col_count);
        for (int i = 0; i < row_count; ++i) {
            for (int j = 0; j < col_count; ++j) {
                augMatrix.matrix[i][j] = userInput.nextDouble();
            }
        }
        return augMatrix;
    }

    public static Matrix getInputMatrixFromFile(String fileName) throws FileNotFoundException {
        File inputFile = new File(fileName);
        Scanner fileInput = new Scanner(new FileInputStream(inputFile));

        List<List<Double>> matrixData = new ArrayList<>();

        while (fileInput.hasNextLine()) {
            String line = fileInput.nextLine();
            String[] values = line.split("\\s+");

            List<Double> row = new ArrayList<>();
            for (String value : values) {
                row.add(Double.parseDouble(value));
            }

            matrixData.add(row);
        }

        int row_count_file = matrixData.size();
        int col_count_file = matrixData.get(0).size();

        Matrix augMatrix_file = new Matrix(row_count_file, col_count_file);
        for (int i = 0; i < row_count_file; ++i) {
            List<Double> row = matrixData.get(i);
            for (int j = 0; j < col_count_file; ++j) {
                augMatrix_file.matrix[i][j] = row.get(j);
            }
        }

        return augMatrix_file;
    }
}
