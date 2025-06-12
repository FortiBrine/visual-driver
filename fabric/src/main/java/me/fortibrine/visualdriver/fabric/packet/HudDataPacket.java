package me.fortibrine.visualdriver.fabric.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record HudDataPacket(byte[] data) implements CustomPayload {
    public static final CustomPayload.Id<HudDataPacket> ID = new CustomPayload.Id<>(Identifier.of("visualdriver", "hud"));

    public static final PacketCodec<RegistryByteBuf, HudDataPacket> CODEC =
            PacketCodec.of(
                    (payload, buf) -> {
                        buf.writeBytes(payload.data());
                    },
                    buf -> {
                        byte[] remaining = new byte[buf.readableBytes()];
                        buf.readBytes(remaining);
                        return new HudDataPacket(remaining);
                    }
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}