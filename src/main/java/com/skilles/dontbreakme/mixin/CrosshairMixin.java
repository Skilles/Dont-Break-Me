package com.skilles.dontbreakme.mixin;

import com.skilles.dontbreakme.Dontbreakme;
import com.skilles.dontbreakme.config.ConfigManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class CrosshairMixin extends DrawableHelper {

    @Shadow
    private
    int scaledWidth;
    @Shadow
    private
    int scaledHeight;

    @Inject(method = "renderCrosshair(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), cancellable = true)
    private void injectCrosshair(MatrixStack matrices, CallbackInfo ci) {
        if (shouldMask()) {
            this.drawTexture(matrices, (this.scaledWidth - 3) / 2, (this.scaledHeight - 3) / 2, 6, 6, 3, 3);
            //this.drawTexture(matrices, (this.scaledWidth - 9) / 2, (this.scaledHeight - 9) / 2, 25, 18, 9, 9);
            ci.cancel();
        }
    }

    @Unique
    private static boolean shouldMask() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!client.player.isCreative() && !Dontbreakme.bypass) {
            if (client.crosshairTarget != null && client.crosshairTarget.getType() == net.minecraft.util.hit.HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult) client.crosshairTarget;
                BlockPos blockPos = blockHitResult.getBlockPos();
                return ConfigManager.containsBlock(client.world.getBlockState(blockPos).getBlock().getName().getString());
            }
        }
        return false;
    }
}
