package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Savetofile {
    public static void saveResultToFile(String result, String folderName) {
        try {
            File resultFolder = new File(folderName);
            
            // kalo foldernya tidak ada
            if (!resultFolder.exists()) {
                resultFolder.mkdir();
            }

            // create file
            String fileName = "result_" + System.currentTimeMillis() + ".txt";
            File outputFile = new File(resultFolder, fileName);

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            // tulis isi file
            writer.write(result);
            writer.close();

        } catch (IOException e) {
            System.err.println("Error saving result to file: " + e.getMessage());
        }
    }
}
