package me.fortibrine.visualdriver.fabric.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class ExtendedScreen extends Screen {

    private final Consumer<Screen> consumer;

    public ExtendedScreen(Text component, Consumer<Screen> consumer) {
        super(component);

        this.consumer = consumer;
    }

    @Override
    protected void init() {
        consumer.accept(this);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

}
