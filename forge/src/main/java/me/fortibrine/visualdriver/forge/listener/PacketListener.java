package me.fortibrine.visualdriver.forge.listener;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.forge.VisualDriverMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@ChannelHandler.Sharable
@AllArgsConstructor
public class PacketListener extends SimpleChannelInboundHandler<Packet<?>> {

    private final VisualDriverMod mod;

    @SubscribeEvent
    public void onServerConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        ChannelPipeline pipeline = event.getManager().channel().pipeline();
        pipeline.addBefore("packet_handler", this.getClass().getName(), this);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet<?> msg) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;

        if (player != null && msg instanceof SPacketCustomPayload) {
            SPacketCustomPayload packet = (SPacketCustomPayload) msg;
            ByteBuf byteBuf = packet.getBufferData();

//            player.sendMessage(new TextComponentString(packet.getChannelName()));

            if (!packet.getChannelName().equals("visualdriver:hud")) {
                ctx.fireChannelRead(msg);
                return;
            }

            JNetBuffer ldoinBuffer = new JNetBuffer(byteBuf);

            String drawMode = ldoinBuffer.readString();

            mod.getHudManager().getTextDrawActions().clear();
            mod.getHudManager().getDrawActions().clear();

            while (!drawMode.equals("end")) {

                if (drawMode.equals("text")) {
                    String text = ldoinBuffer.readString();
                    int x = ldoinBuffer.readVarInt();
                    int y = ldoinBuffer.readVarInt();
                    int color = ldoinBuffer.readVarInt();

                    mod.getHudManager().getTextDrawActions().add((partialTicks) -> Minecraft.getMinecraft().fontRenderer.drawString(text, x, y, color));
                } else if (drawMode.equals("rectangle")) {
                    int x1 = ldoinBuffer.readVarInt();
                    int y1 = ldoinBuffer.readVarInt();
                    int x2 = ldoinBuffer.readVarInt();
                    int y2 = ldoinBuffer.readVarInt();
                    int color = ldoinBuffer.readVarInt();

                    mod.getHudManager().getDrawActions().add((partialTicks) -> {
                        Gui.drawRect(x1, y1, x2, y2, color);
                    });
                }

                drawMode = ldoinBuffer.readString();
            }
        }

        ctx.fireChannelRead(msg);
    }
}
