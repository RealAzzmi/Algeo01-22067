package linearalgebra;

enum SolutionType {
    // Linear System Solution Type
    NONEXISTENT,
    UNIQUE,
    INFINITE,
    // Inverse Matrix Solution Type
    SINGULAR,
    UNDEFINED,
    INVERTIBLE,
    //
    OTHER
}

public class Solution {
    public SolutionType type;
    public Matrix solution;
    public String message;
    public double value;

    public Solution(SolutionType type) {
        this.type = type;
    }

    public Solution(SolutionType type, Matrix solution) {
        this.type = type;
        this.solution = solution;
    }

    public Solution(SolutionType type, String message) {
        this.type = type;
        this.message = message;
    }

    public Solution(SolutionType type, double value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        if (this.type == SolutionType.NONEXISTENT) {
            result.append("Tidak memiliki solusi.\n");
        } else if (this.type == SolutionType.UNIQUE) {
            result.append("Memiliki solusi unik.\n");
            for (int i = 0; i < solution.row(); ++i) {
                result.append(String.format("x_{%d} = %f\n", i + 1, solution.matrix[i][0]));
            }
        } else if (this.type == SolutionType.INFINITE) {
            result.append("Sistem memiliki solusi tak hingga. Print solusi parametrik belum diimplementasikan...\n");

            // Backward phase (Reduced row echelon)
            for (int currentColumn = 0; currentColumn < solution.col() - 1; ++currentColumn) {
                int lastRow = solution.row() - 1;

                while (lastRow >= 0 && solution.matrix[lastRow][currentColumn] != 1)
                    --lastRow;
                if (lastRow < 0)
                    continue;

                for (int above = 0; above < lastRow; ++above) {
                    if (solution.matrix[above][currentColumn] == 0)
                        continue;
                    double multiple = solution.matrix[above][currentColumn] / solution.matrix[lastRow][currentColumn];
                    solution.subtractRowFromRow(above, lastRow, multiple);
                }
            }
            // Parametric Equations
            for (int currentRow = 0; currentRow < solution.row(); ++currentRow) {
                int leading = 0;
                while (leading < solution.col() - 1 && solution.matrix[currentRow][leading] == 0) ++leading;
                if (leading == solution.col() - 1) continue;
                result.append(String.format("x_{%d} =", leading + 1));

                if (solution.matrix[currentRow][solution.col() - 1] != 0) result.append(String.format(" %f", solution.matrix[currentRow][solution.col() - 1]));
                for (int free = leading + 1; free < solution.col() - 1; ++free) {
                    if (solution.matrix[currentRow][free] == 0) continue;
                    result.append(String.format(" %fx_{%d} ", -solution.matrix[currentRow][free], free + 1));
                }
                result.append("\n");
            }

        } else if (this.type == SolutionType.SINGULAR) {
            result.append("Matriks tidak memiliki balikan.\n");
        }
         else if (this.type == SolutionType.UNDEFINED) {
            result.append("Matriks bukan matriks persegi.\n");
         } 
        else if (this.type == SolutionType.OTHER) {
            result.append(message);
            result.append("\n");
            result.append(value);
        } else {
            result.append("Solution tidak terdefinisi\n");
        }

        return result.toString();
    }

    public void print() {
        System.out.println(this.toString());
    }

}