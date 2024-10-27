package me.fortibrine.visualdriver.fabric.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@Getter
public class HudManager {

    private final List<BiConsumer<PoseStack, Float>> actions = new ArrayList<>();

    public HudManager() {
        HudRenderCallback.EVENT.register((stack, delta) -> {
            new ArrayList<>(actions).forEach(action -> action.accept(stack, delta));
        });
    }

}
