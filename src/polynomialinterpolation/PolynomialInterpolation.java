package polynomialinterpolation;

import java.util.ArrayList;

import linearalgebra.LinearSystem;
import linearalgebra.Matrix;
import linearalgebra.Solution;
import linearalgebra.SolutionType;

public class PolynomialInterpolation {
    ArrayList<Double> x = new ArrayList<>();
    ArrayList<Double> y = new ArrayList<>();
    public Matrix coefficient;

    public void addPoint(double a, double b) {
        this.x.add(a);
        this.y.add(b);
    }

    public void clear() {
        x.clear();
        y.clear();
    }

    public void run() {
        int totalPoint = x.size();
        Matrix augmented = new Matrix(totalPoint, totalPoint + 1);
        for (int row = 0; row < totalPoint; ++row) {
            for (int col = 0; col < totalPoint; ++col) {
                augmented.matrix[row][col] = Math.pow(x.get(row), totalPoint - 1 - col);
            }
        }
        for (int row = 0; row < totalPoint; ++row) {
            augmented.matrix[row][totalPoint] = y.get(row);
        }
        coefficient = new LinearSystem(augmented).gauss().solution;
    }

    public Solution approximate(double x) {
        int totalPoint = this.x.size();
        double result = 0;
        for (int i = 0; i < this.x.size(); ++i) {
            result += coefficient.matrix[i][0] * Math.pow(x, totalPoint - 1 - i);
        }
        return new Solution(SolutionType.OTHER, result);
    }
}
