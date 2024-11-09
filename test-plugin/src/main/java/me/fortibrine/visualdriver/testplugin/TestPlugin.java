package me.fortibrine.visualdriver.testplugin;

import me.fortibrine.visualdriver.testplugin.command.TextCommand;
import me.fortibrine.visualdriver.testplugin.listener.JoinListener;
import me.fortibrine.visualdriver.testplugin.listener.KeyListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("text").setExecutor(new TextCommand());
        Arrays.asList(
                new JoinListener(this),
                new KeyListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }
}
