package me.fortibrine.visualdriver.fabric.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.fortibrine.visualdriver.fabric.event.HudRenderEvents;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @Inject(method = "renderPlayerHealth", at = @At("HEAD"), cancellable = true)
    public void renderPlayerHealth(PoseStack poseStack, CallbackInfo info) {
        if (!HudRenderEvents.RENDER_HEALTH.invoker().renderHealth(poseStack)) {
            info.cancel();
        }
    }

    @Inject(method = "renderVehicleHealth", at = @At("HEAD"), cancellable = true)
    public void renderVehicleHealth(PoseStack poseStack, CallbackInfo info) {
        if (!HudRenderEvents.RENDER_VEHICLE_HEALTH.invoker().renderVehicleHealth(poseStack)) {
            info.cancel();
        }
    }

    @Inject(method = "renderEffects", at = @At("HEAD"), cancellable = true)
    public void renderEffects(PoseStack poseStack, CallbackInfo info) {
        if (!HudRenderEvents.RENDER_EFFECTS.invoker().renderEffects(poseStack)) {
            info.cancel();
        }
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    public void renderExperienceBar(PoseStack poseStack, int i, CallbackInfo info) {
        if (!HudRenderEvents.RENDER_EXPERIENCE_BAR.invoker().renderExperienceBar(poseStack, i)) {
            info.cancel();
        }
    }

    @Inject(method = "renderJumpMeter", at = @At("HEAD"), cancellable = true)
    public void renderJumpMeter(PoseStack poseStack, int i, CallbackInfo info) {
        if (!HudRenderEvents.RENDER_JUMP_METER.invoker().renderJumpMeter(poseStack, i)) {
            info.cancel();
        }
    }

    @Inject(method = "renderSelectedItemName", at = @At("HEAD"), cancellable = true)
    public void renderSelectedItemName(PoseStack poseStack, CallbackInfo info) {
        if (!HudRenderEvents.RENDER_SELECTED_ITEM_NAME.invoker().renderSelectedItemName(poseStack)) {
            info.cancel();
        }
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    public void renderCrosshair(PoseStack poseStack, CallbackInfo info) {
        if (!HudRenderEvents.RENDER_CROSSHAIR.invoker().renderCrosshair(poseStack)) {
            info.cancel();
        }
    }

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    public void renderHotbar(float f, PoseStack poseStack, CallbackInfo info) {
        if (!HudRenderEvents.RENDER_HOTBAR.invoker().renderHotbar(f, poseStack)) {
            info.cancel();
        }
    }

}
