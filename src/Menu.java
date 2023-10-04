import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.sound.midi.MidiSystem;

import bicubic.Bicubic;
import linearalgebra.LinearSystem;
import linearalgebra.Matrix;
import linearalgebra.Solution;
import polynomialinterpolation.PolynomialInterpolation;
import regression.Regression;
import resizeimage.Resizeimage;
import utils.Savetofile;

public class Menu {
    public static void solveLinearSystem(Scanner userInput) {
        while (true) {
            System.out.println("Metode:");
            System.out.println("1. Metode Gauss");
            System.out.println("2. Metode Gauss-Jordan");
            System.out.println("3. Metode Matriks balikan");
            System.out.println("4. Metode Cramer");
            System.out.println("5. Kembali");
            System.out.print("Pilih metode: ");

            int linearSubMenuChoice = userInput.nextInt();
            System.out.println();

            while (linearSubMenuChoice > 5 || linearSubMenuChoice < 1) {
                System.out.print("Pilihan tidak valid. Silakan pilih lagi: ");
                linearSubMenuChoice = userInput.nextInt();
                System.out.println();
            }

            if (linearSubMenuChoice == 5) {
                System.out.println();
                break;
            }

            System.out.println("1. Input dari keyboard");
            System.out.println("2. Input dari file (.txt)");
            System.out.println("3. Kembali");
            System.out.print("Pilih jenis input: ");
            int inputChoice = userInput.nextInt();

            while (inputChoice > 4 || inputChoice < 1) {
                System.out.print("Pilihan tidak valid. Silakan pilih lagi: ");
                linearSubMenuChoice = userInput.nextInt();
                System.out.println();
            }

            LinearSystem ls = null;
            Solution solution = null;

            if (inputChoice == 1) {
                Matrix augMatrix = Matrix.getInputMatrixFromUser(userInput);
                ls = new LinearSystem(augMatrix);
            } else if (inputChoice == 2) {
                System.out.print("Masukkan nama file input: ");
                userInput.nextLine(); // Membersihkan newline yang tersisa di dalam buffer
                String inputFileName = userInput.nextLine();

                try {
                    Matrix augMatrix_file = Matrix.getInputMatrixFromFile(inputFileName);
                    ls = new LinearSystem(augMatrix_file);
                } catch (FileNotFoundException e) {
                    System.err.println("File tidak ditemukan: " + e.getMessage());
                    continue;
                }

            } else if (inputChoice == 3) {
                System.out.println();
                break;
            } else {
                System.out.println("Pilihan tidak valid. Silakan pilih lagi.");
                continue;
            }

            if (linearSubMenuChoice == 1) {
                solution = ls.gauss();
                solution.print();
            } else if (linearSubMenuChoice == 2) {
                solution = ls.gaussJordan();
                solution.print();
            } else if (linearSubMenuChoice == 3) {
                solution = ls.solveInverse();
                solution.print();
            } else if (linearSubMenuChoice == 4) {
                solution = ls.cramer();
                solution.print();
            }

            String resultString = solution.toString();

            String resultFolderName = "src/test/output";
            Savetofile.saveResultToFile(resultString, resultFolderName);

        }
    }

