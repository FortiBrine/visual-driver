package me.fortibrine.visualdriver.bukkit.key;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPluginMessage;
import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.bukkit.VisualDriverPlugin;
import me.fortibrine.visualdriver.bukkit.key.event.KeyPressEvent;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.stream.Collectors;

public class KeyListener implements PacketListener {

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {

        if (!event.getPacketType().equals(PacketType.Play.Client.PLUGIN_MESSAGE)) return;

        WrapperPlayClientPluginMessage packet = new WrapperPlayClientPluginMessage(event);

        String channel = packet.getChannelName();

        if (!channel.equals("visualdriver:key")) return;

        JNetBuffer buffer = new JNetBuffer(Unpooled.wrappedBuffer(packet.getData()));

        String key = buffer.readString();
        int pressType = buffer.readVarInt();
        int modifierType = buffer.readVarInt();

        PressType click = Arrays.stream(PressType.values())
                .filter(type -> type.getPressType() == pressType)
                .collect(Collectors.toList())
                .get(0);

        KeyModifier modifier = Arrays.stream(KeyModifier.values())
                .filter(type -> type.getModifier() == modifierType)
                .collect(Collectors.toList())
                .get(0);

        Bukkit.getScheduler().runTask(VisualDriverPlugin.getInstance(), () -> {
           Bukkit.getPluginManager().callEvent(new KeyPressEvent(
                    event.getPlayer(),
                    key,
                    click,
                    modifier
            ));
        });

    }

}
