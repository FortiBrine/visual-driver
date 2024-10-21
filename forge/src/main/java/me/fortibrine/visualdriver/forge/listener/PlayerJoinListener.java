package me.fortibrine.visualdriver.forge.listener;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@Mod.EventBusSubscriber
public class PlayerJoinListener {

    @SubscribeEvent
    public void join(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        Minecraft.getMinecraft().getConnection().sendPacket(
                new CPacketCustomPayload(
                        "visualdriver:load",
                        new PacketBuffer(Unpooled.buffer())
                )
        );

    }

}
