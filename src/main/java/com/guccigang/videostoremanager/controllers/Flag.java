package com.guccigang.videostoremanager.controllers;

import com.guccigang.videostoremanager.items.Item;

public class Flag {
    static int check = 0;

    static Item item;

    static void setCheck(int num)
    {
        check = num;
    }
    static void setItem(Item newItem)
    {
        item = newItem;
    }
}
