package bicubic;
import linearalgebra.Matrix;
public class Bicubic{
    public static double solveBicubic(Matrix inputMatrix,double a, double b){
        double[][] bicubicValues = {
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
        {1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,1,2,3,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,1,2,3,0,1,2,3,0,1,2,3,0,1,2,3},
        {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0},
        {0,0,0,0,1,0,0,0,2,0,0,0,3,0,0,0},
        {0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3},
        {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,2,3,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,0,0,0,2,0,0,0,3,0,0},
        {0,0,0,0,0,1,2,3,0,2,4,6,0,3,6,9}         
    };Matrix bicubicMatrix = new Matrix(bicubicValues);
    Matrix mInverse= Matrix.inverse(bicubicMatrix);
    Matrix solution = Matrix.multiplyMatrix(mInverse,inputMatrix);
    double sol = 0;
    for(int i=0;i<16;i++){
            if(i<4){
            sol+= solution.matrix[i][0]*Math.pow(a,i);
            }else if(i<8){
                sol+=solution.matrix[i-4][1]*Math.pow(a,i-4)*b;
            }else if(i<12){
                sol+= solution.matrix[i-8][2]*Math.pow(a,i-8)*b*b;
            }else{
                sol+= solution.matrix[i-12][3]*Math.pow(a,i-12)*b*b*b;
            }
        }
    return sol;
}
}
