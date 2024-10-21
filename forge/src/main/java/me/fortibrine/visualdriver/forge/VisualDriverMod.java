package me.fortibrine.visualdriver.forge;

import me.fortibrine.visualdriver.forge.command.SendPayloadCommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = VisualDriverMod.MODID, name = VisualDriverMod.NAME, version = VisualDriverMod.VERSION)
public class VisualDriverMod {

    public static final String MODID = "visual-driver";
    public static final String NAME = "Visual Driver";
    public static final String VERSION = "1.0";

    public VisualDriverMod() {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new SendPayloadCommand());
    }

}
