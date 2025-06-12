package me.fortibrine.visualdriver.fabric.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ClientGuiActionPacket(String key, String menuId, int widgetIndex) implements CustomPayload {
    public static final Id<ClientGuiActionPacket> ID = new Id<>(Identifier.of("visualdriver", "gui"));

    public static final PacketCodec<RegistryByteBuf, ClientGuiActionPacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, ClientGuiActionPacket::key,
                    PacketCodecs.STRING, ClientGuiActionPacket::menuId,
                    PacketCodecs.VAR_INT, ClientGuiActionPacket::widgetIndex,
                    ClientGuiActionPacket::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}