    public static void solveDeterminant(Scanner userInput) {
        while (true) {
            System.out.println("Metode:");
            System.out.println("1. Metode Kofaktor");
            System.out.println("2. Metode Reduksi");
            System.out.println("3. Kembali");
            System.out.print("Pilih metode: ");
            int determinantSubMenuChoice = userInput.nextInt();
            System.out.println();

            while (determinantSubMenuChoice > 3 || determinantSubMenuChoice < 1) {
                System.out.print("Pilihan tidak valid. Silakan pilih lagi: ");
                determinantSubMenuChoice = userInput.nextInt();
                System.out.println();
            }

            if (determinantSubMenuChoice == 3) {
                System.out.println();
                break;
            }

            System.out.println("1. Input dari keyboard");
            System.out.println("2. Input dari file (.txt)");
            System.out.println("3. Kembali");
            System.out.print("Pilih jenis input: ");
            int inputChoice = userInput.nextInt();
            System.out.println();

            while (inputChoice > 3 || inputChoice < 1) {
                System.out.print("Pilihan tidak valid. Silakan pilih lagi: ");
                determinantSubMenuChoice = userInput.nextInt();
                System.out.println();
            }

            if (inputChoice == 3) {
                System.out.println();
                break;
            }

            Matrix augMatrix = null;
            if (inputChoice == 1) {
                augMatrix = Matrix.getInputMatrixFromUser(userInput);
            } else if (inputChoice == 2) {
                System.out.print("Masukkan nama file input: ");
                userInput.nextLine();
                String inputFileName = userInput.nextLine();

                try {
                    augMatrix = Matrix.getInputMatrixFromFile(inputFileName);
                } catch (FileNotFoundException e) {
                    System.err.println("File tidak ditemukan: " + e.getMessage());
                    continue;
                }

            } else if (inputChoice == 3) {
                break;
            }

            Solution detAugMatrix = null;
            if (determinantSubMenuChoice == 1) {
                detAugMatrix = augMatrix.determinantByCofactor();
            } else if (determinantSubMenuChoice == 2) {
                detAugMatrix = augMatrix.determinantByReduction();
            }

            detAugMatrix.print();
            Savetofile.saveResultToFile(detAugMatrix.toString(), "src/test/output");
        }

    }

    public static void solveInverse(Scanner userInput) {
        while (true) {
            System.out.println("Metode:");
            System.out.println("1. Metode Adjoint (harus matriks persegi)");
            System.out.println("2. Metode OBE");
            System.out.println("3. Kembali");
            System.out.print("Pilih metode: ");
            int inverseSubMenuChoice = userInput.nextInt();
            System.out.println();

            while (inverseSubMenuChoice > 3 || inverseSubMenuChoice < 1) {
                System.out.print("Pilihan tidak valid. Silakan pilih lagi: ");
                inverseSubMenuChoice = userInput.nextInt();
                System.out.println();
            }

            if (inverseSubMenuChoice == 3) {
                System.out.println();
                break;
            }

            System.out.println("1. Input dari keyboard");
            System.out.println("2. Input dari file (.txt)");
            System.out.println("3. Kembali");
            System.out.print("Pilih jenis input: ");

            int inputChoice = userInput.nextInt();
            System.out.println();

            while (inputChoice > 3 || inputChoice < 1) {
                System.out.print("Pilihan tidak valid. Silakan pilih lagi: ");
                inputChoice = userInput.nextInt();
                System.out.println();
            }

            if (inputChoice == 3) {
                System.out.println();
                break;
            }

            Matrix augMatrix = null;
            if (inputChoice == 1) {
                augMatrix = Matrix.getInputMatrixFromUser(userInput);
            } else if (inputChoice == 2) {
                System.out.print("Masukkan nama file input: ");
                userInput.nextLine(); // Membersihkan newline yang tersisa di dalam buffer
                String inputFileName = userInput.nextLine();

                try {
                    augMatrix = Matrix.getInputMatrixFromFile(inputFileName);
                } catch (FileNotFoundException e) {
                    System.err.println("File tidak ditemukan: " + e.getMessage());
                    continue;
                }
                System.out.println();

            }

            Solution solution = null;
            if (inverseSubMenuChoice == 1) {
                solution = augMatrix.inverseByAdjoint();
            } else if (inverseSubMenuChoice == 2) {
                solution = augMatrix.inverse();
            }

            String resultString = solution.toString();
            String resultFolderName = "src/test/output";
            Savetofile.saveResultToFile(resultString, resultFolderName);
            solution.print();
        }
    }

