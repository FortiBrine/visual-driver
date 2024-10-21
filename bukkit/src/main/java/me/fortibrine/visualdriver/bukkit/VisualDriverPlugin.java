package me.fortibrine.visualdriver.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.fortibrine.visualdriver.bukkit.listener.PacketListener;
import org.bukkit.plugin.java.JavaPlugin;

public class VisualDriverPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.addPacketListener(new PacketListener(this));
    }

    @Override
    public void onDisable() {

    }

}
