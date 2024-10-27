package me.fortibrine.visualdriver.fabric.mixin;

import io.netty.channel.ChannelHandlerContext;
import me.fortibrine.visualdriver.fabric.event.CustomPayloadCallback;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(Connection.class)
public class ConnectionMixin {

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"))
    public void channelRead0(ChannelHandlerContext context, Packet<?> packet, CallbackInfo info) throws IOException {

        if (!(packet instanceof ClientboundCustomPayloadPacket)) return;

        ClientboundCustomPayloadPacket customPayloadPacket = (ClientboundCustomPayloadPacket) packet;

        ResourceLocation identifier = customPayloadPacket.getIdentifier();
        FriendlyByteBuf data = customPayloadPacket.getData();

        CustomPayloadCallback.EVENT.invoker().payload(identifier, data, info);
    }

}
