package me.fortibrine.visualdriver.fabric.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface TitleScreenRenderCallback {
    Event<TitleScreenRenderCallback> EVENT = EventFactory.createArrayBacked(
            TitleScreenRenderCallback.class,
            (listeners) -> (screen, stack, info) -> {
                for (TitleScreenRenderCallback listener : listeners) {
                    listener.render(screen, stack, info);
                }
            }
    );

    void render(TitleScreen screen, PoseStack stack, CallbackInfo info);
}
