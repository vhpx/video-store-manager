import java.io.IOException;

public class Application {
    private Application() {
    }

    private static Application instance = null;

    public static Application getInstance() {
        if (instance == null)
            instance = new Application();
        return instance;
    }

    public static AuthManager auth = AuthManager.getInstance();
    public static InternalManager internal = InternalManager.getInstance();

    public void run() {
        System.out.println("Howdy!");
    }

    public static void main(String[] args) throws IOException {
        Application app = Application.getInstance();
        app.run();

        Application.internal.items.loadData();
        Application.internal.items.deleteItem();
        Application.internal.items.displayAll();
    }
}