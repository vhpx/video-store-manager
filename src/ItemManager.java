public class ItemManager {
    private ItemManager() {}
    private static ItemManager instance = null;

    public static ItemManager getInstance() {
        if (instance == null) instance = new ItemManager();
        return instance;
    }
}
