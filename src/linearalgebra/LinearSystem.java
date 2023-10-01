package linearalgebra;

public class LinearSystem {
    public Matrix augmentedMatrix;
    public Matrix coefficient;
    public Matrix constant;

    public LinearSystem(Matrix augmentedMatrix) {
        this.augmentedMatrix = augmentedMatrix;
        this.coefficient = new Matrix(augmentedMatrix.row(), augmentedMatrix.col() - 1);
        this.constant = new Matrix(augmentedMatrix.row(), 1);

        for (int i = 0; i < coefficient.row(); ++i) {
            for (int j = 0; j < coefficient.col(); ++j) {
                this.coefficient.matrix[i][j] = this.augmentedMatrix.matrix[i][j];
            }
        }
        for (int i = 0; i < coefficient.row(); ++i) {
            this.constant.matrix[i][0] = this.augmentedMatrix.matrix[i][coefficient.col()];
        }

    }

    public LinearSystem(Matrix coefficient, Matrix constant) {
        this.coefficient = coefficient;
        this.constant = constant;
        this.augmentedMatrix = new Matrix(coefficient.row(), coefficient.col() + 1);
        for (int i = 0; i < coefficient.row(); ++i) {
            for (int j = 0; j < coefficient.col(); ++j) {
                this.augmentedMatrix.matrix[i][j] = this.coefficient.matrix[i][j];
            }
        }
        for (int i = 0; i < coefficient.row(); ++i) {
            this.augmentedMatrix.matrix[i][coefficient.col()] = this.constant.matrix[i][0];
        }
    }

    public Solution gaussJordan() {
        Matrix result = Matrix.deepCopy(this.augmentedMatrix);

        // Forward phase (Row echelon)
        int currentRow = 0;
        for (int currentColumn = 0; currentColumn < result.col() - 1; ++currentColumn) {
            int firstRow = currentRow;

            while (firstRow < result.row() && result.matrix[firstRow][currentColumn] == 0)
                ++firstRow;
            if (firstRow >= result.row())
                continue;

            result.swapRow(firstRow, currentRow);
            result.divideRow(currentRow, result.matrix[currentRow][currentColumn]);

            for (int below = currentRow + 1; below < result.row(); ++below) {
                if (result.matrix[below][currentColumn] == 0)
                    continue;
                double multiple = result.matrix[below][currentColumn] / result.matrix[currentRow][currentColumn];
                result.subtractRowFromRow(below, currentRow, multiple);
            }
            ++currentRow;
        }
        // Backward phase (Reduced row echelon)
        for (int currentColumn = 0; currentColumn < result.col(); ++currentColumn) {
            int lastRow = result.row() - 1;

            while (lastRow >= 0 && result.matrix[lastRow][currentColumn] != 1)
                --lastRow;
            if (lastRow <= 0)
                continue;

            for (int above = 0; above < lastRow; ++above) {
                if (result.matrix[above][currentColumn] == 0)
                    continue;
                double multiple = result.matrix[above][currentColumn] / result.matrix[lastRow][currentColumn];
                result.subtractRowFromRow(above, lastRow, multiple);
            }
        }

        // Check if nonexistent
        for (int nonZeroRow = currentRow; nonZeroRow < result.row(); ++nonZeroRow) {
            if (result.matrix[nonZeroRow][result.col() - 1] != 0) {
                return new Solution(SolutionType.NONEXISTENT, null);
            }
        }
        // Check if infinite
        for (int diagonal = 0; diagonal < Math.min(augmentedMatrix.row(), augmentedMatrix.col() - 1); ++diagonal) {
            if (result.matrix[diagonal][diagonal] != 1) {
                return new Solution(SolutionType.INFINITE, result);
            }
        }
        // Otherwise, the solution must be unique.
        // Backward substitution.
        Matrix solution = new Matrix(result.row(), 1);
        for (int i = 0; i < solution.row(); ++i) {
            solution.matrix[i][0] = result.matrix[i][result.col() - 1];
        }
        return new Solution(SolutionType.UNIQUE, solution);
    }

