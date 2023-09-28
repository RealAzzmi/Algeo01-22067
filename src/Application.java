import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bicubic.Bicubic;
import linearalgebra.LinearSystem;
import linearalgebra.Matrix;
import regression.Regression;
import resizeimage.Resizeimage;

public class Application {


    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

    //     System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------- \n \n");
    //     System.out.println(
    // "        ███    ███  █████  ████████ ██████  ██ ██   ██      ██████  █████  ██       ██████ ██    ██ ██       █████  ████████  ██████  ██████  \n" +
    // "        ████  ████ ██   ██    ██    ██   ██ ██  ██ ██      ██      ██   ██ ██      ██      ██    ██ ██      ██   ██    ██    ██    ██ ██   ██ \n" +
    // "        ██ ████ ██ ███████    ██    ██████  ██   ███       ██      ███████ ██      ██      ██    ██ ██      ███████    ██    ██    ██ ██████  \n" +
    // "        ██  ██  ██ ██   ██    ██    ██   ██ ██  ██ ██      ██      ██   ██ ██      ██      ██    ██ ██      ██   ██    ██    ██    ██ ██   ██ \n" +
    // "        ██      ██ ██   ██    ██    ██   ██ ██ ██   ██      ██████ ██   ██ ███████  ██████  ██████  ███████ ██   ██    ██     ██████  ██   ██ \n \n"
    //     );
    //     System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------- \n");


        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Sistem Persamaan Linear");
            System.out.println("2. Determinan");
            System.out.println("3. Matriks Balikan");
            System.out.println("4. Interpolasi Polinom");
            System.out.println("5. Regresi Linear Berganda");
            System.out.println("6. Interpolasi Bicubic Spline");
            System.out.println("7. Resize Image");
            System.out.println("8. Keluar");
            System.out.print("Pilih menu: ");

            int choice = userInput.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Metode:");
                    System.out.println("1. Metode Gauss");
                    System.out.println("2. Metode Gauss-Jordan");
                    System.out.println("3. Metode Matriks balikan");
                    System.out.println("4. Metode Cramer");
                    System.out.println("Pilih metode: ");
                    int linearSubMenuChoice = userInput.nextInt();
            
