package edu.tcnj.hacktcnj2015;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameList {

    public static List<GameData> ITEMS = new ArrayList<GameData>();

    public static Map<String, GameData> ITEM_MAP = new HashMap<String, GameData>();


    public static void clear() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static void addItem(GameData item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class GameData {
        public String id;
        public String content;

        public GameData(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
