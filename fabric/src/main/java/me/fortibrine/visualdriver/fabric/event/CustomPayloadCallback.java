package me.fortibrine.visualdriver.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface CustomPayloadCallback {
    Event<CustomPayloadCallback> EVENT = EventFactory.createArrayBacked(
            CustomPayloadCallback.class,
            (listeners) -> (identifier, byteBuf) -> {
                for (CustomPayloadCallback listener : listeners) {
                    listener.payload(identifier, byteBuf);
                }
            }
    );

    void payload(ResourceLocation identifier, FriendlyByteBuf byteBuf);
}
