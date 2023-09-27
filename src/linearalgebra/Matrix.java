package linearalgebra;

public class Matrix {
    public double[][] matrix;
    

    public Matrix(int row, int col) {
        this.matrix = new double[row][col];
    }
    public Matrix(double[][] newMatrix){
        this.matrix = newMatrix;
    }
    public void setMatrix(double[][] newMatrix){
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

    public Matrix multiplyByNum(double num) {
        Matrix result = new Matrix(this.row(), this.col());

        for (int i = 0; i < this.row(); i++) {
            for (int j = 0; j < this.col(); j++) {
                result.matrix[i][j] = this.matrix[i][j] * num;
            }
        }

        return result;
    }

    public static double determinantByCofactor(Matrix m) {
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
    public static boolean isLowerTriangular(Matrix m){
        if (m.row()< 2){
        return false;
        }
        for (int i = 0; i < m.row(); i++) {
            for (int j = 0; j < i; j++) {
                if (m.matrix[i][j] != 0){
                    return false;
            }
        }
    }
    return true;
    }
    public static boolean isUpperTriangular(Matrix m){
        if (m.row()< 2){
        return false;
        }
        for(int i = 0; i < m.row(); i++) {
            for (int j = i+1; j < m.col(); j++) {
                if (m.matrix[i][j] != 0){
                    return false;
            }
        }
    }
    return true;
    }
    public static  double determinantByReduction(Matrix m) {
        if(isUpperTriangular(m) || isLowerTriangular(m)){
            double det =1;
            for(int i =0;i<m.row();i++){
                det=det* m.matrix[i][i];
            }
            return det;
        }else{
            double det = 1;
            for(int j = 0; j<m.col()-1;j++){
                for(int i = m.row() - 1; i >= j; i--) {
                    for (int k = m.matrix.length - 1; k >= j; k--) {
                        double tmp1 =m.matrix[i][j];
                        double tmp2 =m.matrix[k][j];
                        if(Math.abs(tmp1) < Math.abs(tmp2)){
                            m.swapRow(i, k);
                            det= det*-1;
                        }
                    }
                }
                for(int l=m.row()-1; l>j ;l--){
                    if(m.matrix[l][j]==0){
                        continue;
                }
                m.subtractRowFromRow(l, l-1, m.matrix[l][j]/m.matrix[l-1][j]);
            }
        }
        for(int i=0;i<m.col();i++){
            det= det*m.matrix[i][i];
        }
        return det;
        }
    }

    public static Matrix adjoint(Matrix original) {
        Matrix tempMatrix = new Matrix(original.row(),original.col());
        for(int i =0;i<original.row();i++){
            for(int j=0;j<original.col();j++){
                tempMatrix.matrix[i][j]= Math.pow(-1,i+ j)*determinantByCofactor(minor(original, i, j));
            }
        }
        tempMatrix.transpose();
        return tempMatrix;
    }
    public Matrix inverse() {
        // Isi augmented menjadi [A | I]
        int order = this.matrix.length;
        Matrix augmented = new Matrix(order, 2 * order);
        for (int i = 0; i < order; ++i) {
            for (int j = 0; j < order; ++j) {
                augmented.matrix[i][j] = this.matrix[i][j];
                if (i == j) augmented.matrix[i][j + order] = 1;
            }
        }
        
        // Forward phase (Row echelon)
        int currentRow = 0;
        for (int currentColumn = 0; currentColumn < order; ++currentColumn) {
            int firstRow = currentRow;
            
            while (firstRow < order && augmented.matrix[firstRow][currentColumn] == 0) ++firstRow;
            if (firstRow >= order) continue;

            augmented.swapRow(firstRow, currentRow);
            augmented.divideRow(currentRow, augmented.matrix[currentRow][currentColumn]); 

            for (int below = currentRow + 1; below < order; ++below) {
                if (augmented.matrix[below][currentColumn] == 0) continue;
                double multiple = augmented.matrix[below][currentColumn] / augmented.matrix[currentRow][currentColumn];
                augmented.subtractRowFromRow(below, currentRow, multiple);
            }
            ++currentRow;
        }
        // Backward phase (Reduced row echelon)
        for (int currentColumn = 0; currentColumn < order; ++currentColumn) {
            int lastRow = order - 1;

            while (lastRow >= 0 && augmented.matrix[lastRow][currentColumn] != 1) --lastRow;
            if (lastRow <= 0) continue;

            for (int above = 0; above < lastRow; ++above) {
                if (augmented.matrix[above][currentColumn] == 0) continue;
                double multiple = augmented.matrix[above][currentColumn] / augmented.matrix[lastRow][currentColumn];
                augmented.subtractRowFromRow(above, lastRow, multiple);
            }
        }
        Matrix result = new Matrix(order, order);
        for (int i = 0; i < order; ++i) {
            for (int j = 0; j < order; ++j) {
                result.matrix[i][j] = augmented.matrix[i][j+order];
            }
        }
        return result;
    }
    public static Matrix inverseByAdjoint(Matrix m) {
        // Pre kondisi : Matriks harus matriks persegi
        double det = determinantByCofactor(m);

        if(det == 0){
            // Matriks tidak punya invers
            return null;
        }
        else{
            Matrix adjoint = adjoint(m);
            Matrix inverseMatrix = adjoint.multiplyByNum(1.0/det);
            return inverseMatrix;
        }
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
    public static Matrix multiplyMatrix(Matrix m1,Matrix m2){
        //prekondisi kolom m1 dan baris m2 sama
        Matrix m3 = new Matrix(m1.row(),m2.col());
        for(int i =0 ;i<m1.row();i++){
            for(int j=0;j<m2.col();j++){
                m3.matrix[i][j]=0;
                for(int k=0;k<m1.col();k++){
                    m3.matrix[i][j]+=m1.matrix[i][k]*m2.matrix[k][j];
                }
            }
        }
        return m3;

    }
}
