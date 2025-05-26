package me.fortibrine.visualdriver.fabric.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record KeyC2SPayload(String key, int pressType, int modifierType) implements CustomPayload {
    public static final Id<KeyC2SPayload> ID = new Id<>(Identifier.of("visualdriver", "key"));

    public static final PacketCodec<RegistryByteBuf, KeyC2SPayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.STRING, KeyC2SPayload::key,
                    PacketCodecs.VAR_INT, KeyC2SPayload::pressType,
                    PacketCodecs.VAR_INT, KeyC2SPayload::modifierType,
                    KeyC2SPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}