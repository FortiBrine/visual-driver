package me.fortibrine.visualdriver.testplugin;

import me.fortibrine.visualdriver.bukkit.hud.HudScreenBuilder;
import me.fortibrine.visualdriver.bukkit.key.event.KeyPressEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        new HudScreenBuilder()
                .rectangle(0, 0, 80, 40, 0xFFFFFFFF)
                .text("hello, world", 10, 10, 0xFF000000)
                .apply(player);
    }

    @EventHandler
    public void onPress(KeyPressEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(
                event.getKey() + " " + event.getType() + " " + event.getModifier()
        );
    }
}
