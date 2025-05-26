package me.fortibrine.visualdriver.fabric.hud;

import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import me.fortibrine.visualdriver.fabric.drawable.ImageDrawable;
import me.fortibrine.visualdriver.fabric.drawable.ItemDrawable;
import me.fortibrine.visualdriver.fabric.drawable.RectangleDrawable;
import me.fortibrine.visualdriver.fabric.drawable.TextDrawable;
import me.fortibrine.visualdriver.fabric.packet.HudPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.Arrays;

public class HudPacketListener implements ClientPlayNetworking.PlayPayloadHandler<HudPayload> {

    private final VisualDriver mod;

    public HudPacketListener(VisualDriver mod) {
        this.mod = mod;
        ClientPlayNetworking.registerGlobalReceiver(HudPayload.ID, this);
    }

    @Override
    public void receive(HudPayload hudPayload, ClientPlayNetworking.Context context) {
        JNetBuffer ldoinBuffer = new JNetBuffer(Unpooled.wrappedBuffer(hudPayload.data()));

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
