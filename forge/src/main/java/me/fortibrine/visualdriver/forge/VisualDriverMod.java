package me.fortibrine.visualdriver.forge;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.fortibrine.visualdriver.forge.command.SendPayloadCommand;
import me.fortibrine.visualdriver.forge.hud.HudManager;
import me.fortibrine.visualdriver.forge.listener.PacketListener;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = VisualDriverMod.MODID, name = VisualDriverMod.NAME, version = VisualDriverMod.VERSION)
@NoArgsConstructor
@Getter
public class VisualDriverMod {

    public static final String MODID = "visual-driver";
    public static final String NAME = "Visual Driver";
    public static final String VERSION = "1.0";

    private final HudManager hudManager = new HudManager();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(hudManager);
        MinecraftForge.EVENT_BUS.register(new PacketListener(this));
        ClientCommandHandler.instance.registerCommand(new SendPayloadCommand());
    }

}
