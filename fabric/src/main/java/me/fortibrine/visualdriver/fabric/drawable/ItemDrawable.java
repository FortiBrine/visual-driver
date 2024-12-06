package me.fortibrine.visualdriver.fabric.drawable;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Getter
public class ItemDrawable implements DrawConsumer {

    private final String drawMode;
    private String key;
    private int x;
    private int y;

    private final Minecraft mc = Minecraft.getInstance();

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
    public void draw(VisualDriver mod, GuiComponent gui, PoseStack stack, float delta) {
        if (drawMode.equals("item")) {
            Item item = Registry.ITEM.get(new ResourceLocation(key));
            mc.getItemRenderer().renderGuiItem(new ItemStack(item), x, y);
        }
    }

}

