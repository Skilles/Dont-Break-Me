package com.skilles.dontbreakme.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import static com.skilles.dontbreakme.config.ConfigPojo.*;
import static com.skilles.dontbreakme.config.ConfigUtil.*;

public class ConfigScreen {
    public static Screen getConfigScreen(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setDefaultBackgroundTexture(new Identifier("minecraft:textures/block/item_frame.png"))
                .setTitle(new TranslatableText("config.dontbreakme.title"));

        builder.setGlobalized(true);
        builder.setGlobalizedExpanded(false);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("config.dontbreakme.category.general"));
        general.addEntry(getBooleanEntry("globalenable", generalGroup.globalEnable, true, entryBuilder)
                .setSaveConsumer((value) -> { generalGroup.globalEnable = value; })
                .build());
        general.addEntry(getBooleanEntry("inverse", generalGroup.inverse, false, entryBuilder)
                .setSaveConsumer((value) -> { generalGroup.inverse = value; })
                .build());
        general.addEntry(entryBuilder.startTextDescription(new TranslatableText("config.dontbreakme.general.list.caption")).build());
        general.addEntry(entryBuilder.startStrList(new TranslatableText("config.dontbreakme.general.list"), generalGroup.blocks).build());

        builder.setSavingRunnable(ModConfig::saveConfig);

        return builder.build();
    }
}