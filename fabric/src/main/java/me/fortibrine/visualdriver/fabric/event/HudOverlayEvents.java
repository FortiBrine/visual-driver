package me.fortibrine.visualdriver.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.util.math.MatrixStack;

public interface HudOverlayEvents {

    Event<RenderHealth> RENDER_HEALTH = EventFactory.createArrayBacked(
            RenderHealth.class,
            (listeners) -> (stack) -> {
                for (RenderHealth listener : listeners) {
                    if (!listener.renderHealth(stack)) {
                        return false;
                    }
                }
                return true;
            }
    );

    Event<RenderVehicleHealth> RENDER_VEHICLE_HEALTH = EventFactory.createArrayBacked(
            RenderVehicleHealth.class,
            (listeners) -> (stack) -> {
                for (RenderVehicleHealth listener : listeners) {
                    if (!listener.renderVehicleHealth(stack)) {
                        return false;
                    }
                }
                return true;
            }
    );

    Event<RenderEffects> RENDER_EFFECTS = EventFactory.createArrayBacked(
            RenderEffects.class,
            (listeners) -> (stack) -> {
                for (RenderEffects listener : listeners) {
                    if (!listener.renderEffects(stack)) {
                        return false;
                    }
                }
                return true;
            }
    );

    Event<RenderExperienceBar> RENDER_EXPERIENCE_BAR = EventFactory.createArrayBacked(
            RenderExperienceBar.class,
            (listeners) -> (stack) -> {
                for (RenderExperienceBar listener : listeners) {
                    if (!listener.renderExperienceBar(stack)) {
                        return false;
                    }
                }
                return true;
            }
    );

    Event<RenderJumpMeter> RENDER_JUMP_METER = EventFactory.createArrayBacked(
            RenderJumpMeter.class,
            (listeners) -> (stack) -> {
                for (RenderJumpMeter listener : listeners) {
                    if (!listener.renderJumpMeter(stack)) {
                        return false;
                    }
                }
                return true;
            }
    );

    Event<RenderSelectedItemName> RENDER_SELECTED_ITEM_NAME = EventFactory.createArrayBacked(
            RenderSelectedItemName.class,
            (listeners) -> (stack) -> {
                for (RenderSelectedItemName listener : listeners) {
                    if (!listener.renderSelectedItemName(stack)) {
                        return false;
                    }
                }
                return true;
            }
    );

    Event<RenderCrosshair> RENDER_CROSSHAIR = EventFactory.createArrayBacked(
            RenderCrosshair.class,
            (listeners) -> (stack) -> {
                for (RenderCrosshair listener : listeners) {
                    if (!listener.renderCrosshair(stack)) {
                        return false;
                    }
                }
                return true;
            }
    );

    Event<RenderHotbar> RENDER_HOTBAR = EventFactory.createArrayBacked(
            RenderHotbar.class,
            (listeners) -> (stack) -> {
                for (RenderHotbar listener : listeners) {
                    if (!listener.renderHotbar(stack)) {
                        return false;
                    }
                }
                return true;
            }
    );

    public static interface RenderHealth {
        boolean renderHealth(MatrixStack stack);
    }

    public static interface RenderVehicleHealth {
        boolean renderVehicleHealth(MatrixStack stack);
    }

    public static interface RenderEffects {
        boolean renderEffects(MatrixStack stack);
    }

    public static interface RenderExperienceBar {
        boolean renderExperienceBar(MatrixStack stack);
    }

    public static interface RenderJumpMeter {
        boolean renderJumpMeter(MatrixStack stack);
    }

    public static interface RenderSelectedItemName {
        boolean renderSelectedItemName(MatrixStack stack);
    }

    public static interface RenderCrosshair {
        boolean renderCrosshair(MatrixStack stack);
    }

    public static interface RenderHotbar {
        boolean renderHotbar(MatrixStack stack);
    }

}
