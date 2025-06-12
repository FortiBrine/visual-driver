package me.fortibrine.visualdriver.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface KeyInputCallback {
    Event<KeyInputCallback> EVENT = EventFactory.createArrayBacked(
            KeyInputCallback.class,
            (listeners) -> (key, arg0, modifiers, info) -> {
                for (KeyInputCallback listener : listeners) {
                    listener.keyPress(key, arg0, modifiers, info);
                }
            }
    );

    void keyPress(InputUtil.Key key, int arg0, int modifiers, CallbackInfo info);
}
