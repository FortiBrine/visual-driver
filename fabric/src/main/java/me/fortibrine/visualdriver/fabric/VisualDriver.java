package me.fortibrine.visualdriver.fabric;

import lombok.Getter;
import me.fortibrine.visualdriver.fabric.gui.GuiPacketListener;
import me.fortibrine.visualdriver.fabric.hud.HudManager;
import me.fortibrine.visualdriver.fabric.hud.HudPacketListener;
import me.fortibrine.visualdriver.fabric.image.ImageLoader;
import me.fortibrine.visualdriver.fabric.listener.KeyListener;
import me.fortibrine.visualdriver.fabric.packet.*;
import me.fortibrine.visualdriver.fabric.world.WorldManager;
import me.fortibrine.visualdriver.fabric.world.text.TextPacketListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.gui.screen.TitleScreen;

@Getter
public class VisualDriver implements ClientModInitializer {

    private final HudManager hudManager = new HudManager(this);
    private final WorldManager worldManager = new WorldManager();
    private final ImageLoader imageLoader = new ImageLoader();

    @Override
    public void onInitializeClient() {

        PayloadTypeRegistry.playC2S().register(KeyC2SPayload.ID, KeyC2SPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(GuiActionC2SPayload.ID, GuiActionC2SPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(GuiPayload.ID, GuiPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(HudPayload.ID, HudPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(WorldTextPayload.ID, WorldTextPayload.CODEC);

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            hudManager.getActions().clear();
            hudManager.getDisableRender().clear();
            worldManager.getActions().clear();
            imageLoader.clear();
        });

        ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof TitleScreen) {
                ScreenEvents.afterRender(screen).register(((screen1, matrices, mouseX, mouseY, tickDelta) -> {
                    matrices.drawTextWithShadow(
                            client.textRenderer,
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
        new GuiPacketListener(this);
        new KeyListener();

    }
}
