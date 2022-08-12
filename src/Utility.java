import java.util.Scanner;

public class Utility {
    public String askInfo (String message)
    {
        System.out.print(message);
        return new Scanner(System.in).nextLine();
    }
}
