package com.skilles.dontbreakme.config;

import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains values for config fields
 */
public class ConfigPojo {
    @Setting.Group
    public static GeneralGroup generalGroup = new GeneralGroup();

    public static class GeneralGroup {
        @Setting(comment = "Global enable")
        public boolean globalEnable = true;
        @Setting(comment = "Inverse mode")
        public boolean mode = false;
        @Setting(comment = "Blocks to Allow")
        public List<String> whitelist = new ArrayList<>();
        @Setting(comment = "Blocks to Ignore")
        public List<String> blacklist = new ArrayList<>();
    }
}
