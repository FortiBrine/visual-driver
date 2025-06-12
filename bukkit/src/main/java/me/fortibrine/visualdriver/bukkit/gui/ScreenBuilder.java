package me.fortibrine.visualdriver.bukkit.gui;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPluginMessage;
import io.netty.buffer.Unpooled;
import me.fortibrine.visualdriver.api.JNetBuffer;
import me.fortibrine.visualdriver.bukkit.gui.widget.GuiButton;
import me.fortibrine.visualdriver.bukkit.gui.widget.GuiTextBox;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ScreenBuilder {

    private final JNetBuffer ldoinBuffer = new JNetBuffer(Unpooled.buffer());
    private String title;
    private final ScreenManager screenManager;
    private final List<Object> widgets = new ArrayList<>();
    private final String menuId = UUID.randomUUID().toString();

    public ScreenBuilder(ScreenManager screenManager) {
        this.screenManager = screenManager;

        ldoinBuffer.writeString(menuId);
    }

    public ScreenBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ScreenBuilder button(int x, int y, int width, int height, String text, Consumer<Player> onPress) {
        ldoinBuffer.writeString("button");
        ldoinBuffer.writeVarInt(x);
        ldoinBuffer.writeVarInt(y);
        ldoinBuffer.writeVarInt(width);
        ldoinBuffer.writeVarInt(height);
        ldoinBuffer.writeString(text);
        widgets.add(new GuiButton(x, y, width, height, text, onPress));
        return this;
    }

    public ScreenBuilder textBox(int x, int y, int width, int height, String text) {
        ldoinBuffer.writeString("textbox");
        ldoinBuffer.writeVarInt(x);
        ldoinBuffer.writeVarInt(y);
        ldoinBuffer.writeVarInt(width);
        ldoinBuffer.writeVarInt(height);
        ldoinBuffer.writeString(text);
        widgets.add(new GuiTextBox(x, y, width, height, text));
        return this;
    }

    public void open(Player player) {
        ldoinBuffer.writeString("end");
        ldoinBuffer.writeString(title == null ? player.getName() : title);

        PacketEvents.getAPI()
                .getPlayerManager()
                .sendPacket(player, new WrapperPlayServerPluginMessage(
                        "visualdriver:gui",
                        ldoinBuffer.getBuf().array()
                ));

        screenManager.getMenus().put(
                menuId,
                widgets
        );

    }

}
