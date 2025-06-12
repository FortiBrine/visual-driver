package me.fortibrine.visualdriver.bukkit.input.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyActionType {
    UP(0),
    DOWN(1),
    LONG(2);

    private final int pressType;
}
