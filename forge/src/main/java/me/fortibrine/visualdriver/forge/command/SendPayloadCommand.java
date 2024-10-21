package me.fortibrine.visualdriver.forge.command;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.server.command.TextComponentHelper;

public class SendPayloadCommand extends CommandBase {

    @Override
    public String getName() {
        return "sendpayload";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.sendpayload.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayerSP player = Minecraft.getMinecraft().player;

        Minecraft.getMinecraft().getConnection().sendPacket(
                new CPacketCustomPayload(
                        args[0],
                        new PacketBuffer(Unpooled.buffer())
                )
        );

        ITextComponent component = TextComponentHelper.createComponentTranslation(
                player,
                "command.sendpayload.success"
        );
        player.sendMessage(component);
    }

}
