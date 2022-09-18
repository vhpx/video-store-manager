package com.guccigang.videostoremanager.items;


import com.guccigang.videostoremanager.core.Constants;
import com.guccigang.videostoremanager.core.Manager;
import com.guccigang.videostoremanager.errors.ItemException;
import com.guccigang.videostoremanager.utils.ItemIO;
import com.guccigang.videostoremanager.utils.ItemUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public ArrayList<Item> sort(boolean type) {
        var result = this.getAll();
        if (type)
            Collections.sort(result,new ItemIDComparator());
        else
            Collections.sort(result,new ItemTitleComparator());
        return result;
    }


    public ArrayList<Item> searchItem(String input) {
        var result = new ArrayList<Item>();

        for (Item a : getAll())
            if (input.equals(a.getId()) || input.equals(a.getTitle()))
                result.add(a);

        return result;
    }
}
class ItemIDComparator implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        if (o2.getId().compareTo(o2.getId()) == 1)
            return 1;
        return -1;
    }
}
class ItemTitleComparator implements Comparator <Item>{
    @Override
    public int compare(Item o1, Item o2) {
        if (o2.getTitle().compareTo(o2.getTitle()) == 1)
            return 1;
        if (o2.getTitle().compareTo(o2.getTitle()) == -1)
            return -1;
        return 0;
    }
}
