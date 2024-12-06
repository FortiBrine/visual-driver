package me.fortibrine.visualdriver.bukkit.hud;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import io.netty.buffer.Unpooled;
import lombok.NoArgsConstructor;
import me.fortibrine.visualdriver.api.JNetBuffer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@NoArgsConstructor
public class HudScreenBuilder {

    private final JNetBuffer ldoinBuffer = new JNetBuffer(Unpooled.buffer());
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public HudScreenBuilder text(String text, int x, int y, int color) {
        ldoinBuffer.writeString("text");
        ldoinBuffer.writeString(text);
        ldoinBuffer.writeVarInt(x);
        ldoinBuffer.writeVarInt(y);
        ldoinBuffer.writeVarInt(color);
        return this;
    }

    public HudScreenBuilder rectangle(int x1, int y1, int x2, int y2, int color) {
        ldoinBuffer.writeString("rectangle");
        ldoinBuffer.writeVarInt(x1);
        ldoinBuffer.writeVarInt(y1);
        ldoinBuffer.writeVarInt(x2);
        ldoinBuffer.writeVarInt(y2);
        ldoinBuffer.writeVarInt(color);
        return this;
    }

    public HudScreenBuilder image(String url, int x, int y, int offsetX, int offsetY, int width, int height) {
        ldoinBuffer.writeString("image");
        ldoinBuffer.writeString(url);
        ldoinBuffer.writeVarInt(x);
        ldoinBuffer.writeVarInt(y);
        ldoinBuffer.writeVarInt(offsetX);
        ldoinBuffer.writeVarInt(offsetY);
        ldoinBuffer.writeVarInt(width);
        ldoinBuffer.writeVarInt(height);
        return this;
    }

    public HudScreenBuilder disable(DisableRender... renders) {
        for (DisableRender render : renders) {
            ldoinBuffer.writeString("disable");
            ldoinBuffer.writeString(render.getName());
        }

        return this;
    }

    public HudScreenBuilder item(String key, int x, int y) {
        ldoinBuffer.writeString("item");
        ldoinBuffer.writeString(key);
        ldoinBuffer.writeVarInt(x);
        ldoinBuffer.writeVarInt(y);
        return this;
    }

    public void apply(Player player) {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.CUSTOM_PAYLOAD);

        packet.getStrings().write(0, "visualdriver:hud");
        packet.getModifier().write(1, MinecraftReflection.getPacketDataSerializer(ldoinBuffer.getBuf()));

        protocolManager.sendServerPacket(player, packet);
    }

}
