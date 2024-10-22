package me.fortibrine.visualdriver.forge.listener;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import me.fortibrine.visualdriver.forge.VisualDriverMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
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

            player.sendMessage(new TextComponentString(packet.getChannelName()));

            if (!packet.getChannelName().equals("visualdriver:hud")) {
                ctx.fireChannelRead(msg);
                return;
            }

            String element;
            while (!(element = ByteBufUtils.readUTF8String(byteBuf)).equals("end")) {
                if (element.equals("text")) {
                    String text = ByteBufUtils.readUTF8String(byteBuf);
                    int x = byteBuf.readInt();
                    int y = byteBuf.readInt();
                    int color = byteBuf.readInt();

                    mod.getHudManager().getDrawActions().clear();
                    mod.getHudManager().getDrawActions().add((partialTicks) -> Minecraft.getMinecraft().fontRenderer.drawString(text, x, y, color));
                }
            }
        }

        ctx.fireChannelRead(msg);
    }
}