    public static void solvePolynomial(Scanner userInput) {
        while(true){
            System.out.println("1. Input dari keyboard");
            System.out.println("2. Input dari file (.txt)");
            System.out.println("3. Kembali");
            System.out.print("Pilih jenis input: ");
            int inputChoice = userInput.nextInt();
            System.out.println();
            if(inputChoice ==1){
                System.out.println("Masukan nilai n: ");
                int n = userInput.nextInt();
                double x,y;
                PolynomialInterpolation solverPolynom = new PolynomialInterpolation();
                for(int i=0;i<n;i++){
                    x = userInput.nextDouble();
                    y = userInput.nextDouble();
                    solverPolynom.addPoint(x,y);
                }
                double val = userInput.nextDouble();
                solverPolynom.run();
                Solution solution = solverPolynom.approximate(val);
                solution.print();
            }
            else if(inputChoice==2){
                System.out.print("Masukan lokasi file(input.txt): ");
                PolynomialInterpolation solverPolynom = new PolynomialInterpolation();
                userInput.nextLine();
                double a,b;
                String fileName = userInput.nextLine();
                try {
                File inputFile = new File(fileName);
                Scanner fileInput = new Scanner(new FileInputStream(inputFile));
                while(fileInput.hasNextLine()){
                    String line =  fileInput.nextLine();
                    String[] elements = line.trim().split("\\s+");
                    a = Double.parseDouble(elements[0]);
                    b = Double.parseDouble(elements[1]);
                    solverPolynom.addPoint(a, b);
                    }   
                    String lastLine = fileInput.nextLine();
                    String[] element = lastLine.trim().split("\\s+");
                    double x = Double.parseDouble(element[0]);
                    fileInput.close();
                    solverPolynom.run();
                    Solution solution = solverPolynom.approximate(x);
                    solution.print();

                }catch(FileNotFoundException e){
                    System.err.println("File tidak ditemukan " + e.getMessage());
                }
            }
    }
}

    public static void solveMultipleLinearRegression(Scanner userInput) {
        while (true) {
            System.out.println("1. Input dari keyboard");
            System.out.println("2. Input dari file (.txt)");
            System.out.println("3. Kembali");
            System.out.print("Pilih jenis input: ");
            int inputChoice = userInput.nextInt();
            System.out.println();

            if (inputChoice == 1) {
                System.out.println("Masukkan matriks nya (x1,x2,..,xn) dan y_i:");
                int row_count, col_count;
                System.out.println("Jumlah baris:");
                row_count = userInput.nextInt();
                System.out.println("Jumlah kolom:");
                col_count = userInput.nextInt();

                System.out.println("Masukkan matriksnya:");
                Matrix augMatrix = new Matrix(row_count, col_count);
                for (int i = 0; i < row_count; ++i) {
                    for (int j = 0; j < col_count; ++j) {
                        augMatrix.matrix[i][j] = userInput.nextDouble();
                    }
                }

                System.out.println("Masukkan matriks (x1,x2,..,xn) yang ingin ditaksir nilai y nya:");

                int col_count_est = col_count - 1;
                Matrix estimateMatrix = new Matrix(col_count_est, 1);
                for (int i = 0; i < col_count_est; i++) {
                    for (int j = 0; j < 1; j++) {
                        estimateMatrix.matrix[i][j] = userInput.nextDouble();
                    }
                }

                Regression reg = new Regression(augMatrix);
                Matrix coefficients = reg.calculateRegression();

                String regressionEquation = reg.getRegressionEquation(coefficients);

                // Print the equation
                System.out.println("Persamaan regresi adalah:");
                System.out.println(regressionEquation);

                double y_estimate = Regression.estimateY(coefficients, estimateMatrix);
                System.out.println("Nilai taksiran y adalah: " + y_estimate + "\n");

                // Save the equation to a file
                Savetofile.saveResultToFile("Persamaan regresi adalah: " + regressionEquation + "\n"
                        + "Nilai taksiran y adalah: " + y_estimate, "src/test/output");

                break;
            }

            else if (inputChoice == 2) {
                System.out.print("Masukkan nama file input: ");
                userInput.nextLine(); // Membersihkan newline yang tersisa di dalam buffer
                String inputFileName = userInput.nextLine();

                try {
                    Matrix augMatrix = Matrix.getInputMatrixFromFile(inputFileName);

                    // Display the matrix read from the file
                    System.out.println("Matriks dari file " + inputFileName + ":");
                    System.out.println(augMatrix.toString());

                    System.out.println("Masukkan matriks (x1,x2,..,xn) yang ingin ditaksir nilai y nya:");

                    int col_count_est = augMatrix.col() - 1;
                    Matrix estimateMatrix = new Matrix(col_count_est, 1);
                    for (int i = 0; i < col_count_est; i++) {
                        for (int j = 0; j < 1; j++) {
                            estimateMatrix.matrix[i][j] = userInput.nextDouble();
                        }
                    }

                    Regression reg = new Regression(augMatrix);
                    Matrix coefficients = reg.calculateRegression();

                    String regressionEquation = reg.getRegressionEquation(coefficients);

                    // Print the equation
                    System.out.println("Persamaan regresi adalah:");
                    System.out.println(regressionEquation);

                    double y_estimate = Regression.estimateY(coefficients, estimateMatrix);
                    System.out.println("Nilai taksiran y adalah: " + y_estimate + "\n");

                    // Save the equation to a file
                    Savetofile.saveResultToFile("Persamaan regresi adalah: " + regressionEquation + "\n"
                            + "Nilai taksiran y adalah: " + y_estimate, "src/test/output");

                    break;
                } catch (FileNotFoundException e) {
                    System.err.println("File tidak ditemukan: " + e.getMessage());
                    continue;
                }
            }

            else {
                break;
            }

        }
    }

