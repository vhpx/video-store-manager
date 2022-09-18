package com.guccigang.videostoremanager.utils;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class IOHelper {
    private final static Scanner sc = new Scanner(System.in);

    public static String getProjectPath() {
        // Generate a random file name
        String fileName = "temp-" + System.currentTimeMillis();

        // Create a check file
        File check = new File(fileName);

        // Get the project path
        String path = check.getAbsolutePath().replace(fileName, "");

        // Delete the check file
        check.delete();

        return path;
    }

    public static ArrayList<String> readFile(String fileName) {
        // Get the path to store data
        String path = getProjectPath() + fileName;

        // Create a list to store the lines
        ArrayList<String> lines = new ArrayList<>();

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            // Read the file line by line
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

    public static void createFile(String fileName, ArrayList<String> lines) {
        // Get the path to store data
        String path = getProjectPath() + fileName;

        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            // Write the lines to the file
            for (String line : lines)
                bw.write(line + "\n");

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating file " + fileName);
        }
    }
}
