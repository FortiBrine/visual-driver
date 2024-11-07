package me.fortibrine.visualdriver.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import me.fortibrine.visualdriver.fabric.event.TitleScreenRenderCallback;
import me.fortibrine.visualdriver.fabric.hud.HudManager;
import me.fortibrine.visualdriver.fabric.listener.KeyListener;
import me.fortibrine.visualdriver.fabric.listener.HudPacketListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.phys.Vec3;

@Getter
public class VisualDriver implements ClientModInitializer {

    private final HudManager hudManager = new HudManager();

    @Override
    public void onInitializeClient() {
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            Minecraft mc = Minecraft.getInstance();
            Font font = mc.font;

            PoseStack pose = context.matrixStack();
            pose.pushPose();
            Vec3 camera = context.camera().getPosition();
            pose.translate(-camera.x, -camera.y, -camera.z);
            pose.translate(100, 100, 100);
            pose.mulPose(context.camera().rotation());
            pose.scale(-0.025F, -0.025F, 0.025F);
            String text = "hello, world";
            font.drawInBatch(
                    text,
                    -font.width(text) / 2f,
                    0,
                    0xFF000000,
                    false,
                    pose.last().pose(),
                    context.consumers(),
                    false,
                    0xFFFFFFFF,
                    0
            );
            pose.popPose();
        });

        TitleScreenRenderCallback.EVENT.register((screen, stack, info) -> {
            Minecraft.getInstance().font.drawShadow(
                    stack,
                    "Visual Driver",
                    10,
                    10,
                    0xFFFFFF
            );
        });


        new HudPacketListener(this);
        new KeyListener();

    }
}
