package me.fortibrine.visualdriver.fabric.listener;

import me.fortibrine.visualdriver.fabric.VisualDriver;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.event.CustomPayloadCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class PacketListener implements CustomPayloadCallback {

    private final VisualDriver mod;

    public PacketListener(VisualDriver mod) {
        this.mod = mod;
        CustomPayloadCallback.EVENT.register(this);
    }

    @Override
    public void payload(ResourceLocation identifier, FriendlyByteBuf byteBuf, CallbackInfo info) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) return;

        String channel = identifier.toString();

        if (!channel.equals("visualdriver:hud")) return;

        JNetBuffer ldoinBuffer = new JNetBuffer(byteBuf);

        String drawMode = ldoinBuffer.readString();

        mod.getHudManager().getActions().clear();

        while (!drawMode.equals("end")) {
            if (drawMode.equals("text")) {
                String text = ldoinBuffer.readString();
                int x = ldoinBuffer.readVarInt();
                int y = ldoinBuffer.readVarInt();
                int color = ldoinBuffer.readVarInt();

                mod.getHudManager().getActions().add((stack, delta) -> {
                    Minecraft.getInstance().font.drawShadow(
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
            }

            drawMode = ldoinBuffer.readString();
        }

    }
}
