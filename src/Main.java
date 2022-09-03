import core.Application;

public class Main {
    public static void main(String[] args) {
        // Create a new instance of the Application class
        Application app = Application.getInstance();

        // Start the application
        app.start();

        // Execute application loop
        app.execute();
    }
}
