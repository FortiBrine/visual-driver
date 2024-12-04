package me.fortibrine.visualdriver.fabric.listener;

import com.mojang.blaze3d.platform.InputConstants;
import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.fabric.event.KeyPressCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class KeyListener implements KeyPressCallback {

    public KeyListener() {
        KeyPressCallback.EVENT.register(this);
    }

    @Override
    public void keyPress(InputConstants.Key key, int arg0, int modifiers, CallbackInfo info) {
        JNetBuffer ldoinBuffer = new JNetBuffer(Unpooled.buffer());

        ldoinBuffer.writeString(key.getName());
        ldoinBuffer.writeVarInt(arg0);
        ldoinBuffer.writeVarInt(modifiers);

        if (Minecraft.getInstance().getCurrentServer() != null) {
            ClientPlayNetworking.send(
                    new ResourceLocation("visualdriver", "key"),
                    new FriendlyByteBuf(ldoinBuffer.getBuf())
            );
        }

    }
}
