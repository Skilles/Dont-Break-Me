package com.skilles.dontbreakme.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    public static boolean containsBlock(String blockName) {
        List<String> blockList = new ArrayList<>(ConfigPojo.generalGroup.blocks);
        if (!ConfigPojo.generalGroup.globalEnable) return false;

        // Set both list and blockname to lower case for comparison
        String lBlockName = blockName.toLowerCase();
        for (int i = 0, blockListSize = blockList.size(); i < blockListSize; i++) {
            String block = blockList.get(i);
            blockList.set(i, block.toLowerCase());
        }

        // If inverse is true, if the block is found then allow block breaking
        if (blockList.stream().anyMatch(lBlockName::contains)) return !ConfigPojo.generalGroup.inverse;
        
        /*for(String block : blockList) {
            if (blockName.contains(block)){
                return !ConfigPojo.generalGroup.inverse;
            }
        }*/
        
        // If the block is not found, deny breaking
        return ConfigPojo.generalGroup.inverse;
    }

}