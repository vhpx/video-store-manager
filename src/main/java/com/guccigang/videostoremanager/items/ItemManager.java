package com.guccigang.videostoremanager.items;

import com.guccigang.videostoremanager.core.Constants;
import com.guccigang.videostoremanager.core.Manager;
import com.guccigang.videostoremanager.errors.ItemException;
import com.guccigang.videostoremanager.utils.ItemIO;
import com.guccigang.videostoremanager.utils.ItemUtils;

public class ItemManager extends Manager<Item> {
    public void initialize() {
        // Initialize item I/O helper
        var itemIO = new ItemIO(this, new ItemUtils());
        var fileName = Constants.getItemsFileName();

        // Set the manager's I/O helper and file name to operate on
        setIO(itemIO, fileName);

        // Load the items from the local storage
        load();
    }

    public void stop() {
        // Save the items to the local storage
        save();
    }

    public void increaseStock(Item item) {
        item.increaseStock();
    }

    public void increaseStock(Item item, int n) throws ItemException {
        item.increaseStock(n);
    }

    public void decreaseStock(Item item) throws ItemException {
        item.decreaseStock();
    }

    public void decreaseStock(Item item, int n) throws ItemException {
        item.decreaseStock(n);
    }
}
