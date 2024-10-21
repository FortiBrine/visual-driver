package me.fortibrine.visualdriver.bukkit.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.fortibrine.visualdriver.bukkit.VisualDriverPlugin;

public class PacketListener extends PacketAdapter {

    public PacketListener(VisualDriverPlugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.CUSTOM_PAYLOAD);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        PacketContainer packet = event.getPacket();

        String channel = packet.getStrings().read(0);

        plugin.getLogger().info(channel);
    }

}
