package utils;

import core.Manager;
import items.Item;

public class ItemIO extends ObjectIO<Item> {
    public ItemIO(Manager<Item> manager, ItemUtils utils) {
        super(manager, utils, "items");
    }
}
