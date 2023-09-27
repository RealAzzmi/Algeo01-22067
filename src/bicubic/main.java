package bicubic;

import linearalgebra.Matrix;
public class main{
//ini tester doang

public class Main {
    public static void main(String[] args) {
        double[][] inputArray = {
            {1},
            {2},
            {3},
            {4},
            {5},
            {6},
            {7},
            {8},
            {9},
            {10},
            {11},
            {12},
            {13},
            {14},
            {15},
            {16}
        };
        Matrix inputMatrix = new Matrix(inputArray);
        Bicubic.prepare();
        double x = 0.5;
        double y = 0.5;
        double result = Bicubic.approximate(inputMatrix, x, y);

        System.out.println("Approximation: " + result);
    }
}

}
