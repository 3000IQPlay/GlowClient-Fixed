//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.util.ResourceLocation
 */
package com.client.glowclient.mods.background;

import com.client.glowclient.utils.base.mod.branches.ServiceMod;
import com.client.glowclient.utils.client.Globals;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class TextureDownloader
extends ServiceMod {
    public static ResourceLocation texturefile;
    public static ResourceLocation texturefile2;
    private final String textureurl = "https://raw.githubusercontent.com/Intel-i80486-DX4/paynoattention/main/ColorCodes.png";
    private final String textureurl2 = "https://raw.githubusercontent.com/Intel-i80486-DX4/paynoattention/main/wrench.png";

    public TextureDownloader() {
        super("TextureDownloader", "Gets texture from github source");
    }

    private BufferedImage getImageFromUrl(String link) {
        BufferedImage image = null;
        try {
            URL url = new URL(link);
            image = ImageIO.read(url);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void onLoad() {
        Globals.MC.addScheduledTask(() -> {
            try {
                BufferedImage image = this.getImageFromUrl("https://raw.githubusercontent.com/Intel-i80486-DX4/paynoattention/main/ColorCodes.png");
                BufferedImage image2 = this.getImageFromUrl("https://raw.githubusercontent.com/Intel-i80486-DX4/paynoattention/main/wrench.png");
                if (image == null) {
                    Globals.LOGGER.warn("Failed to download image");
                    return;
                }
                DynamicTexture dynamicTexture = new DynamicTexture(image);
                dynamicTexture.loadTexture(Globals.MC.getResourceManager());
                texturefile = Globals.MC.getTextureManager().getDynamicTextureLocation("Texture", dynamicTexture);
                DynamicTexture dynamicTexture2 = new DynamicTexture(image2);
                dynamicTexture2.loadTexture(Globals.MC.getResourceManager());
                texturefile2 = Globals.MC.getTextureManager().getDynamicTextureLocation("Texture2", dynamicTexture2);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

