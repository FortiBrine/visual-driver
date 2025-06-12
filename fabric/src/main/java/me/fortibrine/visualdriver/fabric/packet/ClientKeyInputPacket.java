package me.fortibrine.visualdriver.fabric.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ClientKeyInputPacket(String key, int pressType, int modifierType) implements CustomPayload {
    public static final Id<ClientKeyInputPacket> ID = new Id<>(Identifier.of("visualdriver", "key"));

    public static final PacketCodec<RegistryByteBuf, ClientKeyInputPacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, ClientKeyInputPacket::key,
                    PacketCodecs.VAR_INT, ClientKeyInputPacket::pressType,
                    PacketCodecs.VAR_INT, ClientKeyInputPacket::modifierType,
                    ClientKeyInputPacket::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}