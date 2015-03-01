package edu.tcnj.hacktcnj2015.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class GameContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<GameItem> ITEMS = new ArrayList<GameItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, GameItem> ITEM_MAP = new HashMap<String, GameItem>();


    public static void clear() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static void addItem(GameItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class GameItem {
        public String id;
        public String content;

        public GameItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
