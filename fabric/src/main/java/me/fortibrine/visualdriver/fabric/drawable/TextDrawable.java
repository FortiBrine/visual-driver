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
public class TextDrawable implements DrawConsumer {

    private final Logger logger = LogManager.getLogger();

    private final String drawMode;
    private String text;
    private int x;
    private int y;
    private int color;

    private final Minecraft mc = Minecraft.getInstance();

    public TextDrawable(String drawMode, JNetBuffer buffer) {

        this.drawMode = drawMode;

        if (!drawMode.equals("text")) {
            return;
        }

        this.text = buffer.readString();
        this.x = buffer.readVarInt();
        this.y = buffer.readVarInt();
        this.color = buffer.readVarInt();

        logger.info("text ({} {} {} {})", text, x, y, color);
    }

    @Override
    public void draw(VisualDriver mod, GuiComponent gui, PoseStack stack, float delta) {
        if (drawMode.equals("text")) {
            mc.font.drawShadow(
                    stack,
                    text,
                    x, y,
                    color
            );
        }
    }

}
