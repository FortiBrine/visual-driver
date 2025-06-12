package me.fortibrine.visualdriver.fabric.world.text;

import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import me.fortibrine.visualdriver.fabric.packet.WorldTextPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

public class TextPacketListener implements ClientPlayNetworking.PlayPayloadHandler<WorldTextPayload> {

    private final VisualDriver mod;

    public TextPacketListener(VisualDriver mod) {
        this.mod = mod;
        ClientPlayNetworking.registerGlobalReceiver(
                WorldTextPayload.ID,
                this
        );
    }

    @Override
    public void receive(WorldTextPayload worldTextPayload, ClientPlayNetworking.Context networkingContext) {
        MinecraftClient mc = networkingContext.client();
        TextRenderer font = mc.textRenderer;

        JNetBuffer ldoinBuffer = new JNetBuffer(Unpooled.wrappedBuffer(worldTextPayload.data()));

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
                    MatrixStack pose = context.matrixStack();
                    pose.push();

                    Vec3d camera = context.camera().getPos();
                    pose.translate(-camera.x, -camera.y, -camera.z);
                    pose.translate(x, y, z);

                    if (rotation) {
                        pose.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-context.camera().getYaw()));
                        pose.multiply(RotationAxis.POSITIVE_X.rotationDegrees(context.camera().getPitch()));
                    }

                    pose.scale(-0.025F, -0.025F, 0.025F);

                    font.draw(
                            Text.literal(text),
                            -font.getWidth(text) / 2f + offsetX,
                            0 + offsetY,
                            textColor,
                            false,
                            pose.peek().getPositionMatrix(),
                            context.consumers(),
                            TextRenderer.TextLayerType.NORMAL,
                            backgroundColor,
                            0,
                            false
                    );
                    pose.pop();
                }
        );
    }
}
