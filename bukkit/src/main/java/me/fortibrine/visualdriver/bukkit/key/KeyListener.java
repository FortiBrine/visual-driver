package me.fortibrine.visualdriver.bukkit.key;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import io.netty.buffer.ByteBuf;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.bukkit.VisualDriverPlugin;
import me.fortibrine.visualdriver.bukkit.key.event.KeyPressEvent;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.stream.Collectors;

public class KeyListener implements PacketListener {

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {

        String channel = event.getChannel().toString();
        ByteBuf byteBuf = (ByteBuf) event.getByteBuf();

        if (!channel.equals("visualdriver:key")) return;

        JNetBuffer ldoinBuffer = new JNetBuffer(byteBuf);

        String key = ldoinBuffer.readString();
        int pressType = ldoinBuffer.readVarInt();
        int modifierType = ldoinBuffer.readVarInt();

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
