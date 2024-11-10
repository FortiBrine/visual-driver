package me.fortibrine.visualdriver.fabric;

import lombok.Getter;
import me.fortibrine.visualdriver.fabric.hud.HudManager;
import me.fortibrine.visualdriver.fabric.listener.KeyListener;
import me.fortibrine.visualdriver.fabric.hud.HudPacketListener;
import me.fortibrine.visualdriver.fabric.world.WorldManager;
import me.fortibrine.visualdriver.fabric.world.text.TextPacketListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;

@Getter
public class VisualDriver implements ClientModInitializer {

    private final HudManager hudManager = new HudManager();
    private final WorldManager worldManager = new WorldManager();

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            hudManager.getActions().clear();
            hudManager.getDisableRender().clear();
            worldManager.getActions().clear();
        });

        ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof TitleScreen) {
                ScreenEvents.afterRender(screen).register(((screen1, matrices, mouseX, mouseY, tickDelta) -> {
                    Minecraft.getInstance().font.drawShadow(
                            matrices,
                            "Visual Driver",
                            10,
                            10,
                            0xFFFFFF
                    );
                }));
            }
        });

        new HudPacketListener(this);
        new TextPacketListener(this);
        new KeyListener();

    }
}
