package me.fortibrine.visualdriver.fabric.listener;

import me.fortibrine.visualdriver.fabric.event.KeyPressCallback;
import me.fortibrine.visualdriver.fabric.packet.KeyC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class KeyListener implements KeyPressCallback {

    public KeyListener() {
        KeyPressCallback.EVENT.register(this);
    }

    @Override
    public void keyPress(InputUtil.Key key, int arg0, int modifiers, CallbackInfo info) {
        if (MinecraftClient.getInstance().getCurrentServerEntry() != null) {
            ClientPlayNetworking.send(new KeyC2SPayload(key.getTranslationKey(), arg0, modifiers));
        }

    }
}
