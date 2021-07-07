package com.skilles.dontbreakme;

import com.skilles.dontbreakme.config.ConfigManager;
import com.skilles.dontbreakme.config.ConfigPojo;
import com.skilles.dontbreakme.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class Dontbreakme implements ClientModInitializer {

    public static boolean bypass = false;

    @Override
    public void onInitializeClient() {
        // Load the config
        ModConfig.loadModConfig();

        // Bypass keybinding
        KeyBinding bypassKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.dontbreakme.bypasskey", // The translation key of the keybinding's name
                InputUtil.Type.MOUSE, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_MOUSE_BUTTON_5, // The keycode of the key
                "category.dontbreakme.keybinds" // The translation key of the keybinding's category.
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> bypass = bypassKeybind.isPressed());

        // Prevent break if block is on the config
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (ConfigManager.containsBlock(world.getBlockState(pos).getBlock().getName().getString())
            && !player.isCreative() && !bypass) {
                return ActionResult.FAIL;
            } else {
                return ActionResult.PASS;
            }
        });
    }
}
