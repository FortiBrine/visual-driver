package me.fortibrine.visualdriver.fabric.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record GuiPayload(byte[] data) implements CustomPayload {
    public static final Id<GuiPayload> ID = new Id<>(Identifier.of("visualdriver", "gui"));

    public static final PacketCodec<RegistryByteBuf, GuiPayload> CODEC =
            PacketCodec.of(
                    (payload, buf) -> {
                        buf.writeBytes(payload.data());
                    },
                    buf -> {
                        byte[] remaining = new byte[buf.readableBytes()];
                        buf.readBytes(remaining);
                        return new GuiPayload(remaining);
                    }
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}