                    switch (linearSubMenuChoice) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            while (true) {
                                System.out.println("1. Input dari keyboard");
                                System.out.println("2. Input dari file (.txt)");
                                System.out.println("3. Kembali");
                                System.out.print("Pilih jenis input: ");
                                int inputChoice = userInput.nextInt();
                
                                switch (inputChoice) {
                                    case 1:
                                        Matrix augMatrix = Matrix.getInputMatrixFromUser(userInput);
                                        LinearSystem ls = new LinearSystem(augMatrix);
                                        Matrix solution = null;
                                        
                                        if (linearSubMenuChoice == 1) {
                                            solution = ls.gauss();
                                        } else if (linearSubMenuChoice == 2) {
                                            solution = ls.gaussJordan();
                                        } else if (linearSubMenuChoice == 3) {
                                            solution = ls.solveInverse();
                                        } else if (linearSubMenuChoice == 4) {
                                            solution = ls.cramer();
                                        }
                                        
                                        if (solution != null) {
                                            solution.print();
                                        }
                                        break;
                                    case 2:
                                        System.out.print("Masukkan nama file input: ");
                                        userInput.nextLine(); // Membersihkan newline yang tersisa di dalam buffer
                                        String inputFileName = userInput.nextLine();
                
                                        try {
                                            Matrix augMatrix_file = Matrix.getInputMatrixFromFile(inputFileName);
                                            LinearSystem ls_file = new LinearSystem(augMatrix_file);
                                            Matrix solution_file = null;
                
                                            if (linearSubMenuChoice == 1) {
                                                solution_file = ls_file.gauss();
                                            } else if (linearSubMenuChoice == 2) {
                                                solution_file = ls_file.gaussJordan();
                                            } else if (linearSubMenuChoice == 3) {
                                                solution_file = ls_file.solveInverse();
                                            } else if (linearSubMenuChoice == 4) {
                                                solution_file = ls_file.cramer();
                                            }
                
                                            if (solution_file != null) {
                                                solution_file.print();
                                            }
                                        } catch (FileNotFoundException e) {
                                            System.err.println("File tidak ditemukan: " + e.getMessage());
                                        }
                                        break;
                                    default:
                                        System.out.println("Pilihan tidak valid. Silakan pilih lagi.");
                                }
                            }
                        }
                        break;

                case 2:
                    System.out.println("Metode:");
                    System.out.println("1. Metode Kofaktor");
                    System.out.println("2. Metode Reduksi");
                    System.out.print("Pilih metode: ");
                    int determinantSubMenuChoice = userInput.nextInt();

                    switch (determinantSubMenuChoice) {
                        case 1:
                        case 2:
                            while (true) {
                                System.out.println("1. Input dari keyboard");
                                System.out.println("2. Input dari file (.txt)");
                                System.out.println("3. Kembali");
                                System.out.print("Pilih jenis input: ");
                                int inputChoice = userInput.nextInt();

                                switch (inputChoice) {
                                    case 1:
                                        Matrix augMatrix = Matrix.getInputMatrixFromUser(userInput);    

                                        if (determinantSubMenuChoice == 1) {
                                            double detAugMatrix = Matrix.determinantByCofactor(augMatrix);
                                            System.out.println(detAugMatrix);
                                        } else if (determinantSubMenuChoice == 2) {
                                            double detAugMatrix = Matrix.determinantByReduction(augMatrix);
                                            System.out.println(detAugMatrix);
                                        }
                                        break;
                                    case 2:
                                        System.out.print("Masukkan nama file input: ");
                                        userInput.nextLine(); // Membersihkan newline yang tersisa di dalam buffer
                                        String inputFileName = userInput.nextLine();
                
                                        try {
                                            Matrix augMatrix_file = Matrix.getInputMatrixFromFile(inputFileName);

                                            if (determinantSubMenuChoice == 1){
                                                double detAugMatrix = Matrix.determinantByCofactor(augMatrix_file);
                                                System.out.println(detAugMatrix);
                                            }
                                            else if (determinantSubMenuChoice == 2){
                                                double detAugMatrix = Matrix.determinantByReduction(augMatrix_file);
                                                System.out.println(detAugMatrix);
                                            }
                                        } catch (FileNotFoundException e) {
                                                System.err.println("File tidak ditemukan: " + e.getMessage());
                                        }
                                        break;
                                    default:
                                        System.out.println("Pilihan tidak valid. Silakan pilih lagi.");
                                }
                            }
                    }
                    // Handle submenu choice for determinant
                    // Add your code here
                    break;
                case 3:
                    System.out.println("Metode:");
                    System.out.println("1. Metode Adjoint (harus matriks persegi)");
                    System.out.println("2. Metode OBE");
                    System.out.print("Pilih metode: ");
                    int inverseSubMenuChoice = userInput.nextInt();
                    // code

                    switch (inverseSubMenuChoice) {
                        case 1:
                        case 2:
                            while (true) {
                                System.out.println("1. Input dari keyboard");
                                System.out.println("2. Input dari file (.txt)");
                                System.out.println("3. Kembali");
                                System.out.print("Pilih jenis input: ");
                                int inputChoice = userInput.nextInt();

                                switch (inputChoice) {
                                    case 1:
                                        Matrix augMatrix = Matrix.getInputMatrixFromUser(userInput);
                                        if (inverseSubMenuChoice == 1){
                                            Matrix inverseMatrix = Matrix.inverseByAdjoint(augMatrix);
                                            inverseMatrix.print();
                                        }
                                        else if (inverseSubMenuChoice == 2){
                                            Matrix inveMatrix = augMatrix.inverse();
                                            inveMatrix.print();
                                        }
                                        break;
                                    case 2:
                                        System.out.print("Masukkan nama file input: ");
                                        userInput.nextLine(); // Membersihkan newline yang tersisa di dalam buffer
                                        String inputFileName = userInput.nextLine();

                                        try {
                                            Matrix augMatrix_file = Matrix.getInputMatrixFromFile(inputFileName);

                                            if (inverseSubMenuChoice == 1){
                                                Matrix inverseMatrix = Matrix.inverseByAdjoint(augMatrix_file);
                                                inverseMatrix.print();
                                            }
                                            else if (inverseSubMenuChoice == 2){
                                                Matrix inveMatrix = augMatrix_file.inverse();
                                                inveMatrix.print();
                                            }
                                        } catch (FileNotFoundException e) {
                                                System.err.println("File tidak ditemukan: " + e.getMessage());
                                        }
                                        break;
                                    default:
                                        System.out.println("Pilihan tidak valid. Silakan pilih lagi.");
                                }
                            }
                    }

                    break;
                case 4:
                    // polinom
                    break;
                case 5:
                    // regresi
                    System.out.println("1. Input dari keyboard");
                    System.out.println("2. Input dari file (.txt)");
                    System.out.println("3. Kembali");
                    System.out.print("Pilih jenis input: ");
                    int inputChoice = userInput.nextInt();
                    
                    switch (inputChoice) {
                        case 1:
                            // System.out.println("Masukkan matriks nya (x1,x2,..,xn) dan y_i:");
                            // int row_count, col_count;
                            // System.out.println("Jumlah baris:");
                            // row_count = userInput.nextInt();
                            // System.out.println("Jumlah kolom:");
                            // col_count = userInput.nextInt();
                    
                            // System.out.println("Masukkan matriksnya:");
                            // Matrix augMatrix = new Matrix(row_count, col_count);
                            // for (int i = 0; i < row_count; ++i) {
                            //     for (int j = 0; j < col_count; ++j) {
                            //         augMatrix.matrix[i][j] = userInput.nextDouble();
                            //     }
                            // }
                    
                            // System.out.println("Masukkan matriks (x1,x2,..,xn) yang ingin ditaksir nilai y nya:");
                    
                            // int col_count_est = col_count - 1;
                            // Matrix estimateMatrix = new Matrix(col_count_est, 1);
                            // for (int i = 0; i < col_count_est; i++) {
                            //     for (int j = 0; j < 1; j++){
                            //         estimateMatrix.matrix[i][j] = userInput.nextDouble();
                            //     }
                            // }
                    
                            // Regression reg = new Regression(augMatrix);
                            // Matrix coefficients = reg.calculateRegression();
                    
                            // System.out.println("Persamaan regresi adalah:");
                            // reg.printRegressionEquation(coefficients);
                    
                            // double y_estimate = Regression.estimateY(coefficients, estimateMatrix);
                            // System.out.println("Nilai taksiran y adalah: " + y_estimate);

                            break;
                            case 2:
                                System.out.print("Masukkan nama file input: ");
                                userInput.nextLine(); // Membersihkan newline yang tersisa di dalam buffer
                                String inputFileName = userInput.nextLine();

                                try {
                                    File inputFile = new File(inputFileName);
                                    Scanner fileInput = new Scanner(new FileInputStream(inputFile));

                                    int row_count = 0;
                                    int col_count = 0;
                                    Matrix augMatrix = null;
                                    Matrix estimateMatrix = null;

                                    while (fileInput.hasNextLine()) {
                                        String line = fileInput.nextLine();
                                        String[] values = line.split("\\s+");
                                        
                                        if (values.length > 0) {
                                            if (augMatrix == null) {
                                                row_count++;
                                                col_count = values.length;
                                                augMatrix = new Matrix(row_count, col_count);
                                            }
                                            
                                            for (int j = 0; j < col_count; ++j) {
                                                augMatrix.matrix[row_count][j] = Double.parseDouble(values[j]);
                                            }

                                            System.out.println(row_count);

                                        }
                                    }

                                    // Membaca nilai yang ingin ditaksir
                                    String lastLine = fileInput.nextLine();
                                    String[] estimateValues = lastLine.split("\\s+");
                                    int col_count_est = col_count - 1;
                                    System.out.println(col_count_est);
                                    estimateMatrix = new Matrix(col_count_est, 1);
                                    for (int j = 0; j < col_count_est; j++) {
                                        estimateMatrix.matrix[j][0] = Double.parseDouble(estimateValues[j]);
                                    }

                                    fileInput.close();

                                    Regression reg = new Regression(augMatrix);
                                    Matrix coefficients = reg.calculateRegression();

                                    System.out.println("Persamaan regresi adalah:");
                                    reg.printRegressionEquation(coefficients);

                                    double y_estimate = Regression.estimateY(coefficients, estimateMatrix);
                                    System.out.println("Nilai taksiran y adalah: " + y_estimate);

                                } catch (FileNotFoundException e) {
                                    System.err.println("File tidak ditemukan: " + e.getMessage());
                                }
                                break;

                        
                    }


                    break;
                case 6:
                    // bicubic
                    
                    break;
                case 7:
                    // resize image
                    System.out.println("Menu:");
                    System.out.println("1. Resize Gambar");
                    System.out.println("2. Keluar");
                    System.out.print("Pilih menu (1/2): ");
                    int resizeImageChoice = userInput.nextInt();
                    userInput.nextLine();

                    switch (resizeImageChoice) {
                        case 1:
                            System.out.print("Masukkan letak file gambar (misal: input.png): ");
                            String inputFileName = userInput.nextLine();

                            System.out.print("Masukkan faktor perbesaran (misal: 2.0): ");
                            double factor = userInput.nextDouble();

                            System.out.print("Masukkan letak dan nama file hasil resize (misal: output.png): ");
                            userInput.nextLine();
                            String outputFileName = userInput.nextLine();
                            Bicubic.prepare();
                            Resizeimage resizer = new resizeimage.Resizeimage();
                            resizer.load(inputFileName);
                            resizer.resize(factor, outputFileName);

                            System.out.println("Gambar berhasil diresize dan disimpan sebagai " + outputFileName);
                            break;

                        case 2:
                            
                            break;
                    }

                    break;
                case 8:
                    System.out.println("Keluar dari program.");
                    userInput.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih lagi.");
            }
        }

    }

}
