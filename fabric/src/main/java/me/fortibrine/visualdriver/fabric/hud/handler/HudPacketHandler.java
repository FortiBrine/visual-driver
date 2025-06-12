package me.fortibrine.visualdriver.fabric.hud.handler;

import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import me.fortibrine.visualdriver.fabric.drawable.ImageDrawable;
import me.fortibrine.visualdriver.fabric.drawable.ItemDrawable;
import me.fortibrine.visualdriver.fabric.drawable.RectangleElement;
import me.fortibrine.visualdriver.fabric.drawable.TextElement;
import me.fortibrine.visualdriver.fabric.packet.HudDataPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.Arrays;

public class HudPacketHandler implements ClientPlayNetworking.PlayPayloadHandler<HudDataPacket> {

    private final VisualDriver mod;

    public HudPacketHandler(VisualDriver mod) {
        this.mod = mod;
        ClientPlayNetworking.registerGlobalReceiver(HudDataPacket.ID, this);
    }

    @Override
    public void receive(HudDataPacket hudDataPacket, ClientPlayNetworking.Context context) {
        JNetBuffer ldoinBuffer = new JNetBuffer(Unpooled.wrappedBuffer(hudDataPacket.data()));

        mod.getHudOverlayManager().getActions().clear();
        mod.getHudOverlayManager().getDisableRender().clear();

        while (ldoinBuffer.getBuf().isReadable()) {
            String drawMode = ldoinBuffer.readString();

            mod.getHudOverlayManager().getActions().addAll(Arrays.asList(
                    new TextElement(drawMode, ldoinBuffer),
                    new RectangleElement(drawMode, ldoinBuffer),
                    new ImageDrawable(drawMode, ldoinBuffer),
                    new ItemDrawable(drawMode, ldoinBuffer)
            ));

            if (drawMode.equals("disable")) {
                mod.getHudOverlayManager().getDisableRender().add(ldoinBuffer.readString());
            }
        }
    }

}
