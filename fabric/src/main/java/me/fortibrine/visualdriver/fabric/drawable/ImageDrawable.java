package me.fortibrine.visualdriver.fabric.drawable;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;

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

    private final Minecraft mc = Minecraft.getInstance();

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
    public void draw(VisualDriver mod, GuiComponent gui, PoseStack stack, float delta) {
        if (drawMode.equals("image")) {

            ResourceLocation location = mod.getImageLoader().load(url);

            mc.getTextureManager().bind(location);
            gui.blit(stack, x, y, offsetX, offsetY, width, height);
        }
    }

}
