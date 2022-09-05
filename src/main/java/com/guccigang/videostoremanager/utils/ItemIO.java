package com.guccigang.videostoremanager.utils;

import com.guccigang.videostoremanager.core.Manager;

import com.guccigang.videostoremanager.items.Item;

public class ItemIO extends ObjectIO<Item> {
    public ItemIO(Manager<Item> manager, ItemUtils utils) {
        super(manager, utils, "items");
    }
}
