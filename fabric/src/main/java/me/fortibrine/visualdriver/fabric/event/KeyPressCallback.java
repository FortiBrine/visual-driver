package me.fortibrine.visualdriver.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface KeyPressCallback {
    Event<KeyPressCallback> EVENT = EventFactory.createArrayBacked(
            KeyPressCallback.class,
            (listeners) -> (key, arg0, modifiers, info) -> {
                for (KeyPressCallback listener : listeners) {
                    listener.keyPress(key, arg0, modifiers, info);
                }
            }
    );

    void keyPress(InputUtil.Key key, int arg0, int modifiers, CallbackInfo info);
}
