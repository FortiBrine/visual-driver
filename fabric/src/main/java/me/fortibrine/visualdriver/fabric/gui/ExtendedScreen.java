package me.fortibrine.visualdriver.fabric.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class ExtendedScreen extends Screen {

    private final Consumer<Screen> consumer;

    public ExtendedScreen(Component component, Consumer<Screen> consumer) {
        super(component);

        this.consumer = consumer;
    }

    @Override
    protected void init() {
        consumer.accept(this);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
    }
}
