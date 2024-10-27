package me.fortibrine.visualdriver.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface CustomPayloadCallback {
    Event<CustomPayloadCallback> EVENT = EventFactory.createArrayBacked(
            CustomPayloadCallback.class,
            (listeners) -> (identifier, byteBuf, info) -> {
                for (CustomPayloadCallback listener : listeners) {
                    listener.payload(identifier, byteBuf, info);
                }
            }
    );

    void payload(ResourceLocation identifier, FriendlyByteBuf byteBuf, CallbackInfo info);
}
