package me.fortibrine.visualdriver.testplugin.command;

import me.fortibrine.visualdriver.bukkit.world.WorldContext;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        Location location = player.getLocation();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        if (args.length < 3) {
            return false;
        }

        new WorldContext()
                .text((Player) sender, args[0], x, y, z, Integer.parseInt(args[1]), Integer.parseInt(args[2]));

        return true;
    }
}
