package me.fortibrine.visualdriver.bukkit.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.utility.MinecraftVersion;
import io.netty.buffer.ByteBuf;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;

@UtilityClass
public class CustomPayloadPacketUtil {

    @SneakyThrows
    public PacketContainer createServerPacket(String path, ByteBuf buffer) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.CUSTOM_PAYLOAD);

        if (protocolManager.getMinecraftVersion().compareTo(MinecraftVersion.AQUATIC_UPDATE) >= 0) {
            Class<?> minecraftKeyClass = MinecraftReflection.getMinecraftKeyClass();
            Constructor<?> constructor = minecraftKeyClass.getConstructor(String.class);
            Object minecraftKey = constructor.newInstance(path);
            packet.getModifier().write(0, minecraftKey);
        } else {
            packet.getStrings().write(0, path);
        }

        packet.getModifier().write(1, MinecraftReflection.getPacketDataSerializer(buffer));

        return packet;
    }

}
