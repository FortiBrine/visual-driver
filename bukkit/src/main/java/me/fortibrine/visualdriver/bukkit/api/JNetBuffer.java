package me.fortibrine.visualdriver.bukkit.api;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Getter
public class JNetBuffer {

    private final ByteBuf buf;

    public byte readByte() {
        return buf.readByte();
    }

    public void writeByte(byte val) {
        buf.writeByte(val);
    }

    public short readShort() {
        return buf.readShort();
    }

    public void writeShort(short val) {
        buf.writeShort(val);
    }

    public int readInt() {
        return buf.readInt();
    }

    public void writeInt(int val) {
        buf.writeInt(val);
    }

    public long readLong() {
        return buf.readLong();
    }

    public void writeLong(long val) {
        buf.writeLong(val);
    }

    public float readFloat() {
        return buf.readFloat();
    }

    public void writeFloat(float val) {
        buf.writeFloat(val);
    }

    public double readDouble() {
        return buf.readDouble();
    }

    public void writeDouble(double val) {
        buf.writeDouble(val);
    }

    public byte[] readBytes(int length) {
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        return bytes;
    }

    public void writeBytes(byte[] bytes) {
        buf.writeBytes(bytes);
    }

    public int readVarInt() {
        byte tmp;

        if ((tmp = readByte()) >= 0) {
            return tmp;
        } else {
            int result = tmp & 127;
            if ((tmp = readByte()) >= 0) {
                result |= tmp << 7;
            } else {
                result |= (tmp & 127) << 7;
                if ((tmp = readByte()) >= 0) {
                    result |= tmp << 14;
                } else {
                    result |= (tmp & 127) << 14;
                    if ((tmp = readByte()) >= 0) {
                        result |= tmp << 21;
                    } else {
                        result |= (tmp & 127) << 21;
                        result |= readByte() << 28;
                    }
                }
            }

            return result;
        }
    }

    public void writeVarInt(int val) {
        while (true) {
            int bits = val & 127;
            val >>>= 7;

            if (val == 0) {
                writeByte((byte) bits);
                return;
            }

            writeByte((byte) (bits | 128));
        }
    }

    public long readVarLong() {
        long value = 0L;

        for (int i = 0; i < 10; i++) {
            byte temp = readByte();
            value |= (long) (temp & 127) << i * 7;

            if ((temp & 128) != 128)
                break;
        }

        return value;
    }

    public void writeVarLong(long l) {
        do {
            byte temp = (byte) ((int) (l & 127L));
            l >>>= 7;

            if (l != 0L) {
                temp = (byte) (temp | 128);
            }

            writeByte(temp);
        } while (l != 0L);
    }

    public boolean[] readBooleanArray() {
        byte b = readByte();
        boolean[] array = new boolean[8];

        for (int i = 0; i < 8; i++) {
            array[i] = ((b >> i) & 1) == 1;
        }

        return array;
    }

    public void writeBooleanArray(boolean[] array) {
        byte result = 0;

        for (int i = 0; i < 8 && i < array.length; i++) {
            if (array[i]) {
                result |= (1 << i);
            }
        }

        writeByte(result);
    }

    public String readString() {
        int length = readVarInt();
        String read = buf.toString(buf.readerIndex(), length, StandardCharsets.UTF_8);
        buf.skipBytes(length);
        return read;
    }

    public void writeString(String s) {
        ByteBuf encoded = buf.alloc().buffer(ByteBufUtil.utf8MaxBytes(s));

        try {
            ByteBufUtil.writeUtf8(encoded, s);
            writeVarInt(encoded.readableBytes());
            buf.writeBytes(encoded);
        } finally {
            encoded.release();
        }
    }
}