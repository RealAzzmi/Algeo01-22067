package linearalgebra;

public class Matrix {
    public double[][] matrix;

    public Matrix(int row, int col) {
        this.matrix = new double[row][col];
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
        if (firstRow == secondRow) return;
        for (int i = 0; i < this.col(); ++i) {
            double temp = this.matrix[firstRow][i];
            this.matrix[firstRow][i] = this.matrix[secondRow][i];
            this.matrix[secondRow][i] = temp;
        }
    }

    public void swapColumn(int firstColumn, int secondColumn) {
        if (firstColumn == secondColumn) return;
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

    public double determinantByCofactor() {
        return 0;
    }
    public double determinantByReduction() {
        return 0;
    }

    public Matrix inverse() {
        return new Matrix(0, 0);
    }

    public Matrix adjoint() {
        return new Matrix(0, 0);
    }

    public Matrix cofactor(int row, int col) {
        return new Matrix(0, 0);
    }
    public void print() {
        for (int i = 0; i < this.row(); ++i) {
            for (int j = 0; j < this.col(); ++j) {
                System.out.printf("%5.2f ", this.matrix[i][j]);
            }
            System.out.println();
        }
    }
}
