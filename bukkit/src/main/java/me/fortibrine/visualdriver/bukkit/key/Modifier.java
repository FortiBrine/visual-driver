package me.fortibrine.visualdriver.bukkit.key;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Modifier {
    BASIC_CLICK(0),
    SHIFT_CLICK(1),
    CTRL_CLICK(2);

    private final int modifier;

}
