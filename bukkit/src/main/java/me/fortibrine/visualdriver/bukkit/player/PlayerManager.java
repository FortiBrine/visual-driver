package me.fortibrine.visualdriver.bukkit.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private final Map<UUID, PlayerStatus> playerStatusMap = new HashMap<>();

    public void setPlayerStatus(UUID uuid, PlayerStatus status) {
        playerStatusMap.put(uuid, status);
    }

    public PlayerStatus getPlayerStatus(UUID uuid) {
        return playerStatusMap.getOrDefault(uuid, PlayerStatus.UNKNOWN);
    }

    public void setPlayerStatus(Player player, PlayerStatus status) {
        setPlayerStatus(player.getUniqueId(), status);
    }

    public PlayerStatus getPlayerStatus(Player player) {
        return getPlayerStatus(player.getUniqueId());
    }

    public boolean isPlayerModded(Player player) {
        return getPlayerStatus(player) == PlayerStatus.MODDED;
    }

}
