package com.skilles.dontbreakme.config;

import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.AnnotatedSettings;
import io.github.fablabsmc.fablabs.api.fiber.v1.annotation.SettingNamingConvention;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.FiberException;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    /*FiberId someIdentifier = new FiberId("some", "identifier");

    @Setting.Group
    GuiGroup gui = new GuiGroup();

    private static class GuiGroup {
        public @Setting.Constrain.Range(min = 0, max = 1, step = 0.1) float opacity = 1f;
    }*/
    public static boolean isConfigLoaded = false;
    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("dontbreakme.json5");
    private static final AnnotatedSettings ANNOTATED_SETTINGS = AnnotatedSettings.builder()
            .useNamingConvention(SettingNamingConvention.SNAKE_CASE)
            .build();
    private static final ConfigPojo POJO = new ConfigPojo();
    public static final ConfigTree TREE = ConfigTree.builder()
            .applyFromPojo(POJO, ANNOTATED_SETTINGS)
            .build();

    private static JanksonValueSerializer serializer = new JanksonValueSerializer(false);

    public static void loadModConfig() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                ANNOTATED_SETTINGS.applyToNode(TREE, POJO);
                FiberSerialization.deserialize(TREE, Files.newInputStream(CONFIG_PATH), serializer);
                isConfigLoaded = true;
            } catch (IOException | FiberException e) {
                e.printStackTrace();
            }
        } else {
            saveConfig();
            isConfigLoaded = true;
        }
    }

    public static void saveConfig() {
        try {
            ANNOTATED_SETTINGS.applyToNode(TREE, POJO);
            FiberSerialization.serialize(TREE, Files.newOutputStream(CONFIG_PATH), serializer);
        } catch (IOException | FiberException e) {
            e.printStackTrace();
        }
    }
}
