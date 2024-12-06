package me.fortibrine.visualdriver.fabric.drawable;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
public class RectangleDrawable implements DrawConsumer {

    private final Logger logger = LogManager.getLogger();

    private final String drawMode;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int color;

    private final Minecraft mc = Minecraft.getInstance();

    public RectangleDrawable(String drawMode, JNetBuffer buffer) {

        this.drawMode = drawMode;

        if (!drawMode.equals("rectangle")) {
            return;
        }

        this.x1 = buffer.readVarInt();
        this.y1 = buffer.readVarInt();
        this.x2 = buffer.readVarInt();
        this.y2 = buffer.readVarInt();
        this.color = buffer.readVarInt();
    }

    @Override
    public void draw(VisualDriver mod, GuiComponent gui, PoseStack stack, float delta) {
        if (drawMode.equals("rectangle")) {
            GuiComponent.fill(stack, x1, y1, x2, y2, color);
        }
    }

}
