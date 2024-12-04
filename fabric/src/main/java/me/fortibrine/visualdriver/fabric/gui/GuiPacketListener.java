package me.fortibrine.visualdriver.fabric.gui;

import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import me.fortibrine.visualdriver.fabric.mixin.ScreenAccessor;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GuiPacketListener implements ClientPlayNetworking.PlayChannelHandler {

    private final VisualDriver mod;

    public GuiPacketListener(VisualDriver mod) {
        this.mod = mod;
        ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation("visualdriver", "gui"), this);
    }

    @Override
    public void receive(Minecraft mc, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
        JNetBuffer ldoinBuffer = new JNetBuffer(buf);

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

                            ClientPlayNetworking.send(
                                    new ResourceLocation("visualdriver", "gui"),
                                    new FriendlyByteBuf(ldoinClickBuffer.getBuf())
                            );
                        })
                ));
            } else if (drawMode.equals("textbox")) {

                int x = ldoinBuffer.readVarInt();
                int y = ldoinBuffer.readVarInt();
                int width = ldoinBuffer.readVarInt();
                int height = ldoinBuffer.readVarInt();
                String text = ldoinBuffer.readString();

                actions.add(screen -> ((ScreenAccessor) screen).addButton(
                        new EditBox(
                                mc.font,
                                x,
                                y,
                                width,
                                height,
                                new TextComponent(text)
                        )
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
