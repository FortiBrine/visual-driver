package me.fortibrine.visualdriver.bukkit.key;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PressType {
    UP(0),
    DOWN(1),
    LONG(2);

    private final int pressType;
}
