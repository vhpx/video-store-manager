public class System {
    private System() {}
    private static System instance = null;

    public static System getInstance() {
        if (instance == null) instance = new System();
        return instance;
    }
    
    public static void main(String[] args) {
        // TODO: implement System.main
    }
}