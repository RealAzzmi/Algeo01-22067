package linearalgebra;

enum SolutionType {
	// Linear System Solution Type
	NONEXISTENT,
	UNIQUE,
	INFINITE,
	// Inverse Matrix Solution Type
	SINGULAR,
}

public class Solution {
    public SolutionType type;
    public Matrix solution;

    public Solution(SolutionType type, Matrix solution) {
        this.type = type;
        this.solution = solution;
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

            // Belum selesai.

			// int nonZeroRow = solution.row() - 1;
			// for (; nonZeroRow >= 0; --nonZeroRow) {
			// 	int col = 0;
			// 	while (col < solution.col() - 1 && solution.matrix[nonZeroRow][col] == 0) continue;
			// 	if (col != solution.col() - 1) break;
			// }

        } else if (this.type == SolutionType.SINGULAR) {
            result.append("Matriks koefisien tidak memiliki balikan.\n");
        }
		else {
            result.append("Solution tidak terdefinisi\n");
        }

        return result.toString();
    }

    public void print() {
        System.out.println(this.toString());
    }

}