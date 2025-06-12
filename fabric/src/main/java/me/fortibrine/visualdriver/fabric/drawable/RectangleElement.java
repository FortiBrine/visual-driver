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
public class RectangleElement implements DrawableRenderer {

    private final Logger logger = LogManager.getLogger();

    private final String drawMode;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int color;

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public RectangleElement(String drawMode, JNetBuffer buffer) {

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
    public void draw(VisualDriver mod, InGameHud gui, DrawContext context, float delta) {
        if (drawMode.equals("rectangle")) {
            context.fill(x1, y1, x2, y2, color);
        }
    }

}
