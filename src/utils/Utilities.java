package utils;

public class Utilities {
    public String askInfo(String message) {
        System.out.print(message);

        var sc = IOHelper.getScanner();
        String info = sc.nextLine();

        return info;
    }
}
