package me.fortibrine.visualdriver.fabric.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record WorldTextPayload(byte[] data) implements CustomPayload {
    public static final Id<WorldTextPayload> ID = new Id<>(Identifier.of("visualdriver", "text"));

    public static final PacketCodec<RegistryByteBuf, WorldTextPayload> CODEC =
            PacketCodec.of(
                    (payload, buf) -> {
                        buf.writeBytes(payload.data());
                    },
                    buf -> {
                        byte[] remaining = new byte[buf.readableBytes()];
                        buf.readBytes(remaining);
                        return new WorldTextPayload(remaining);
                    }
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}