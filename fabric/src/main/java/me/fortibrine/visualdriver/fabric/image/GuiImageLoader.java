package me.fortibrine.visualdriver.fabric.image;

import lombok.SneakyThrows;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiImageLoader {

    private final Map<String, Identifier> loaded = new HashMap<>();
    private final MinecraftClient client = MinecraftClient.getInstance();

    public GuiImageLoader() {

    }

    @SneakyThrows
    public Identifier load(String path) {

        if (loaded.containsKey(path)) {
            return loaded.get(path);
        }

        String textureName = UUID.randomUUID().toString().replace("-", "").substring(0, 6);

        NativeImage image = NativeImage.read(new URL(path).openStream());
        Identifier location = Identifier.of("visualdriver", textureName);
        NativeImageBackedTexture texture = new NativeImageBackedTexture(() -> textureName, image);
        TextureManager textureManager = client.getTextureManager();
        textureManager.registerTexture(location, texture);

        loaded.put(path, location);

        return location;
    }

    public void unload(String path) {
        if (!loaded.containsKey(path)) return;

        Identifier location = loaded.get(path);

        AbstractTexture texture = client.getTextureManager().getTexture(location);

        if (texture != null) {
            texture.close();
        }

        loaded.remove(path);
        client.getTextureManager().destroyTexture(location);
    }

    public void clear() {
        new HashMap<>(loaded).keySet().forEach(this::unload);
    }

}
