package me.fortibrine.visualdriver.fabric.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.fortibrine.visualdriver.fabric.event.TitleScreenRenderCallback;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Inject(at = @At("TAIL"), method = "render")
    public void render(PoseStack stack, int i, int j, float f, CallbackInfo info) {
        TitleScreen titleScreen = (TitleScreen) (Object) this;

        TitleScreenRenderCallback.EVENT.invoker().render(titleScreen, stack, info);
    }

}
