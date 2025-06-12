package me.fortibrine.visualdriver.fabric.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GuiDataPacket(byte[] data) implements CustomPayload {
    public static final Id<GuiDataPacket> ID = new Id<>(Identifier.of("visualdriver", "gui"));

    public static final PacketCodec<RegistryByteBuf, GuiDataPacket> CODEC =
            PacketCodec.of(
                    (payload, buf) -> {
                        buf.writeBytes(payload.data());
                    },
                    buf -> {
                        byte[] remaining = new byte[buf.readableBytes()];
                        buf.readBytes(remaining);
                        return new GuiDataPacket(remaining);
                    }
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}