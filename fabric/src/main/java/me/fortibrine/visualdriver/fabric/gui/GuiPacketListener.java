package me.fortibrine.visualdriver.fabric.gui;

import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import me.fortibrine.visualdriver.fabric.event.CustomPayloadCallback;
import me.fortibrine.visualdriver.fabric.mixin.ScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GuiPacketListener implements CustomPayloadCallback {

    private final VisualDriver mod;

    public GuiPacketListener(VisualDriver mod) {
        this.mod = mod;
        CustomPayloadCallback.EVENT.register(this);
    }

    @Override
    public void payload(ResourceLocation identifier, FriendlyByteBuf byteBuf) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        String channel = identifier.toString();

        if (!channel.equals("visualdriver:gui")) return;

        JNetBuffer ldoinBuffer = new JNetBuffer(byteBuf);

        String menuId = ldoinBuffer.readString();
        String drawMode = ldoinBuffer.readString();

        List<Consumer<Screen>> actions = new ArrayList<>();

        int index = 0;
        while (!drawMode.equals("end")) {
            if (drawMode.equals("button")) {
                int x = ldoinBuffer.readVarInt();
                int y = ldoinBuffer.readVarInt();
                int width = ldoinBuffer.readVarInt();
                int height = ldoinBuffer.readVarInt();
                String text = ldoinBuffer.readString();

                final int finalIndex = index;

                actions.add(screen -> ((ScreenAccessor) screen).addButton(
                        new Button(x, y, width, height, new TextComponent(text), button -> {
                            JNetBuffer ldoinClickBuffer = new JNetBuffer(Unpooled.buffer());

                            ldoinClickBuffer.writeString("onPress");
                            ldoinClickBuffer.writeString(menuId);
                            ldoinClickBuffer.writeVarInt(finalIndex);

                            if (mc.getConnection() != null) {
                                mc.getConnection().send(
                                        new ServerboundCustomPayloadPacket(
                                                new ResourceLocation("visualdriver", "gui"),
                                                new FriendlyByteBuf(ldoinClickBuffer.getBuf())
                                        )
                                );
                            }
                        })
                ));
            }
            index++;

            drawMode = ldoinBuffer.readString();
        }

        String title = ldoinBuffer.readString();

        ExtendedScreen screen = new ExtendedScreen(new TextComponent(title), screen1 -> {
            actions.forEach(action -> action.accept(screen1));
        });

        mc.setScreen(screen);
    }
}