    public Solution gauss() {
        Matrix result = Matrix.deepCopy(this.augmentedMatrix);

        // Forward phase (Row echelon)
        int currentRow = 0;
        for (int currentColumn = 0; currentColumn < result.col() - 1; ++currentColumn) {
            int firstRow = currentRow;

            while (firstRow < result.row() && result.matrix[firstRow][currentColumn] == 0)
                ++firstRow;
            if (firstRow >= result.row())
                continue;

            result.swapRow(firstRow, currentRow);
            result.divideRow(currentRow, result.matrix[currentRow][currentColumn]);

            for (int below = currentRow + 1; below < result.row(); ++below) {
                if (result.matrix[below][currentColumn] == 0)
                    continue;
                double multiple = result.matrix[below][currentColumn] / result.matrix[currentRow][currentColumn];
                result.subtractRowFromRow(below, currentRow, multiple);
            }
            ++currentRow;
        }
        result.print();
        // Check if nonexistent
        for (int nonZeroRow = currentRow; nonZeroRow < result.row(); ++nonZeroRow) {
            if (result.matrix[nonZeroRow][result.col() - 1] != 0) {
                return new Solution(SolutionType.NONEXISTENT, null);
            }
        }

        // Check if infinite
        if (result.col() - 1 > result.row()) return new Solution(SolutionType.INFINITE, result);
        for (int diagonal = 0; diagonal < result.col() - 1; ++diagonal) {
            if (result.matrix[diagonal][diagonal] != 1) {
                return new Solution(SolutionType.INFINITE, result);
            }
        }
        // Otherwise, the solution must be unique.
        // Backward substitution
        int nonZeroRow = result.row() - 1;
        for (; nonZeroRow >= 0; --nonZeroRow) {
            int col = 0;
            while (col < result.col() - 1 && result.matrix[nonZeroRow][col] == 0) ++col;
            if (col != result.col() - 1) break;
        }
        int solutionSize = nonZeroRow + 1;
        Matrix solution = new Matrix(solutionSize, 1);
        for (int row = nonZeroRow; row >= 0; --row) {
            double x = result.matrix[row][result.col() - 1];
            for (int col = row + 1; col < result.col() - 1; ++col) {
                x -= result.matrix[row][col] * solution.matrix[col][0];
            }
            solution.matrix[row][0] = x;
        }
        return new Solution(SolutionType.UNIQUE, solution);
    }

    public Solution solveInverse() {
        Solution iCoeff = this.coefficient.inverse();
        if (iCoeff.type == SolutionType.SINGULAR)
            return iCoeff;

        Matrix solution = Matrix.multiplyMatrix(iCoeff.solution, this.constant);
        return new Solution(SolutionType.UNIQUE, solution);
    }

    public Solution cramer() {
        int nCoeff = this.augmentedMatrix.row();

        // Inisialisasi matriks koefisien, konstanta, dan hasil
        Matrix coeff = new Matrix(nCoeff, nCoeff);
        Matrix constant = new Matrix(nCoeff, 1);
        Matrix result = new Matrix(nCoeff, 1);

        // Memisahkan matriks koefisien dan konstanta
        for (int i = 0; i < nCoeff; i++) {
            for (int j = 0; j < nCoeff; j++) {
                coeff.matrix[i][j] = this.augmentedMatrix.matrix[i][j];
            }
            constant.matrix[i][0] = this.augmentedMatrix.matrix[i][nCoeff];
        }

        // Determinan matriks koefisien
        double detCoeff = Matrix.determinantByCofactor(coeff);

        if (detCoeff == 0) {
            // Metode cramer tidak dapat digunakan karena determinan matriks koefisien
            // adalah nol
            return null;
        }

        // Menyelipkan matriks konstanta ke matriks koefisien lalu menghitung pembagian
        // determinannya dan disimpan di matriks result
        for (int i = 0; i < nCoeff; i++) {
            Matrix modifiedCoeff = Matrix.deepCopy(coeff);
            for (int j = 0; j < nCoeff; j++) {
                modifiedCoeff.matrix[j][i] = constant.matrix[j][0];
            }
            double detModified = Matrix.determinantByCofactor(modifiedCoeff);
            result.matrix[i][0] = detModified / detCoeff;
        }

        // return result;

        return new Solution(null, null);
    }

}
