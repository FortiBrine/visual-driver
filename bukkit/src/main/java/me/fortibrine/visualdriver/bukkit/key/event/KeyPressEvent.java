package me.fortibrine.visualdriver.bukkit.key.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.fortibrine.visualdriver.bukkit.key.KeyModifier;
import me.fortibrine.visualdriver.bukkit.key.PressType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class KeyPressEvent extends Event {

    private final Player player;
    private final String key;
    private final PressType type;
    private final KeyModifier modifier;

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
