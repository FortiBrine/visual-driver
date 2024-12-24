package me.fortibrine.visualdriver.bukkit.world;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.bukkit.utils.CustomPayloadPacketUtil;
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

        protocolManager.sendServerPacket(player, CustomPayloadPacketUtil.createServerPacket("visualdriver:text", ldoinBuffer.getBuf()));
        return this;
    }

    public WorldContext text(Player player, String text, int x, int y, int z, int textColor, int backgroundColor) {
        text(player, UUID.randomUUID().toString(), text, x, y, z, textColor, backgroundColor, true, 0f, 0f);
        return this;
    }

}
