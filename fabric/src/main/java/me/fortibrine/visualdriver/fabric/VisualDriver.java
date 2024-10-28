package me.fortibrine.visualdriver.fabric;

import lombok.Getter;
import me.fortibrine.visualdriver.fabric.event.TitleScreenRenderCallback;
import me.fortibrine.visualdriver.fabric.hud.HudManager;
import me.fortibrine.visualdriver.fabric.listener.KeyListener;
import me.fortibrine.visualdriver.fabric.listener.PacketListener;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;

@Getter
public class VisualDriver implements ClientModInitializer {

    private final HudManager hudManager = new HudManager();

    @Override
    public void onInitializeClient() {

        TitleScreenRenderCallback.EVENT.register((screen, stack, info) -> {
            Minecraft.getInstance().font.drawShadow(
                    stack,
                    "Visual Driver",
                    10,
                    10,
                    0xFFFFFF
            );
        });


        new PacketListener(this);
        new KeyListener();

    }

}
