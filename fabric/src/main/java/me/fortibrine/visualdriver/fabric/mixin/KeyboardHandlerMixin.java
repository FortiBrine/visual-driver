package me.fortibrine.visualdriver.fabric.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import me.fortibrine.visualdriver.fabric.event.KeyPressCallback;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    @Inject(method = "keyPress", at = @At("HEAD"))
    public void onKeyPress(long window, int keyCode, int scanCode, int arg3, int modifiers, CallbackInfo info) {
        if (Minecraft.getInstance().getWindow().getWindow() != window) return;

        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) return;

        InputConstants.Key key = InputConstants.getKey(keyCode, scanCode);

        KeyPressCallback.EVENT.invoker().keyPress(key, arg3, modifiers, info);
    }

}
