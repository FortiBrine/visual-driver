package me.fortibrine.visualdriver.bukkit.listener;

import lombok.AllArgsConstructor;
import me.fortibrine.visualdriver.bukkit.hud.HudScreenBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

@AllArgsConstructor
public class PlayerJoinListener implements Listener {

    private final Plugin plugin;

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(
                plugin,
                () -> {
                    new HudScreenBuilder()
                            .text("hello, world", 10, 10, 0xFF0000FF)
                            .rectangle(0, 0, 40, 40, 0x000000FF)
                            .apply(player);
                },
                20L, 20L
        );
    }

}
