package me.fortibrine.visualdriver.bukkit.gui.widget;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public class GuiButton {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final String text;
    private final Consumer<Player> onPress;
}