    public static void solveBicubicSplineInterpolation(Scanner userInput) {
        while (true) {
            System.out.print("Masukan lokasi file input: ");
            userInput.nextLine();

            String fileName = userInput.nextLine();
            try {
                File inputFile = new File(fileName);
                Scanner fileInput = new Scanner(new FileInputStream(inputFile));

                Matrix matrixData = new Matrix(4, 4);
                int row = 0;
                while (fileInput.hasNextLine() && row < 4) {
                    String line = fileInput.nextLine();
                    String[] elements = line.trim().split("\\s+");
                    for (int col = 0; col < Math.min(elements.length, 4); col++) {
                        matrixData.matrix[row][col] = Double.parseDouble(elements[col]);
                    }
                    row++;
                }
                Matrix matrixCoeff = new Matrix(16, 1);
                int index = 0;
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        matrixCoeff.matrix[index][0] = matrixData.matrix[i][j];
                        index++;
                    }
                }
                double a, b;
                String lastline = fileInput.nextLine();
                fileInput.close();
                String[] elementss = lastline.trim().split("\\s+");
                a = Double.parseDouble(elementss[0]);
                b = Double.parseDouble(elementss[1]);
                Bicubic.prepare();
                double result = Bicubic.approximate(matrixCoeff, a, b);
                System.out.println("Aproksimasinya: " + result);
            } catch (FileNotFoundException e) {
                System.err.println("File tidak ditemukan " + e.getMessage());
            }
            break;
        }
    }

    public static void solveResizeImage(Scanner userInput) {
        while (true) {
            System.out.println("1. Resize Gambar");
            System.out.println("2. Keluar");
            System.out.print("Pilih menu: ");
            int resizeImageChoice = userInput.nextInt();
            userInput.nextLine();
            System.out.println();

            if (resizeImageChoice == 1) {
                System.out.print("Masukkan letak file gambar: ");
                String inputFileName = userInput.nextLine();

                System.out.print("Masukkan faktor perbesaran: ");
                double factor = userInput.nextDouble();

                System.out.print("Masukkan letak dan nama file hasil resize: ");
                userInput.nextLine();
                String outputFileName = userInput.nextLine();
                Bicubic.prepare();
                Resizeimage resizer = new resizeimage.Resizeimage();
                resizer.load(inputFileName);
                resizer.resize(factor, outputFileName);
                System.out.println("Gambar berhasil diresize dan disimpan sebagai " + outputFileName);
                break;
            } else {
                break;
            }

        }
    }

}
