package linearalgebra;

public enum SolutionType {
    // Linear System Solution Type
    NONEXISTENT,
    UNIQUE,
    INFINITE,
    // Inverse Matrix Solution Type
    SINGULAR, // Jika matriks persegin tidak mempunyai inverse
    UNDEFINED, // Jika bukan matriks persegi
    INVERTIBLE, // Jika matriks persegi mempunyai inverse
    // Others
    OTHER // Jika tidak termasuk kategori di atas
}


// public class SolutionType {
    
// }
