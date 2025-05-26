package me.fortibrine.visualdriver.fabric.drawable;

import lombok.Getter;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

@Getter
public class ImageDrawable implements DrawConsumer {

    private final String drawMode;
    private String url;
    private int x;
    private int y;
    private int offsetX;
    private int offsetY;
    private int width;
    private int height;

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public ImageDrawable(String drawMode, JNetBuffer buffer) {

        this.drawMode = drawMode;

        if (!drawMode.equals("image")) {
            return;
        }

        this.url = buffer.readString();
        this.x = buffer.readVarInt();
        this.y = buffer.readVarInt();
        this.offsetX = buffer.readVarInt();
        this.offsetY = buffer.readVarInt();
        this.width = buffer.readVarInt();
        this.height = buffer.readVarInt();
    }

    @Override
    public void draw(VisualDriver mod, InGameHud gui, DrawContext context, float delta) {
        if (drawMode.equals("image")) {

            Identifier location = mod.getImageLoader().load(url);
            context.drawTexture(RenderLayer::getGuiTextured, location, x, y, 14, 14, width, height, width, height);
        }
    }

}
