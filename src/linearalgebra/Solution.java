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

	public void print() {
		if (this.type == SolutionType.NONEXISTENT) {
			System.out.println("Tidak memiliki solusi.");
		} else if (this.type == SolutionType.UNIQUE) {
			System.out.println("Memiliki solusi unik.");
			for (int i = 0; i < solution.row(); ++i) {
				System.out.printf("x_{%d} = %f\n", i + 1, solution.matrix[i][0]);
			}
		} else if (this.type == SolutionType.INFINITE) {
			System.out.println("Sistem memiliki solusi tak hingga. Print solusi parametrik belum diimplementasikan...");

			// Belum selesai.

			// int nonZeroRow = solution.row() - 1;
			// for (; nonZeroRow >= 0; --nonZeroRow) {
			// 	int col = 0;
			// 	while (col < solution.col() - 1 && solution.matrix[nonZeroRow][col] == 0) continue;
			// 	if (col != solution.col() - 1) break;
			// }
		} else if (this.type == SolutionType.SINGULAR) {
			System.out.println("Matriks koefisien tidak memiliki balikan.");
		} 
		else {
			System.out.println("Solution tidak terdefinisi");
		}
		System.out.println();
	}
}