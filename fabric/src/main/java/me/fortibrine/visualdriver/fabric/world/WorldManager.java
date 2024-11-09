package me.fortibrine.visualdriver.fabric.world;

import lombok.Getter;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Getter
public class WorldManager implements WorldRenderEvents.AfterEntities {

    private final Map<String, Consumer<WorldRenderContext>> actions = new HashMap<>();

    public WorldManager() {
        WorldRenderEvents.AFTER_ENTITIES.register(this);
    }

    @Override
    public void afterEntities(WorldRenderContext context) {
        new HashMap<>(actions).values().forEach(action -> action.accept(context));
    }

}
