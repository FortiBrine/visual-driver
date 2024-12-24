package me.fortibrine.visualdriver.bukkit.gui;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.bukkit.gui.widget.Button;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class GuiManager extends PacketAdapter {

    private final Map<String, List<Object>> menus = new HashMap<>();

    public GuiManager(Plugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.CUSTOM_PAYLOAD);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        PacketContainer packet = event.getPacket();

        String channel = packet.getModifier().read(0).toString();
        ByteBuf byteBuf = (ByteBuf) packet.getModifier().read(1);

        if (!channel.equals("visualdriver:gui")) return;

        JNetBuffer ldoinBuffer = new JNetBuffer(byteBuf);

        String action = ldoinBuffer.readString();
        String menuId = ldoinBuffer.readString();
        int index = ldoinBuffer.readVarInt();

        List<Object> widgets = menus.getOrDefault(menuId, new ArrayList<>());

        if (action.equals("onPress")) {
            if (index < widgets.size() && widgets.get(index) instanceof Button) {
                Button button = (Button) widgets.get(index);

                button.getOnPress().accept(event.getPlayer());
            }
        }
    }

}
