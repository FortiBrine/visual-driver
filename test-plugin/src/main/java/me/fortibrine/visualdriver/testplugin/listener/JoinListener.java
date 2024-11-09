package me.fortibrine.visualdriver.testplugin.listener;

import lombok.AllArgsConstructor;
import me.fortibrine.visualdriver.bukkit.hud.HudScreenBuilder;
import me.fortibrine.visualdriver.bukkit.world.WorldContext;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

@AllArgsConstructor
public class JoinListener implements Listener {

    private final Plugin plugin;

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getServer().getScheduler().runTaskLater(plugin,
                () -> {
                    new HudScreenBuilder()
                            .rectangle(0, 0, 80, 40, 0xFFFFFFFF)
                            .text("HUD RENDER TEXT", 10, 10, 0xFF000000)
                            .apply(player);
                    new WorldContext()
                            .text(player, "hello, world", 100, 100, 100, 0xFFFFFFFF, 0);
                },
                20L
        );
    }

}
