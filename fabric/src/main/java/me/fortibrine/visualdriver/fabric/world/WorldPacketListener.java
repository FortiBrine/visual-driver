package me.fortibrine.visualdriver.fabric.world;

import com.mojang.blaze3d.vertex.PoseStack;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import me.fortibrine.visualdriver.fabric.event.CustomPayloadCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class WorldPacketListener implements CustomPayloadCallback {

    private final VisualDriver mod;

    public WorldPacketListener(VisualDriver mod) {
        this.mod = mod;
        CustomPayloadCallback.EVENT.register(this);
    }

    @Override
    public void payload(ResourceLocation identifier, FriendlyByteBuf byteBuf, CallbackInfo info) {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;
        LocalPlayer player = mc.player;

        if (player == null) return;

        String channel = identifier.toString();

        if (!channel.equals("visualdriver:text")) return;

        JNetBuffer ldoinBuffer = new JNetBuffer(byteBuf);

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
