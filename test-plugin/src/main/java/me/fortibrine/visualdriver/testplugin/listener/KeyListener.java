package me.fortibrine.visualdriver.testplugin.listener;

import me.fortibrine.visualdriver.bukkit.key.event.KeyPressEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KeyListener implements Listener {
    @EventHandler
    public void keyPress(KeyPressEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(
                event.getKey() + " " + event.getType() + " " + event.getModifier()
        );
    }
}
