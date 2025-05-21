package me.fortibrine.visualdriver.bukkit.gui;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.bukkit.gui.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class GuiManager implements PacketListener {

    private final Map<String, List<Object>> menus = new HashMap<>();

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {

        String channel = event.getChannel().toString();
        ByteBuf byteBuf = (ByteBuf) event.getByteBuf();

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
