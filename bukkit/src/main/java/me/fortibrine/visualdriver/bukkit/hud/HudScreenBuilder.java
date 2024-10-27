package me.fortibrine.visualdriver.bukkit.hud;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import io.netty.buffer.Unpooled;
import lombok.NoArgsConstructor;
import me.fortibrine.visualdriver.bukkit.api.JNetBuffer;
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

    public void apply(Player player) {

        ldoinBuffer.writeString("end");

        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.CUSTOM_PAYLOAD);

        packet.getStrings().write(0, "visualdriver:hud");
        packet.getModifier().write(1, MinecraftReflection.getPacketDataSerializer(ldoinBuffer.getBuf()));

        protocolManager.sendServerPacket(player, packet);
    }

}
