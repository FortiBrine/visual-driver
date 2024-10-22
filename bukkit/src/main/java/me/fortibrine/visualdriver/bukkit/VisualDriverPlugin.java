package me.fortibrine.visualdriver.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.fortibrine.visualdriver.bukkit.listener.PacketListener;
import me.fortibrine.visualdriver.bukkit.listener.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public class VisualDriverPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        protocolManager.addPacketListener(new PacketListener(this));
    }

    @Override
    public void onDisable() {

    }

}
