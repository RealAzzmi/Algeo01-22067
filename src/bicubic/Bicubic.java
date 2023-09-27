package bicubic;
import linearalgebra.Matrix;
public class Bicubic{
    private static double[][] t ;
    private static void prepare(double[][] m){
        int p=0,q=0;
        for(int i =0;i<16;i++){
            for(int j=0;j<4;j++){
                 if(i<4){
                    m[i][j] =  Math.pow(p,j)*Math.pow(q, 0);    
                    m[i][j+4] = Math.pow(p, j)*Math.pow(q, 1);
                    m[i][j+8] = Math.pow(p, j)*Math.pow(q, 2);
                    m[i][j+12] = Math.pow(p, j)*Math.pow(q, 3);
                }else if(4<=i&&i<8){
                    if(j==0&&p==0){
                        m[i][j]=0;
                        m[i][j+4]=0;
                        m[i][j+8]=0;
                        m[i][j+12]=0;
                        continue;
                    }
                    m[i][j] =  j*Math.pow(p,j-1)*Math.pow(q, 0);    
                    m[i][j+4] =j*Math.pow(p, j-1)*Math.pow(q, 1);
                    m[i][j+8] = j*Math.pow(p, j-1)*Math.pow(q, 2);
                    m[i][j+12] = j*Math.pow(p, j-1)*Math.pow(q,3);
                }else if(8<=i&&i<12){
                    m[i][j] =  0;    
                    m[i][j+4] = 1*Math.pow(p, j)*Math.pow(q, 0);
                    m[i][j+8] = 2*Math.pow(p, j)*Math.pow(q, 1);
                    m[i][j+12] = 3*Math.pow(p, j)*Math.pow(q, 2);
                }else{
                    if(j==0&&p==0){
                        m[i][j]=0;
                        m[i][j+4]=0;
                        m[i][j+8]=0;
                        m[i][j+12]=0;
                        continue;
                    }
                    m[i][j] =  0;    
                    m[i][j+4] = 1*j*Math.pow(p, j-1)*Math.pow(q, 0);
                    m[i][j+8] = 2*j*Math.pow(p, j-1)*Math.pow(q, 1);
                    m[i][j+12] = 3*j*Math.pow(p, j-1)*Math.pow(q, 2);
                }
            
        }
            if(p==0&&q==0){
                p++;
                continue;
            }else if(p==1&&q==0){
                q++;
                p--;
                continue;
            }else if(p==0&&q==1){
                p++;
                continue;
            }
            else if(p==1 && q==1){
                p=0;
                q=0;
                continue;
            }
        }
    }
    public static double approximate(Matrix inputMatrix, double x, double y) {
        t = new double[16][16];
        prepare(t);
        
        Matrix xMatrix = new Matrix(t);
        xMatrix.print();
        Matrix dMatrix = xMatrix.inverse();
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
