package com.skilles.dontbreakme.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    public static boolean containsBlock(String blockName) {
        if (!ConfigPojo.generalGroup.globalEnable) return false;

        List<String> blockList;
        if (ConfigPojo.generalGroup.mode) { // Whitelist
            blockList = new ArrayList<>(ConfigPojo.generalGroup.whitelist);
        } else {
            blockList = new ArrayList<>(ConfigPojo.generalGroup.blacklist);
        }
        // Set both list and blockname to lower case for comparison
        String lBlockName = blockName.toLowerCase();
        for (int i = 0, blockListSize = blockList.size(); i < blockListSize; i++) {
            String block = blockList.get(i);
            // Check for blank lines
            if (block.equals("")) {
                blockList.remove(i);
            } else {
                blockList.set(i, block.toLowerCase());
            }
        }
        // If whitelist, if the block is found then allow block breaking
        if (blockList.stream().anyMatch(lBlockName::contains)) return !ConfigPojo.generalGroup.mode;

        // If the block is not found, deny breaking
        return ConfigPojo.generalGroup.mode;
    }

}