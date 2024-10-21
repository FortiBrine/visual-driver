package me.fortibrine.visualdriver.bukkit.listener;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.fortibrine.visualdriver.bukkit.hud.HudScreenBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerJoinListener implements Listener {

    private final Plugin plugin;
    private final ProtocolManager protocolManager;

    public PlayerJoinListener(Plugin plugin) {
        this.plugin = plugin;
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        new HudScreenBuilder()
                .text("hello, world", 10, 10, 0xFF0000FF)
                .apply(player);
    }

}
