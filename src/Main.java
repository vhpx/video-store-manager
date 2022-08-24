import core.Application;

public class Main {
    public static void main(String[] args) {
        // * Application entry point
        // Create a new instance of the Application class
        Application app = Application.getInstance();

        // Start the application
        app.initialize();

        // Execute application loop
        app.execute();
    }
}
