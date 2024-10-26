package me.fortibrine.visualdriver.forge.hud;

import lombok.Getter;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class HudManager {

    private final List<Consumer<Float>> drawActions = new ArrayList<>();
    private final List<Consumer<Float>> textDrawActions = new ArrayList<>();

    @SubscribeEvent
    public void onHudRender(RenderGameOverlayEvent.Text event) {
        new ArrayList<>(drawActions).forEach(action -> action.accept(event.getPartialTicks()));
        new ArrayList<>(textDrawActions).forEach(action -> action.accept(event.getPartialTicks()));
    }

}
