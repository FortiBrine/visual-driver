package me.fortibrine.visualdriver.fabric.mixin;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Screen.class)
public interface ScreenAccessor {

    @Invoker("addWidget")
    public <T extends GuiEventListener> T addWidget(T widget);

    @Invoker("addButton")
    public <T extends AbstractWidget> T addButton(T widget);

}
