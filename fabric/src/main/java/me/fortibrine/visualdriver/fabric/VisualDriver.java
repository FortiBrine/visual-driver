package me.fortibrine.visualdriver.fabric;

import lombok.Getter;
import me.fortibrine.visualdriver.fabric.gui.GuiPacketHandler;
import me.fortibrine.visualdriver.fabric.hud.HudOverlayManager;
import me.fortibrine.visualdriver.fabric.hud.handler.HudPacketHandler;
import me.fortibrine.visualdriver.fabric.image.GuiImageLoader;
import me.fortibrine.visualdriver.fabric.listener.KeyInputListener;
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

    private final HudOverlayManager hudOverlayManager = new HudOverlayManager(this);
    private final WorldManager worldManager = new WorldManager();
    private final GuiImageLoader guiImageLoader = new GuiImageLoader();

    @Override
    public void onInitializeClient() {

        PayloadTypeRegistry.playC2S().register(ClientKeyInputPacket.ID, ClientKeyInputPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(ClientGuiActionPacket.ID, ClientGuiActionPacket.CODEC);

        PayloadTypeRegistry.playS2C().register(GuiDataPacket.ID, GuiDataPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(HudDataPacket.ID, HudDataPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(WorldTextPayload.ID, WorldTextPayload.CODEC);

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            hudOverlayManager.getActions().clear();
            hudOverlayManager.getDisableRender().clear();
            worldManager.getActions().clear();
            guiImageLoader.clear();
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

        new HudPacketHandler(this);
        new TextPacketListener(this);
        new GuiPacketHandler(this);
        new KeyInputListener();

    }
}
