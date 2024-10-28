package me.fortibrine.visualdriver.fabric.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
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

    void keyPress(InputConstants.Key key, int arg0, int modifiers, CallbackInfo info);
}
