package me.fortibrine.visualdriver.fabric.drawable;

import com.mojang.blaze3d.vertex.PoseStack;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import net.minecraft.client.gui.GuiComponent;

public interface DrawConsumer {
    public void draw(VisualDriver mod, GuiComponent gui, PoseStack stack, float delta);
}
