package me.fortibrine.visualdriver.fabric.drawable;

import me.fortibrine.visualdriver.fabric.VisualDriver;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;

public interface DrawableRenderer {
    public void draw(VisualDriver mod, InGameHud gui, DrawContext context, float delta);
}
