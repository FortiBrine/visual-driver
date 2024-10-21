package me.fortibrine.visualdriver.forge.listener;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

@Mod.EventBusSubscriber
public class CustomPayloadPacketsListener {

    @SubscribeEvent
    public void onCustomPayload(FMLNetworkEvent.ServerCustomPacketEvent event) {
        FMLProxyPacket packet = event.getPacket();
        ByteBuf byteBuf = packet.payload();


    }

}
