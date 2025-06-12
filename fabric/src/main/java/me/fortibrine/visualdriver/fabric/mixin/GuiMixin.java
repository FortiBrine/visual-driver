package me.fortibrine.visualdriver.fabric.mixin;

import me.fortibrine.visualdriver.fabric.event.HudOverlayEvents;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.JumpingMount;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class GuiMixin {

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    public void renderPlayerHealth(DrawContext drawContext, CallbackInfo ci) {
        if (!HudOverlayEvents.RENDER_HEALTH.invoker().renderHealth(drawContext.getMatrices())) {
            ci.cancel();
        }
    }

    @Inject(method = "renderMountHealth", at = @At("HEAD"), cancellable = true)
    public void renderVehicleHealth(DrawContext drawContext, CallbackInfo ci) {
        if (!HudOverlayEvents.RENDER_VEHICLE_HEALTH.invoker().renderVehicleHealth(drawContext.getMatrices())) {
            ci.cancel();
        }
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    public void renderEffects(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo ci) {
        if (!HudOverlayEvents.RENDER_EFFECTS.invoker().renderEffects(drawContext.getMatrices())) {
            ci.cancel();
        }
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    public void renderExperienceBar(DrawContext drawContext, int i, CallbackInfo ci) {
        if (!HudOverlayEvents.RENDER_EXPERIENCE_BAR.invoker().renderExperienceBar(drawContext.getMatrices())) {
            ci.cancel();
        }
    }

    @Inject(method = "renderMountJumpBar", at = @At("HEAD"), cancellable = true)
    public void renderJumpMeter(JumpingMount jumpingMount, DrawContext drawContext, int i, CallbackInfo ci) {
        if (!HudOverlayEvents.RENDER_JUMP_METER.invoker().renderJumpMeter(drawContext.getMatrices())) {
            ci.cancel();
        }
    }

    @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
    public void renderSelectedItemName(DrawContext drawContext, CallbackInfo ci) {
        if (!HudOverlayEvents.RENDER_SELECTED_ITEM_NAME.invoker().renderSelectedItemName(drawContext.getMatrices())) {
            ci.cancel();
        }
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    public void renderCrosshair(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo ci) {
        if (!HudOverlayEvents.RENDER_CROSSHAIR.invoker().renderCrosshair(drawContext.getMatrices())) {
            ci.cancel();
        }
    }

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    public void renderHotbar(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo ci) {
        if (!HudOverlayEvents.RENDER_HOTBAR.invoker().renderHotbar(drawContext.getMatrices())) {
            ci.cancel();
        }
    }

}
