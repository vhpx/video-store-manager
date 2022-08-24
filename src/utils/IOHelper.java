package utils;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class IOHelper {
    public static void writeToFile(String fileName, String data) {
        try {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(data);
            bw.close();

            System.out.println("Data written to file " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to file " + fileName);
        }
    }

    public static void appendToFile(String fileName, String data) {
        try {
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(data);
            bw.close();

            System.out.println("Data appended to file " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error appending to file " + fileName);
        }
    }

    public static void deleteFile(String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.close();

            System.out.println("File " + fileName + " deleted");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error deleting file " + fileName);
        }
    }

    public static void createFile(String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.close();

            System.out.println("File " + fileName + " created");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating file " + fileName);
        }
    }

    public static ArrayList<String> readFile(String fileName) {
        ArrayList<String> lines = new ArrayList<>();

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null)
                lines.add(line);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file " + fileName);
        }
        return lines;
    }

    public static String getProjectPath() {
        // Create a check file
        File check = new File("check.txt");

        // Get the project path
        String path = check.getAbsolutePath().replace("check.txt", "");

        // Delete the check file
        check.delete();

        return path;
    }
}
