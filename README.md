# Tugas Besar 1 IF2123 Aljabar Linier dan Geometri 2023/2024

## Deskripsi
Repository ini adalah kalkulator matriks yang dibuat dalam bahasa Java untuk menentukan solusi sistem persamaan linear, determinan dan invers dari sebuah matriks, menyelesaikan persoalan interpolasi dan regresi linear. Kontributor:

1. Randy Verdian (13522067)
2. Emery Fathan Zwageri (13522079)
3. Azmi Mahmud Bazeid (13522109)

## Tools
- Java Language

## Fitur - fitur
- Menyelesaikan sistem persamaan linear dengan metode gauss, gauss-jordan, matriks balikan, dan cramer
- Menentukan determinan dengan metode kofaktor dan reduksi
- Menentukan invers matriks dengan metode adjoint dan OBE
- Melakukan interpolasi polinomial
- Melakukan regresi linear berganda
- Melakukan interpolasi bicubic spline
- Resize image dengan interpolasi bicubic spline

## Cara menjalankan program
1. Jika belum compile, pergi ke folder src dan ketik command: `javac -d ..\bin Application.java bicubic\*.java linearalgebra\*.java polynomialinterpolation\*.java regression\*.java resizeimage\*.java utils\*.java`
2. Jika sudah compile, pergi ke folder bin dan ketik command: `java Application`
