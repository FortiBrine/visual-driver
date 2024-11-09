package me.fortibrine.visualdriver.fabric.mixin;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Gui.class)
public class GuiMixin {

    @ModifyVariable(method = "renderPlayerHealth", at = @At(value = "STORE"), ordinal = 12)
    public int renderPlayerHealth(int v) {
        // for disable rendering armor
        return -1;
    }

}
