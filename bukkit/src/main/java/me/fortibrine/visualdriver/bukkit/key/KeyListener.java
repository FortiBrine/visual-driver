package me.fortibrine.visualdriver.bukkit.key;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import io.netty.buffer.ByteBuf;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.bukkit.VisualDriverPlugin;
import me.fortibrine.visualdriver.bukkit.key.event.KeyPressEvent;

import java.util.Arrays;
import java.util.stream.Collectors;

public class KeyListener extends PacketAdapter {

    public KeyListener(VisualDriverPlugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.CUSTOM_PAYLOAD);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        PacketContainer packet = event.getPacket();

        String channel = packet.getStrings().read(0);
        ByteBuf byteBuf = (ByteBuf) packet.getModifier().read(1);

        if (!channel.equals("visualdriver:key")) return;

        JNetBuffer ldoinBuffer = new JNetBuffer(byteBuf);

        String key = ldoinBuffer.readString();
        int pressType = ldoinBuffer.readVarInt();
        int modifierType = ldoinBuffer.readVarInt();

        PressType click = Arrays.stream(PressType.values())
                .filter(type -> type.getPressType() == pressType)
                .collect(Collectors.toList())
                .get(0);

        Modifier modifier = Arrays.stream(Modifier.values())
                .filter(type -> type.getModifier() == modifierType)
                .collect(Collectors.toList())
                .get(0);

        plugin.getServer().getPluginManager().callEvent(new KeyPressEvent(
                event.getPlayer(),
                key,
                click,
                modifier
        ));
    }

}
