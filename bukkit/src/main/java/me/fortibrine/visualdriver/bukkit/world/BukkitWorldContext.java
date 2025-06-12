package me.fortibrine.visualdriver.bukkit.world;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPluginMessage;
import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitWorldContext {

    public BukkitWorldContext text(Player player, String id, String text, int x, int y, int z, int textColor, int backgroundColor, boolean rotation, float offsetX, float offsetY) {
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

        PacketEvents.getAPI()
                .getPlayerManager()
                .sendPacket(player, new WrapperPlayServerPluginMessage(
                        "visualdriver:text",
                        ldoinBuffer.getBuf().array()
                ));

        return this;
    }

    public BukkitWorldContext text(Player player, String text, int x, int y, int z, int textColor, int backgroundColor) {
        text(player, UUID.randomUUID().toString(), text, x, y, z, textColor, backgroundColor, true, 0f, 0f);
        return this;
    }

}
