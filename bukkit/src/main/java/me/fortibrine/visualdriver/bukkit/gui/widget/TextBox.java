package me.fortibrine.visualdriver.bukkit.gui.widget;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TextBox {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final String text;
}
