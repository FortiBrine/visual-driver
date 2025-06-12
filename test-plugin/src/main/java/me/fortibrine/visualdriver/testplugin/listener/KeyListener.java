package me.fortibrine.visualdriver.testplugin.listener;

import me.fortibrine.visualdriver.bukkit.input.event.InputEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KeyListener implements Listener {
    @EventHandler
    public void keyPress(InputEvent event) {
        Player player = event.getPlayer();

        player.sendMessage(
                event.getKey() + " " + event.getType() + " " + event.getModifier()
        );
    }
}
