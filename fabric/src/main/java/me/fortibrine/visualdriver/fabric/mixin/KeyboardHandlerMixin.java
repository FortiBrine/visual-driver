package me.fortibrine.visualdriver.fabric.mixin;

import me.fortibrine.visualdriver.fabric.event.KeyInputCallback;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardHandlerMixin {

    @Inject(method = "onKey", at = @At("HEAD"))
    public void onKeyPress(long window, int keyCode, int scanCode, int arg3, int modifiers, CallbackInfo info) {
        if (MinecraftClient.getInstance().getWindow().getHandle() != window) return;

        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null) return;

        InputUtil.Key key = InputUtil.fromKeyCode(keyCode, scanCode);

        KeyInputCallback.EVENT.invoker().keyPress(key, arg3, modifiers, info);
    }

}
