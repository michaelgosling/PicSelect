package me.michaelgosling.picselect.content;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.michaelgosling.picselect.R;

/**
 * Helper class for providing sample title for user interfaces created by
 * Android template wizards.
 */
public class Content {



    /**
     * An array of items
     */
    public static final List<Item> ITEMS = new ArrayList<Item>();

    /**
     * A map of items, by ID.
     */
    public static final Map<String, Item> ITEM_MAP = new HashMap<String, Item>();


    /**
     * Add items
     */
    static {

        addItem(new Item("1", R.string.adult_back_backpack, R.drawable.adult_back_backpack));
        addItem(new Item("2", R.string.bags_black_friday, R.drawable.bags_black_friday));
        addItem(new Item("3", R.string.dark_aurora, R.drawable.dark_aurora));
        addItem(new Item("4", R.string.pexels, R.drawable.pexels));
        addItem(new Item("5", R.string.wolfo, R.drawable.wolfo));
    }

    private static void addItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    /**
     * A dummy item representing a piece of title.
     */
    public static class Item {
        public final String id;
        public final int title;
        public final int resourceID;

        public String getItemId(){
            return id;
        }

        public Item(String id, int title, int resourceID) {
            this.id = id;
            this.title = title;
            this.resourceID = resourceID;
        }
    }
}
