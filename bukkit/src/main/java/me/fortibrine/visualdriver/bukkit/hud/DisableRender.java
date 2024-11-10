package me.fortibrine.visualdriver.bukkit.hud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DisableRender {
    RENDER_VEHICLE_HEALTH("render_vehicle_health"),
    RENDER_HEALTH("render_health"),
    RENDER_EFFECTS("render_effects"),
    RENDER_CROSSHAIR("render_crosshair"),
    RENDER_HOTBAR("render_hotbar"),
    RENDER_SELECTED_ITEM_NAME("render_selected_item_name"),
    RENDER_JUMP_METER("render_jump_meter"),
    RENDER_EXPERIENCE_BAR("render_experience_bar");

    private final String name;
}
