package me.fortibrine.visualdriver.fabric.hud;

import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import me.fortibrine.visualdriver.fabric.drawable.ImageDrawable;
import me.fortibrine.visualdriver.fabric.drawable.ItemDrawable;
import me.fortibrine.visualdriver.fabric.drawable.RectangleDrawable;
import me.fortibrine.visualdriver.fabric.drawable.TextDrawable;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;

public class HudPacketListener implements ClientPlayNetworking.PlayChannelHandler {

    private final VisualDriver mod;

    public HudPacketListener(VisualDriver mod) {
        this.mod = mod;
        ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation("visualdriver", "hud"), this);
    }

    @Override
    public void receive(Minecraft mc, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {

        JNetBuffer ldoinBuffer = new JNetBuffer(buf);

        mod.getHudManager().getActions().clear();
        mod.getHudManager().getDisableRender().clear();

        while (ldoinBuffer.getBuf().isReadable()) {
            String drawMode = ldoinBuffer.readString();

            mod.getHudManager().getActions().addAll(Arrays.asList(
                    new TextDrawable(drawMode, ldoinBuffer),
                    new RectangleDrawable(drawMode, ldoinBuffer),
                    new ImageDrawable(drawMode, ldoinBuffer),
                    new ItemDrawable(drawMode, ldoinBuffer)
            ));

            if (drawMode.equals("disable")) {
                mod.getHudManager().getDisableRender().add(ldoinBuffer.readString());
            }
        }
    }
}
