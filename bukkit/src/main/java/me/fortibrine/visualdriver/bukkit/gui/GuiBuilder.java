package me.fortibrine.visualdriver.bukkit.gui;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.bukkit.gui.widget.Button;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class GuiBuilder {

    private final JNetBuffer ldoinBuffer = new JNetBuffer(Unpooled.buffer());
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    private String title;
    private final GuiManager guiManager;
    private final List<Object> widgets = new ArrayList<>();
    private final String menuId = UUID.randomUUID().toString();

    public GuiBuilder(GuiManager guiManager) {
        this.guiManager = guiManager;

        ldoinBuffer.writeString(menuId);
    }

    public GuiBuilder title(String title) {
        this.title = title;
        return this;
    }

    public GuiBuilder button(int x, int y, int width, int height, String text, Consumer<Player> onPress) {
        ldoinBuffer.writeString("button");
        ldoinBuffer.writeVarInt(x);
        ldoinBuffer.writeVarInt(y);
        ldoinBuffer.writeVarInt(width);
        ldoinBuffer.writeVarInt(height);
        ldoinBuffer.writeString(text);
        widgets.add(new Button(x, y, width, height, text, onPress));
        return this;
    }

    public void open(Player player) {
        ldoinBuffer.writeString("end");
        ldoinBuffer.writeString(title == null ? player.getName() : title);

        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.CUSTOM_PAYLOAD);

        packet.getStrings().write(0, "visualdriver:gui");
        packet.getModifier().write(1, MinecraftReflection.getPacketDataSerializer(ldoinBuffer.getBuf()));

        protocolManager.sendServerPacket(player, packet);

        guiManager.getMenus().put(
                menuId,
                widgets
        );

    }

}
