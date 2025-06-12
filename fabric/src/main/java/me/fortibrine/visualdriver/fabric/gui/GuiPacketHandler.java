package me.fortibrine.visualdriver.fabric.gui;

import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.VisualDriver;
import me.fortibrine.visualdriver.fabric.packet.ClientGuiActionPacket;
import me.fortibrine.visualdriver.fabric.packet.GuiDataPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GuiPacketHandler implements ClientPlayNetworking.PlayPayloadHandler<GuiDataPacket> {

    private final VisualDriver mod;

    public GuiPacketHandler(VisualDriver mod) {
        this.mod = mod;
        ClientPlayNetworking.registerGlobalReceiver(GuiDataPacket.ID, this);
    }

    @Override
    public void receive(GuiDataPacket guiDataPacket, ClientPlayNetworking.Context context) {
        JNetBuffer ldoinBuffer = new JNetBuffer(Unpooled.wrappedBuffer(guiDataPacket.data()));

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

                actions.add(screen -> screen.addDrawableChild(
                        ButtonWidget.builder(Text.literal(text), button -> {
                            ClientPlayNetworking.send(new ClientGuiActionPacket(
                                    "onPress",
                                    menuId,
                                    finalIndex
                            ));
                        })
                                .dimensions(x, y, width, height)
                                .build()
                ));
            } else if (drawMode.equals("textbox")) {

                int x = ldoinBuffer.readVarInt();
                int y = ldoinBuffer.readVarInt();
                int width = ldoinBuffer.readVarInt();
                int height = ldoinBuffer.readVarInt();
                String text = ldoinBuffer.readString();

                actions.add(screen -> screen.addDrawableChild(
                        new EditBoxWidget(
                                MinecraftClient.getInstance().textRenderer,
                                x,
                                y,
                                width,
                                height,
                                Text.empty(),
                                Text.literal(text)
                        )
                ));
            }

            index++;

            drawMode = ldoinBuffer.readString();
        }

        String title = ldoinBuffer.readString();

        CustomGuiScreen screen = new CustomGuiScreen(Text.literal(title), screen1 -> {
            actions.forEach(action -> action.accept(screen1));
        });

        MinecraftClient.getInstance().setScreen(screen);
    }

}
