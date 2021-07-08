package com.skilles.dontbreakme.config;

import com.mojang.datafixers.FunctionType;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
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
        general.addEntry(getBooleanEntry("mode", generalGroup.mode, false, entryBuilder)
                .setSaveConsumer((value) -> { generalGroup.mode = value; })
                .setYesNoTextSupplier((FunctionType<Boolean, Text>) aBoolean -> {
                    String string;
                    if (aBoolean) {
                        string = "Whitelist";
                    } else {
                        string = "Blacklist";
                    }
                    return new LiteralText(string);
                })
                .build());
        general.addEntry(entryBuilder.startTextDescription(new TranslatableText("config.dontbreakme.general.list.caption")).build());
        general.addEntry(entryBuilder.startStrList(new TranslatableText("config.dontbreakme.general.whitelist"), generalGroup.whitelist)
                .setTooltip(new TranslatableText("config.dontbreakme.general.whitelist.tooltip"))
                .setSaveConsumer(value -> { generalGroup.whitelist = value; })
                .build());
        general.addEntry(entryBuilder.startStrList(new TranslatableText("config.dontbreakme.general.blacklist"), generalGroup.blacklist)
                .setTooltip(new TranslatableText("config.dontbreakme.general.blacklist.tooltip"))
                .setSaveConsumer(value -> { generalGroup.blacklist = value; })
                .build());
        builder.setSavingRunnable(ModConfig::saveConfig);

        return builder.build();
    }
}