package me.fortibrine.visualdriver.testplugin.listener;

import lombok.AllArgsConstructor;
import me.fortibrine.visualdriver.bukkit.VisualDriverPlugin;
import me.fortibrine.visualdriver.bukkit.hud.DisableRender;
import me.fortibrine.visualdriver.bukkit.hud.HudLayoutBuilder;
import me.fortibrine.visualdriver.bukkit.world.BukkitWorldContext;
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
                    new HudLayoutBuilder()
                            .rectangle(0, 0, 80, 40, 0xFFFFFFFF)
                            .text("HUD RENDER TEXT", 10, 10, 0xFF000000)
                            .disable(DisableRender.RENDER_EXPERIENCE_BAR)
                            .item("minecraft:apple", 50, 50)
                            .image("https://docs.fabricmc.net/assets/develop/rendering/draw-context-recipe-book-background.png", 30, 30, 0, 0, 256, 256)
                            .apply(player);
                    new BukkitWorldContext()
                            .text(player, "hello, world", 100, 100, 100, 0xFFFFFFFF, 0);
                    VisualDriverPlugin.getInstance().newGuiBuilder()
                            .title("test")
                            .button(50, 50, 120, 20, "click", player1 -> {
                                player1.sendMessage("click");
                            })
                            .textBox(50, 80, 120, 20, "text")
                            .open(player);
                },
                20L
        );
    }

}
