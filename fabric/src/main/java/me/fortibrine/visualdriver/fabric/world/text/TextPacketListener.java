package me.fortibrine.visualdriver.fabric.world.text;

import com.mojang.blaze3d.vertex.PoseStack;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class TextPacketListener implements ClientPlayNetworking.PlayChannelHandler {

    private final VisualDriver mod;

    public TextPacketListener(VisualDriver mod) {
        this.mod = mod;
        ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation("visualdriver", "text"), this);
    }

    @Override
    public void receive(Minecraft mc, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        Font font = mc.font;

        JNetBuffer ldoinBuffer = new JNetBuffer(buf);

        String id = ldoinBuffer.readString();
        int x = ldoinBuffer.readVarInt();
        int y = ldoinBuffer.readVarInt();
        int z = ldoinBuffer.readVarInt();
        String text = ldoinBuffer.readString();
        int textColor = ldoinBuffer.readVarInt();
        int backgroundColor = ldoinBuffer.readVarInt();
        boolean rotation = ldoinBuffer.getBuf().readBoolean();
        float offsetX = ldoinBuffer.readFloat();
        float offsetY = ldoinBuffer.readFloat();

        mod.getWorldManager().getActions().put(
                id,
                (context) -> {
                    PoseStack pose = context.matrixStack();
                    pose.pushPose();

                    Vec3 camera = context.camera().getPosition();
                    pose.translate(-camera.x, -camera.y, -camera.z);
                    pose.translate(x, y, z);

                    if (rotation) {
                        pose.mulPose(context.camera().rotation());
                    }

                    pose.scale(-0.025F, -0.025F, 0.025F);

                    font.drawInBatch(
                            text,
                            -font.width(text) / 2f + offsetX,
                            0 + offsetY,
                            textColor,
                            false,
                            pose.last().pose(),
                            context.consumers(),
                            false,
                            backgroundColor,
                            0
                    );
                    pose.popPose();
                }
        );
    }

}
