package me.fortibrine.visualdriver.bukkit.gui;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.bukkit.gui.widget.GuiButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ScreenManager implements PacketListener {

    private final Map<String, List<Object>> menus = new HashMap<>();

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {

        if (!event.getPacketType().equals(PacketType.Play.Client.PLUGIN_MESSAGE)) return;

        WrapperPlayClientPluginMessage packet = new WrapperPlayClientPluginMessage(event);

        String channel = packet.getChannelName();

        if (!channel.equals("visualdriver:gui")) return;

        JNetBuffer buffer = new JNetBuffer(Unpooled.wrappedBuffer(packet.getData()));

        String action = buffer.readString();
        String menuId = buffer.readString();
        int index = buffer.readVarInt();

        List<Object> widgets = menus.getOrDefault(menuId, new ArrayList<>());

        if (action.equals("onPress")) {
            if (index < widgets.size() && widgets.get(index) instanceof GuiButton) {
                GuiButton guiButton = (GuiButton) widgets.get(index);

                guiButton.getOnPress().accept(event.getPlayer());
            }
        }
    }

}
