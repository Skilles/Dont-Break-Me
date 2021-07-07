package com.skilles.dontbreakme.config;

import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import net.minecraft.text.TranslatableText;

public class ConfigUtil {
    public static BooleanToggleBuilder getBooleanEntry(String name, boolean bool, boolean defaultValue, ConfigEntryBuilder entryBuilder) {
        return entryBuilder.startBooleanToggle(new TranslatableText("config.dontbreakme.mode."+name), bool)
                .setDefaultValue(defaultValue)
                .setTooltip(new TranslatableText("config.dontbreakme.mode."+name+".tooltip"));
    }
}
