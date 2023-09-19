package linearalgebra;

public class LinearSystem {
    public Matrix augmentedMatrix;

    public LinearSystem(Matrix augmentedMatrix) {
        this.augmentedMatrix = augmentedMatrix;
    }
    public LinearSystem(Matrix coefficient, Matrix constant) {

    }

    public Matrix gaussJordan() {
        Matrix result = Matrix.deepCopy(this.augmentedMatrix);

        // Forward phase (Row echelon)
        int currentRow = 0;
        for (int currentColumn = 0; currentColumn < result.col() - 1; ++currentColumn) {
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

            // result.print();
            // System.out.println();
            // System.out.println();
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

            // result.print();
            // System.out.println();
            // System.out.println();
        }

        Matrix solution = new Matrix(result.row(), 1);
        for (int i = 0; i < solution.row(); ++i) {
            solution.matrix[i][0] = result.matrix[i][result.col() - 1];
        }
        return solution;
    }

    public Matrix gauss() {
        return new Matrix(0, 0);
    }
    
    public Matrix cramer() {
        return new Matrix(0, 0);
    }

}
