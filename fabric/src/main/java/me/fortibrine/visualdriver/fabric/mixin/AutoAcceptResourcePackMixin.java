package me.fortibrine.visualdriver.fabric.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class AutoAcceptResourcePackMixin {

    @Inject(method = "handleResourcePack", at = @At("HEAD"))
    public void handleResourcePack(ClientboundResourcePackPacket packet, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        ServerData data = client.getCurrentServer();

        if (data != null) {
            data.setResourcePackStatus(ServerData.ServerPackStatus.ENABLED);
        }
    }

}
