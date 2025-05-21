package me.fortibrine.visualdriver.bukkit;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import me.fortibrine.visualdriver.bukkit.gui.GuiBuilder;
import me.fortibrine.visualdriver.bukkit.gui.GuiManager;
import me.fortibrine.visualdriver.bukkit.key.KeyListener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class VisualDriverPlugin extends JavaPlugin {

    @Getter private static VisualDriverPlugin instance;
    private final GuiManager guiManager = new GuiManager();

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
                new KeyListener(),
                PacketListenerPriority.NORMAL
        );

        PacketEvents.getAPI().getEventManager().registerListener(
                guiManager,
                PacketListenerPriority.NORMAL
        );
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    public GuiBuilder newGuiBuilder() {
        return new GuiBuilder(guiManager);
    }

}
