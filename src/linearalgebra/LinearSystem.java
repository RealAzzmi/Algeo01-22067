package linearalgebra;

public class LinearSystem {
    public Matrix augmentedMatrix;
    public Matrix coefficient;
    public Matrix constant;
    public LinearSystem(Matrix augmentedMatrix) {
        this.augmentedMatrix = augmentedMatrix;
    }
    public LinearSystem(Matrix coefficient, Matrix constant) {
        this.coefficient =coefficient;
        this.constant = constant;

    }

    public Matrix gaussJordan() {
        Matrix result = Matrix.deepCopy(this.augmentedMatrix);

        // Forward phase (Row echelon)
        int currentRow = 0;
        for (int currentColumn = 0; currentColumn < result.col(); ++currentColumn) {
            int firstRow = currentRow;
            
            while (firstRow < result.row() && result.matrix[firstRow][currentColumn] == 0) ++firstRow;
            if (firstRow >= result.row()) continue;

            result.swapRow(firstRow, currentRow);
            result.divideRow(currentRow, result.matrix[currentRow][currentColumn]); 

            for (int below = currentRow + 1; below < result.row(); ++below) {
                if (result.matrix[below][currentColumn] == 0) continue;
                double multiple = result.matrix[below][currentColumn] / result.matrix[currentRow][currentColumn];
                result.subtractRowFromRow(below, currentRow, multiple);
            }
            ++currentRow;
        }
        // Backward phase (Reduced row echelon)
        for (int currentColumn = 0; currentColumn < result.col(); ++currentColumn) {
            int lastRow = result.row() - 1;

            while (lastRow >= 0 && result.matrix[lastRow][currentColumn] != 1) --lastRow;
            if (lastRow <= 0) continue;

            for (int above = 0; above < lastRow; ++above) {
                if (result.matrix[above][currentColumn] == 0) continue;
                double multiple = result.matrix[above][currentColumn] / result.matrix[lastRow][currentColumn];
                result.subtractRowFromRow(above, lastRow, multiple);
            }
        }

        Matrix solution = new Matrix(result.row(), 1);
        for (int i = 0; i < solution.row(); ++i) {
            solution.matrix[i][0] = result.matrix[i][result.col() - 1];
        }
        return solution;
    }

    public Matrix gauss() {
        Matrix result = Matrix.deepCopy(this.augmentedMatrix);

        // Forward phase (Row echelon)
        int currentRow = 0;
        for (int currentColumn = 0; currentColumn < result.col(); ++currentColumn) {
            int firstRow = currentRow;
            
            while (firstRow < result.row() && result.matrix[firstRow][currentColumn] == 0) ++firstRow;
            if (firstRow >= result.row()) continue;

            result.swapRow(firstRow, currentRow);
            result.divideRow(currentRow, result.matrix[currentRow][currentColumn]); 

            for (int below = currentRow + 1; below < result.row(); ++below) {
                if (result.matrix[below][currentColumn] == 0) continue;
                double multiple = result.matrix[below][currentColumn] / result.matrix[currentRow][currentColumn];
                result.subtractRowFromRow(below, currentRow, multiple);
            }   
            ++currentRow;
        }
        // Backward substitution
        Matrix solution = new Matrix(result.row(), 1);
        for (int row = result.row() - 1; row >= 0; --row) {
            double x = result.matrix[row][result.col() - 1];
            for (int col = row + 1; col < result.col() - 1; ++col) {
                x -= result.matrix[row][col] * solution.matrix[col][0];
            }
            solution.matrix[row][0] = x;
        }
        return solution;
    }
    public Matrix solveInverse(){
        Matrix result = new Matrix(this.coefficient.row(),this.constant.col());
        Matrix iCoeff = this.coefficient.inverse();
        result = Matrix.multiplyMatrix(iCoeff, this.constant);
        return result;
    }


    public Matrix cramer() {
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
        double detCoeff = coeff.determinantByCofactor(coeff);

        if (detCoeff == 0) {
            // Metode cramer tidak dapat digunakan karena determinan matriks koefisien adalah nol
            return null;
        }

        // Menyelipkan matriks konstanta ke matriks koefisien lalu menghitung pembagian determinannya dan disimpan di matriks result
        for (int i = 0; i < nCoeff; i++) {
            Matrix modifiedCoeff = Matrix.deepCopy(coeff);
            for (int j = 0; j < nCoeff; j++) {
                modifiedCoeff.matrix[j][i] = constant.matrix[j][0];
            }
            double detModified = modifiedCoeff.determinantByCofactor(modifiedCoeff);
            result.matrix[i][0] = detModified / detCoeff;
        }

        return result;
    }

}
