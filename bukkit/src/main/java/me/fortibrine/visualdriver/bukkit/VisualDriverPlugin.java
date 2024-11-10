package me.fortibrine.visualdriver.bukkit;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import me.fortibrine.visualdriver.bukkit.gui.GuiBuilder;
import me.fortibrine.visualdriver.bukkit.gui.GuiManager;
import me.fortibrine.visualdriver.bukkit.key.KeyListener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class VisualDriverPlugin extends JavaPlugin {

    @Getter private static VisualDriverPlugin instance;
    private final GuiManager guiManager = new GuiManager(this);

    @Override
    public void onEnable() {
        instance = this;

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.addPacketListener(new KeyListener(this));
        protocolManager.addPacketListener(guiManager);
    }

    @Override
    public void onDisable() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.removePacketListeners(this);
    }

    public GuiBuilder newGuiBuilder() {
        return new GuiBuilder(guiManager);
    }

}
