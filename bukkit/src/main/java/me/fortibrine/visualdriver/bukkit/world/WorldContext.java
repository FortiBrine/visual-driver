package me.fortibrine.visualdriver.bukkit.world;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WorldContext {

    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public WorldContext text(Player player, String id, String text, int x, int y, int z, int textColor, int backgroundColor, boolean rotation, float offsetX, float offsetY) {
        final JNetBuffer ldoinBuffer = new JNetBuffer(Unpooled.buffer());

        ldoinBuffer.writeString(id);
        ldoinBuffer.writeVarInt(x);
        ldoinBuffer.writeVarInt(y);
        ldoinBuffer.writeVarInt(z);
        ldoinBuffer.writeString(text);
        ldoinBuffer.writeVarInt(textColor);
        ldoinBuffer.writeVarInt(backgroundColor);
        ldoinBuffer.getBuf().writeBoolean(rotation);
        ldoinBuffer.writeFloat(offsetX);
        ldoinBuffer.writeFloat(offsetY);

        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.CUSTOM_PAYLOAD);

        packet.getStrings().write(0, "visualdriver:text");
        packet.getModifier().write(1, MinecraftReflection.getPacketDataSerializer(ldoinBuffer.getBuf()));

        protocolManager.sendServerPacket(player, packet);
        return this;
    }

    public WorldContext text(Player player, String text, int x, int y, int z, int textColor, int backgroundColor) {
        text(player, UUID.randomUUID().toString(), text, x, y, z, textColor, backgroundColor, true, 0f, 0f);
        return this;
    }

}
