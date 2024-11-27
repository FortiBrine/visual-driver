package me.fortibrine.visualdriver.fabric.hud;

import lombok.SneakyThrows;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.event.CustomPayloadCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HudPacketListener implements CustomPayloadCallback {

    private final VisualDriver mod;

    public HudPacketListener(VisualDriver mod) {
        this.mod = mod;
        CustomPayloadCallback.EVENT.register(this);
    }

    @SneakyThrows
    @Override
    public void payload(ResourceLocation identifier, FriendlyByteBuf byteBuf) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        String channel = identifier.toString();

        if (!channel.equals("visualdriver:hud")) return;

        JNetBuffer ldoinBuffer = new JNetBuffer(byteBuf);

        String drawMode = ldoinBuffer.readString();

        mod.getHudManager().getActions().clear();
        mod.getHudManager().getDisableRender().clear();

        while (!drawMode.equals("end")) {
            if (drawMode.equals("text")) {
                String text = ldoinBuffer.readString();
                int x = ldoinBuffer.readVarInt();
                int y = ldoinBuffer.readVarInt();
                int color = ldoinBuffer.readVarInt();

                mod.getHudManager().getActions().add((stack, delta) -> {
                    mc.font.drawShadow(
                            stack,
                            text,
                            x, y,
                            color
                    );
                });
            } else if (drawMode.equals("rectangle")) {
                int x1 = ldoinBuffer.readVarInt();
                int y1 = ldoinBuffer.readVarInt();
                int x2 = ldoinBuffer.readVarInt();
                int y2 = ldoinBuffer.readVarInt();
                int color = ldoinBuffer.readVarInt();

                mod.getHudManager().getActions().add((stack, delta) -> {
                    Gui.fill(stack, x1, y1, x2, y2, color);
                });
            } else if (drawMode.equals("disable")) {
                mod.getHudManager().getDisableRender().add(ldoinBuffer.readString());
            } else if (drawMode.equals("item")) {
                String key = ldoinBuffer.readString();
                int x = ldoinBuffer.readVarInt();
                int y = ldoinBuffer.readVarInt();

                Item item = Registry.ITEM.get(new ResourceLocation(key));
                mod.getHudManager().getActions().add((stack, delta) -> {
                    mc.getItemRenderer().renderGuiItem(new ItemStack(item), x, y);
                });
            }

            drawMode = ldoinBuffer.readString();
        }

    }
}
