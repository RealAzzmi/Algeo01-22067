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

    public double determinantByCofactor(Matrix m) {
        int n = m.matrix.length;
        if (n == 1) {
            return m.matrix[0][0];
        }
        if (n == 2) {
            return m.matrix[0][0] * m.matrix[1][1] - m.matrix[0][1] * m.matrix[1][0];
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
                    subMatrix.matrix[j - 1][subCol++] = m.matrix[j][k];
                }
            }
            int sign = (i % 2 == 0) ? 1 : -1;
            det += sign * m.matrix[0][i] * determinantByCofactor(subMatrix);
        }
        return det;
    }

    public double determinantByReduction() {
        return 0;
    }

    public Matrix inverse() {
        return new Matrix(0, 0);
    }

    public Matrix adjoint(Matrix original) {
        Matrix tempMatrix = new Matrix(original.row(),original.col());
        for(int i =0;i<original.row();i++){
            for(int j=0;j<original.col();j++){
                tempMatrix.matrix[i][j]= Math.pow(-1,i+ j)*determinantByCofactor(minor(original, i, j));
            }
        }
        tempMatrix.transpose();
        return tempMatrix;
    }

    public void transpose(){
        //diasumsikan matriks persegi
        for (int i =0; i<this.row()-1;i++){
            for(int j =i+1;j<this.col();j++){
                double temp = this.matrix[i][j];
                this.matrix[i][j]=this.matrix[j][i];
                this.matrix[i][j]= temp;
            }
        }
    }

    public static Matrix minor(Matrix original, int col,int row) {
        Matrix mMinor = new Matrix(original.row()-1, original.col()-1);
        int x=0;    
        for(int i=0;i<original.row();i++){
                if(i==row){
                    x=1;
                    continue;
                }
                int y =0;
                for(int j=0;j<original.col();j++){
                    if(j==col){
                        y=1;
                        continue;
                    }
                    mMinor.matrix[i-x][j-y] = original.matrix[i][j];
                }
            }
        return mMinor;
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
