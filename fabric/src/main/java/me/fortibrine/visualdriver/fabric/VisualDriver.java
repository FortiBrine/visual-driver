package me.fortibrine.visualdriver.fabric;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import lombok.SneakyThrows;
import me.fortibrine.visualdriver.fabric.gui.GuiPacketListener;
import me.fortibrine.visualdriver.fabric.hud.HudManager;
import me.fortibrine.visualdriver.fabric.hud.HudPacketListener;
import me.fortibrine.visualdriver.fabric.listener.KeyListener;
import me.fortibrine.visualdriver.fabric.world.WorldManager;
import me.fortibrine.visualdriver.fabric.world.text.TextPacketListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import javax.imageio.ImageIO;
import java.net.URI;
import java.net.URL;

@Getter
public class VisualDriver implements ClientModInitializer {

    private final HudManager hudManager = new HudManager();
    private final WorldManager worldManager = new WorldManager();

    @SneakyThrows
    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            hudManager.getActions().clear();
            hudManager.getDisableRender().clear();
            worldManager.getActions().clear();
        });

//        NativeImage image = NativeImage.read(new URL("https://docs.fabricmc.net/assets/develop/rendering/draw-context-recipe-book-background.png").openStream());
//        DynamicTexture texture = new DynamicTexture(image);
//        Minecraft mc = Minecraft.getInstance();
//        TextureManager textureManager = mc.getTextureManager();
//        ResourceLocation location = textureManager.register("preview", texture);

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

//                    textureManager.bind(location);
//                    GuiComponent.blit(matrices, 90, 90, 0, 0, 16, 16, 16, 16);
                }));
            }
        });

        new HudPacketListener(this);
        new TextPacketListener(this);
        new GuiPacketListener(this);
        new KeyListener();

    }
}
