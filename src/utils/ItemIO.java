package utils;

import java.util.ArrayList;

import items.Item;
import items.ItemManager;

public class ItemIO extends ObjectIO<ItemManager> {
    public ItemIO(ItemManager manager) {
        super(manager);
    }

    public void loadData(String fileName) {
        ArrayList<String> lines = IOHelper.readFile(fileName);

        for (String line : lines) {
            Item item = ItemUtils.parse(line);
            manager.addItem(item);
        }

        System.out.println("Loaded " + lines.size() + " items from \"" + fileName + "\" file.");
    }

    public void saveData(String fileName) {
        ArrayList<Item> items = manager.getItems();
        ArrayList<String> lines = new ArrayList<>();

        for (Item item : items) {
            lines.add(ItemUtils.serialize(item));
        }

        IOHelper.createFile(fileName, lines);
        System.out.println("Saved " + items.size() + " items to \"" + fileName + "\" file.");
    }
}
