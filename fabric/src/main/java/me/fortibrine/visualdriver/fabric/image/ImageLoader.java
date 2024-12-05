package me.fortibrine.visualdriver.fabric.image;

import com.mojang.blaze3d.platform.NativeImage;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    private final Map<String, ResourceLocation> loaded = new HashMap<>();

    public ImageLoader() {

    }

    @SneakyThrows
    public ResourceLocation load(String path) {

        if (loaded.containsKey(path)) {
            return loaded.get(path);
        }

        NativeImage image = NativeImage.read(new URL(path).openStream());
        Minecraft client = Minecraft.getInstance();

        DynamicTexture texture = new DynamicTexture(image);
        TextureManager textureManager = client.getTextureManager();
        ResourceLocation location = textureManager.register("visualdriver", texture);

        loaded.put(path, location);

        return location;
    }

    public void unload(String path) {

    }

    public void clear() {
        new HashMap<>(loaded).keySet().forEach(this::unload);
    }

}
