package bicubic;
import linearalgebra.Matrix;
public class Bicubic{
    public static Matrix xMatrix = new Matrix(new double[][] {
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,2,3,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0},
            {0,1,2,3,0,1,2,3,0,1,2,3,0,1,2,3},
            {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,2,0,0,0,3,0,0,0},
            {0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3},
            {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,1,2,3,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,1,0,0,0,2,0, 0,0,3,0,0},
            {0,0,0,0,0,1,2,3,0,2,4,6,0,3,6,9},     
    });
    public static Matrix dMatrix = xMatrix.inverse();
    
    public static double approximate(Matrix inputMatrix, double x, double y) {
        Matrix coefficients = Matrix.multiplyMatrix(dMatrix, inputMatrix);
        double approximation = 0;
        for (int yExp = 0; yExp < 4; ++yExp) {
            for (int xExp = 0; xExp < 4; ++xExp) {
                approximation += coefficients.matrix[4 * yExp + xExp][0] * Math.pow(x, xExp) * Math.pow(y, yExp);
            }
        }
        return approximation;
    }
}
