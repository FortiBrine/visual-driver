package me.fortibrine.visualdriver.forge.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class TitleScreenRenderHandler {

    @SubscribeEvent
    public static void onRenderTitleScreen(GuiScreenEvent.DrawScreenEvent event) {
        if (event.getGui() instanceof GuiMainMenu) {
            GuiMainMenu gui = (GuiMainMenu) event.getGui();

            gui.drawString(
                    Minecraft.getMinecraft().fontRenderer,
                    "Visual Driver",
                    10,
                    10,
                    0x000000
            );

        }
    }

}
