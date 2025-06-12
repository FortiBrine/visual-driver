package me.fortibrine.visualdriver.fabric.drawable;

import lombok.Getter;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
public class TextElement implements DrawableRenderer {

    private final Logger logger = LogManager.getLogger();

    private final String drawMode;
    private String text;
    private int x;
    private int y;
    private int color;

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public TextElement(String drawMode, JNetBuffer buffer) {

        this.drawMode = drawMode;

        if (!drawMode.equals("text")) {
            return;
        }

        this.text = buffer.readString();
        this.x = buffer.readVarInt();
        this.y = buffer.readVarInt();
        this.color = buffer.readVarInt();
    }

    @Override
    public void draw(VisualDriver mod, InGameHud gui, DrawContext context, float delta) {
        if (drawMode.equals("text")) {
            context.drawTextWithShadow(
                    mc.textRenderer,
                    text,
                    x, y,
                    color
            );
        }
    }

}
