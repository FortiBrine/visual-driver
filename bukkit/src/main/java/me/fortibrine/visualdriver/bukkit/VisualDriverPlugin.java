package me.fortibrine.visualdriver.bukkit;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import me.fortibrine.visualdriver.bukkit.gui.ScreenBuilder;
import me.fortibrine.visualdriver.bukkit.gui.ScreenManager;
import me.fortibrine.visualdriver.bukkit.input.BukkitKeyInputListener;
import me.fortibrine.visualdriver.bukkit.player.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class VisualDriverPlugin extends JavaPlugin {

    @Getter private static VisualDriverPlugin instance;
    private final ScreenManager screenManager = new ScreenManager();
    private PlayerManager playerManager;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        instance = this;

        PacketEvents.getAPI().init();

        PacketEvents.getAPI().getEventManager().registerListener(
                new BukkitKeyInputListener(),
                PacketListenerPriority.NORMAL
        );

        PacketEvents.getAPI().getEventManager().registerListener(
                screenManager,
                PacketListenerPriority.NORMAL
        );

        this.playerManager = new PlayerManager();
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    public ScreenBuilder newGuiBuilder() {
        return new ScreenBuilder(screenManager);
    }

}
