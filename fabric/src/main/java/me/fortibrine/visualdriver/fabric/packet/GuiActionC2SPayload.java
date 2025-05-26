package me.fortibrine.visualdriver.fabric.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GuiActionC2SPayload(String key, String menuId, int widgetIndex) implements CustomPayload {
    public static final Id<GuiActionC2SPayload> ID = new Id<>(Identifier.of("visualdriver", "gui"));

    public static final PacketCodec<RegistryByteBuf, GuiActionC2SPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, GuiActionC2SPayload::key,
                    PacketCodecs.STRING, GuiActionC2SPayload::menuId,
                    PacketCodecs.VAR_INT, GuiActionC2SPayload::widgetIndex,
                    GuiActionC2SPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}