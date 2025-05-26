package me.fortibrine.visualdriver.fabric.drawable;

import lombok.Getter;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

@Getter
public class ItemDrawable implements DrawConsumer {

    private final String drawMode;
    private String key;
    private int x;
    private int y;

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public ItemDrawable(String drawMode, JNetBuffer buffer) {

        this.drawMode = drawMode;

        if (!drawMode.equals("item")) {
            return;
        }

        this.key = buffer.readString();
        this.x = buffer.readVarInt();
        this.y = buffer.readVarInt();
    }

    @Override
    public void draw(VisualDriver mod, InGameHud gui, DrawContext context, float delta) {
        if (drawMode.equals("item")) {
            Item item = Registries.ITEM.get(Identifier.of(key));
            context.drawItem(new ItemStack(item), x, y);
        }
    }

}

