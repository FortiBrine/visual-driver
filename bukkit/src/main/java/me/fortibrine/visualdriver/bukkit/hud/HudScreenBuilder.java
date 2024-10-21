package me.fortibrine.visualdriver.bukkit.hud;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;

public class HudScreenBuilder {

    private final ByteBuf buffer = Unpooled.buffer();
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public HudScreenBuilder() {

    }

    public HudScreenBuilder text(String text, int x, int y, int color) {
        buffer.writeCharSequence("text", Charsets.UTF_8);
        buffer.writeCharSequence(text, Charsets.UTF_8);
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(color);
        return this;
    }

    public void apply(Player player) {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.CUSTOM_PAYLOAD);

        packet.getStrings().write(0, "visualdriver:hud");
        packet.getModifier().write(1, MinecraftReflection.getPacketDataSerializer(buffer));

        protocolManager.sendServerPacket(player, packet);
    }

}
