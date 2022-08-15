package utils;

import java.util.Scanner;

public class Utilities {
    public String askInfo(String message) {
        System.out.print(message);

        Scanner input = new Scanner(System.in);
        String info = input.nextLine();

        // Close the scanner object to prevent resource leak
        input.close();
        return info;
    }
}
