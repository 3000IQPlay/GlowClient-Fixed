//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityBoat
 *  net.minecraft.entity.passive.AbstractHorse
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.fml.client.FMLClientHandler
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.GlowClient;
import com.client.glowclient.mods.background.TickrateRecorder;
import com.client.glowclient.utils.Utils;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.extra.pepsimod.PUtils;
import com.client.glowclient.utils.render.Colors;
import com.client.glowclient.utils.render.RainbowUtils;
import com.client.glowclient.utils.render.SurfaceBuilder;
import com.client.glowclient.utils.render.SurfaceHelper;
import com.client.glowclient.utils.render.fonts.Fonts;
import com.client.glowclient.utils.render.fonts.MinecraftFontRenderer;
import com.client.glowclient.utils.world.entity.EnchantmentUtils;
import com.client.glowclient.utils.world.entity.MovementUtils;
import java.awt.Color;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HUD
extends ToggleMod {
    public static SettingDouble separation = SettingUtils.settingDouble("HUD", "Separation", "Amount of Separation", 10.0, 1.0, 0.0, 15.0);
    public static SettingBoolean coordinates = SettingUtils.settingBoolean("HUD", "Coordinates", "Displays coordinates", true);
    public static SettingBoolean rotation = SettingUtils.settingBoolean("HUD", "Rotation", "Displays rotation data", true);
    public static SettingBoolean watermark = SettingUtils.settingBoolean("HUD", "Watermark", "Displays watermark", true);
    public static SettingBoolean tps = SettingUtils.settingBoolean("HUD", "TPS", "Displays ticks per second", true);
    public static SettingBoolean dimensioncoords = SettingUtils.settingBoolean("HUD", "DimensionCoords", "Ow/Nether Coordinates", true);
    public static SettingBoolean arraylist = SettingUtils.settingBoolean("HUD", "ArrayList", "Shows Mods", true);
    public static SettingBoolean armor = SettingUtils.settingBoolean("HUD", "Armor", "Displays armor being worn", true);
    public static SettingMode order = SettingUtils.settingMode("HUD", "Order", "Order of arraylist", "Length", "Length", "ABC");
    public static SettingMode side = SettingUtils.settingMode("HUD", "Side", "Side of the arraylist", "Right", "Right", "Left");
    public static SettingBoolean speed = SettingUtils.settingBoolean("HUD", "Speed", "Speed in m/s", true);
    public static SettingBoolean direction = SettingUtils.settingBoolean("HUD", "Direction", "Shows NSEW, XZ direction", true);
    public static SettingBoolean customfont = SettingUtils.settingBoolean("HUD", "CustomFont", "Enables the sexy font", true);
    public static SettingBoolean descriptions = SettingUtils.settingBoolean("HUD", "Descriptions", "Shows GUI Descriptions for arraylist", true);
    public static SettingMode colorMode = SettingUtils.settingMode("HUD", "Color", "Mode of color", "HUD", "HUD", "Category", "Random", "Rainbow");
    public static SettingDouble red = SettingUtils.settingDouble("HUD", "Red", "Red", 0.0, 1.0, 0.0, 255.0);
    public static SettingDouble green = SettingUtils.settingDouble("HUD", "Green", "Green", 140.0, 1.0, 0.0, 255.0);
    public static SettingDouble blue = SettingUtils.settingDouble("HUD", "Blue", "Blue", 255.0, 1.0, 0.0, 255.0);
    public static MinecraftFontRenderer FONT = Fonts.VERDANA;
    private static final String VERSION = GlowClient.getVersion();
    private int r;
    private int g;
    private int b;
    private int alpha;

    public HUD() {
        super(Category.OTHER, "HUD", true, -1, "Shows all HUD features");
    }

    @Override
    public boolean isDrawn() {
        return false;
    }

    private String generateTickRateText() {
        StringBuilder builder = new StringBuilder("");
        TickrateRecorder.TickRateData data = TickrateRecorder.getTickData();
        if (data.getSampleSize() <= 0) {
            builder.append("Loading...");
        } else {
            int factor = 100;
            int sections = data.getSampleSize() / factor;
            if (sections * factor < data.getSampleSize()) {
                TickrateRecorder.TickRateData.CalculationData point = data.getPoint();
                builder.append(String.format("%.2f", point.getAverage()));
                if (sections > 0) {
                    builder.append(", ");
                }
            }
            if (sections > 0) {
                for (int i = sections; i > 0; --i) {
                    int at = i * factor;
                    TickrateRecorder.TickRateData.CalculationData point = data.getPoint(at);
                    builder.append(String.format("%.2f", point.getAverage()));
                    if (i - 1 == 0) continue;
                    builder.append(", ");
                }
            }
        }
        return builder.toString();
    }

    @SubscribeEvent
    public void onRenderScreen(RenderGameOverlayEvent.Text event) {
        SurfaceBuilder builder = new SurfaceBuilder();
        Minecraft MC = FMLClientHandler.instance().getClient();
        ScaledResolution scaledresolution = new ScaledResolution(MC);
        int posX = 1;
        int posY = 1;
        String pitch = String.format("%.2f", Float.valueOf(MC.player.rotationPitch));
        String yaw = String.format("%.2f", Float.valueOf(MathHelper.wrapDegrees((float)MC.player.rotationYaw)));
        String y = String.format("%.3f", MC.player.posY);
        String x = String.format("%.3f", MC.player.posX);
        String z = String.format("%.3f", MC.player.posZ);
        String nx = String.format("%.3f", MC.player.posX / 8.0);
        String nz = String.format("%.3f", MC.player.posZ / 8.0);
        String ox = "";
        if (MC.player.dimension == -1 && MC.player.posX * 8.0 < 3.0E7 && MC.player.posX * 8.0 > -3.0E7) {
            ox = String.format("%.3f", MC.player.posX * 8.0);
        } else if (MC.player.posX * 8.0 > 3.0E7) {
            ox = "30000000.000";
        } else if (MC.player.posX * 8.0 < -3.0E7) {
            ox = "-30000000";
        }
        String oz = "";
        if (MC.player.dimension == -1 && MC.player.posZ * 8.0 < 3.0E7 && MC.player.posZ * 8.0 > -3.0E7) {
            oz = String.format("%.3f", MC.player.posZ * 8.0);
        } else if (MC.player.posZ * 8.0 > 3.0E7) {
            oz = "30000000.000";
        } else if (MC.player.posZ * 8.0 < -3.0E7) {
            oz = "-30000000";
        }
        FONT = customfont.getBoolean() ? Fonts.VERDANA : null;
        if (armor.getBoolean()) {
            int i = 0;
            int xPos = scaledresolution.getScaledWidth() / 2 + 129;
            int yPos = scaledresolution.getScaledHeight() - 55;
            xPos -= 103;
            if (Utils.getRidingEntity() instanceof EntityBoat) {
                yPos = ModuleManager.getModuleFromName("EntityHunger").isEnabled() ? scaledresolution.getScaledHeight() - 55 : scaledresolution.getScaledHeight() - 45;
            }
            if (Utils.getRidingEntity() instanceof AbstractHorse) {
                yPos = ModuleManager.getModuleFromName("EntityHunger").isEnabled() ? (((AbstractHorse)Utils.getRidingEntity()).getMaxHealth() > 21.0f ? scaledresolution.getScaledHeight() - 75 : scaledresolution.getScaledHeight() - 65) : (((AbstractHorse)Utils.getRidingEntity()).getMaxHealth() > 21.0f ? scaledresolution.getScaledHeight() - 65 : scaledresolution.getScaledHeight() - 55);
            }
            if (MC.player.isCreative()) {
                yPos = scaledresolution.getScaledHeight() - 40;
            }
            int count = 0;
            int total = 0;
            String items = "";
            for (int slot = 0; slot < MC.player.inventory.getSizeInventory(); ++slot) {
                ItemStack Stack = MC.player.inventory.getStackInSlot(slot);
                if (Stack == null || !Stack.getItem().equals((Object)Items.TOTEM_OF_UNDYING)) continue;
                total = count += Stack.getCount();
                items = String.valueOf(total);
            }
            this.drawHUDText(items, (double)xPos - SurfaceHelper.getStringWidth(FONT, items) - 11.0, yPos + 8, Colors.WHITE, SurfaceBuilder.getBuilder(), scaledresolution);
            for (int j = 0; j < 4; ++j) {
                ItemStack stack = PUtils.getWearingArmor(j);
                builder.reset().task(SurfaceBuilder::clearColor).task(SurfaceBuilder::enableItemRendering).item(stack, (double)(xPos + 3) + 7.5 * (double)i++, yPos).itemOverlay(stack, (double)(xPos - 5) + 7.5 * (double)i++, yPos).task(SurfaceBuilder::disableItemRendering);
                if (total >= 1) {
                    builder.reset().task(SurfaceBuilder::clearColor).task(SurfaceBuilder::enableItemRendering).item(Items.TOTEM_OF_UNDYING.getDefaultInstance(), xPos - 14, yPos).task(SurfaceBuilder::disableItemRendering);
                }
                double xI2 = xPos + 4 + j * 16;
                double yI2 = yPos - 2;
                List<EnchantmentUtils.EntityEnchantment> enchantments = EnchantmentUtils.getEnchantmentsSorted(stack.getEnchantmentTagList());
                if (enchantments == null) continue;
                for (EnchantmentUtils.EntityEnchantment enchant : enchantments) {
                    double d = 0;
                    builder.reset().push().task(SurfaceBuilder::enableBlend).task(SurfaceBuilder::enableFontRendering).fontRenderer(Fonts.VERDANA50).color(Colors.WHITE).text(enchant.getShortName(), xI2 + 1.0, yI2 + 1.0, true).color(Colors.WHITE).text(enchant.getShortName(), xI2, yI2).task(SurfaceBuilder::enableTexture2D).task(SurfaceBuilder::disableBlend).task(SurfaceBuilder::disableFontRendering).pop();
                    yI2 -= (double)SurfaceHelper.getTextHeight(0.5);
                    if (!(d <= yI2)) continue;
                }
            }
        }
        if (watermark.getBoolean()) {
            double x2 = posX;
            double y2 = posY;
            String watermark = "GlowClient";
            if (side.getMode().equals("Left")) {
                x2 = (double)(scaledresolution.getScaledWidth() - 1) - SurfaceHelper.getStringWidth(FONT, watermark);
            }
            this.drawHUDText(watermark, x2, y2, Colors.toRGBA(0, 150, 255, 255), builder, scaledresolution);
            if (side.getMode().equals("Right")) {
                builder.reset().fontRenderer(Fonts.VERDANA75).color(Colors.WHITE).text(VERSION, x2 + 1.0 + SurfaceHelper.getStringWidth(FONT, watermark), y2 + 1.0, true).color(Colors.toRGBA(255, 255, 255, 255)).text(VERSION, x2 + SurfaceHelper.getStringWidth(FONT, watermark), y2);
            }
            if (side.getMode().equals("Left")) {
                builder.reset().fontRenderer(Fonts.VERDANA75).color(Colors.WHITE).text(VERSION, x2 + 1.0 - SurfaceHelper.getStringWidth(FONT, watermark) * 0.75, y2 + 1.0, true).color(Colors.toRGBA(255, 255, 255, 255)).text(VERSION, x2 - SurfaceHelper.getStringWidth(FONT, watermark) * 0.75, y2);
            }
        }
        posY += 9;
        if (MC.currentScreen instanceof GuiChat) {
            posY -= 13;
        }
        String currentSpeed = String.format("%.3f", MovementUtils.getEntitySpeed((Entity)MC.player));
        Entity entity = MC.getRenderViewEntity();
        EnumFacing enumfacing = entity.getHorizontalFacing();
        String directiondata = "Loading...";
        switch (enumfacing) {
            case NORTH: {
                directiondata = "[-Z]";
                break;
            }
            case SOUTH: {
                directiondata = "[+Z]";
                break;
            }
            case WEST: {
                directiondata = "[-X]";
                break;
            }
            case EAST: {
                directiondata = "[+X]";
            }
        }
        String directiondata2 = "Loading...";
        switch (enumfacing) {
            case NORTH: {
                directiondata2 = "North";
                break;
            }
            case SOUTH: {
                directiondata2 = "South";
                break;
            }
            case WEST: {
                directiondata2 = "West";
                break;
            }
            case EAST: {
                directiondata2 = "East";
            }
        }
        if (speed.getBoolean()) {
            this.drawHUDTextInfo("Speed: ", currentSpeed + "m/s", posX, posY, Colors.toRGBA(red.getInt(), green.getInt(), blue.getInt(), 255), builder, scaledresolution);
            posY -= 9;
        }
        if (tps.getBoolean()) {
            this.drawHUDTextInfo("TPS: ", this.generateTickRateText(), posX, posY, Colors.toRGBA(red.getInt(), green.getInt(), blue.getInt(), 255), builder, scaledresolution);
            posY -= 9;
        }
        if (rotation.getBoolean()) {
            this.drawHUDTextInfo("Pitch: ", pitch, posX, posY, Colors.toRGBA(red.getInt(), green.getInt(), blue.getInt(), 255), builder, scaledresolution);
            this.drawHUDTextInfo("Yaw: ", yaw, posX, posY -= 9, Colors.toRGBA(red.getInt(), green.getInt(), blue.getInt(), 255), builder, scaledresolution);
            posY -= 9;
        }
        if (direction.getBoolean()) {
            this.drawHUDTextInfo(directiondata2 + " ", directiondata, posX, posY, Colors.toRGBA(red.getInt(), green.getInt(), blue.getInt(), 255), builder, scaledresolution);
            posY -= 9;
        }
        if (coordinates.getBoolean()) {
            int py = 28;
            if (MC.currentScreen instanceof GuiChat) {
                py += 12;
            }
            this.drawCoordinate("X: ", x, posX, 10.0, py);
            this.drawCoordinate("Y: ", y, posX, 10.0, py -= 9);
            this.drawCoordinate("Z: ", z, posX, 10.0, py -= 9);
            if (dimensioncoords.getBoolean()) {
                if (MC.player.dimension == 0) {
                    int pyn = 28;
                    if (MC.currentScreen instanceof GuiChat) {
                        pyn += 12;
                    }
                    this.drawCoordinate2("X: ", "NetherX: ", x, nx, posX, 1.0, pyn);
                    this.drawCoordinate2("Z: ", "NetherZ: ", z, nz, posX, 1.0, pyn -= 18);
                }
                if (MC.player.dimension == -1) {
                    int pyo = 28;
                    if (MC.currentScreen instanceof GuiChat) {
                        pyo += 12;
                    }
                    this.drawCoordinate2("X: ", "OwX: ", x, ox, posX, 1.0, pyo);
                    this.drawCoordinate2("Z: ", "OwZ: ", z, oz, posX, 1.0, pyo -= 18);
                }
            }
        }
        this.drawUglyMods(posX, scaledresolution, builder);
    }

    public void drawUglyMods(int posX, ScaledResolution scaledresolution, SurfaceBuilder builder) {
        if (arraylist.getBoolean()) {
            AtomicInteger posY = new AtomicInteger(1);
            AtomicInteger rainbowOffset = new AtomicInteger(-100);
            if (order.getMode().equals("ABC")) {
                ModuleManager.getMods().stream().filter(Module::isEnabled).filter(mod -> mod.isDrawn()).map(mod -> mod.getNameAndTag()).forEach(module -> {
                    if (colorMode.getMode().equals("Rainbow")) {
                        Color color = RainbowUtils.getRainbowColor(rainbowOffset.intValue() * 100000000, 1.0f);
                        this.r = color.getRed();
                        this.g = color.getGreen();
                        this.b = color.getBlue();
                        this.alpha = 200;
                    } else {
                        this.decideColor((String)module);
                    }
                    double x2 = (double)(posX + scaledresolution.getScaledWidth()) - SurfaceHelper.getStringWidth(FONT, module) - 2.0;
                    if (side.getMode().equals("Left")) {
                        x2 = 2.0;
                    }
                    if (FONT != null) {
                        builder.reset().fontRenderer(FONT).color(Colors.toRGBA(this.r, this.g, this.b, this.alpha)).text((String)module, x2 + 1.0, posY.intValue() + 1, true).color(Colors.toRGBA(this.r, this.g, this.b, this.alpha)).text((String)module, x2, posY.intValue());
                    } else {
                        builder.reset().fontRenderer(FONT).color(Colors.toRGBA(this.r, this.g, this.b, this.alpha)).text((String)module, x2, posY.doubleValue(), true);
                    }
                    posY.addAndGet(separation.getInt());
                    rainbowOffset.addAndGet(1);
                });
            }
            if (order.getMode().equals("Length")) {
                if (customfont.getBoolean()) {
                    ModuleManager.getMods().stream().filter(Module::isEnabled).filter(mod -> mod.isDrawn()).map(mod -> mod.getNameAndTag()).sorted(Comparator.comparing(SurfaceHelper::getTextWitFont).reversed()).forEach(module -> {
                        if (colorMode.getMode().equals("Rainbow")) {
                            Color color = RainbowUtils.getRainbowColor(rainbowOffset.intValue() * 100000000, 1.0f);
                            this.r = color.getRed();
                            this.g = color.getGreen();
                            this.b = color.getBlue();
                            this.alpha = 200;
                        } else {
                            this.decideColor((String)module);
                        }
                        double x2 = (double)(posX + scaledresolution.getScaledWidth()) - SurfaceHelper.getStringWidth(FONT, module) - 2.0;
                        if (side.getMode().equals("Left")) {
                            x2 = 2.0;
                        }
                        builder.reset().fontRenderer(FONT).color(Colors.toRGBA(this.r, this.g, this.b, this.alpha)).text((String)module, x2 + 1.0, posY.intValue() + 1, true).color(Colors.toRGBA(this.r, this.g, this.b, this.alpha)).text((String)module, x2, posY.intValue());
                        posY.addAndGet(separation.getInt());
                        rainbowOffset.addAndGet(1);
                    });
                } else {
                    ModuleManager.getMods().stream().filter(Module::isEnabled).filter(mod -> mod.isDrawn()).map(mod -> mod.getNameAndTag()).sorted(Comparator.comparing(SurfaceHelper::getTextWitFont).reversed()).forEach(module -> {
                        if (colorMode.getMode().equals("Rainbow")) {
                            Color color = RainbowUtils.getRainbowColor(rainbowOffset.intValue() * 100000000, 1.0f);
                            this.r = color.getRed();
                            this.g = color.getGreen();
                            this.b = color.getBlue();
                            this.alpha = 200;
                        } else {
                            this.decideColor((String)module);
                        }
                        double x2 = (double)(posX + scaledresolution.getScaledWidth()) - SurfaceHelper.getStringWidth(FONT, module) - 2.0;
                        if (side.getMode().equals("Left")) {
                            x2 = 2.0;
                        }
                        builder.reset().fontRenderer(FONT).color(Colors.toRGBA(this.r, this.g, this.b, this.alpha)).text((String)module, x2, posY.doubleValue(), true);
                        posY.addAndGet(separation.getInt());
                        rainbowOffset.addAndGet(1);
                    });
                }
            }
        }
    }

    private void decideColor(String modName) {
        if (colorMode.getMode().equals("HUD")) {
            this.r = red.getInt();
            this.g = green.getInt();
            this.b = blue.getInt();
            this.alpha = 255;
        }
        if (colorMode.getMode().equals("Random")) {
            for (Module mod : ModuleManager.getMods()) {
                if (!modName.equals(mod.getModName())) {
                    if (modName.contains(mod.getModName())) {
                        this.r = mod.randomRed;
                        this.g = mod.randomGreen;
                        this.b = mod.randomBlue;
                    }
                    this.alpha = 200;
                    continue;
                }
                this.r = mod.randomRed;
                this.g = mod.randomGreen;
                this.b = mod.randomBlue;
                this.alpha = 200;
            }
        }
        if (colorMode.getMode().equals("Category")) {
            for (Module mod : ModuleManager.getMods()) {
                if (!modName.equals(mod.getModName())) {
                    if (!modName.contains(mod.getModName())) continue;
                    if (mod.getModCategory().getName().equals("Combat")) {
                        this.r = 255;
                        this.g = 50;
                        this.b = 0;
                    }
                    if (mod.getModCategory().getName().equals("Player")) {
                        this.r = 255;
                        this.g = 150;
                        this.b = 0;
                    }
                    if (mod.getModCategory().getName().equals("Render")) {
                        this.r = 150;
                        this.g = 50;
                        this.b = 255;
                    }
                    if (mod.getModCategory().getName().equals("Server")) {
                        this.r = 0;
                        this.g = 200;
                        this.b = 50;
                    }
                    if (mod.getModCategory().getName().equals("Movement")) {
                        this.r = 50;
                        this.g = 140;
                        this.b = 255;
                    }
                    this.alpha = 200;
                    continue;
                }
                if (mod.getModCategory().getName().equals("Combat")) {
                    this.r = 255;
                    this.g = 50;
                    this.b = 0;
                }
                if (mod.getModCategory().getName().equals("Player")) {
                    this.r = 255;
                    this.g = 150;
                    this.b = 0;
                }
                if (mod.getModCategory().getName().equals("Render")) {
                    this.r = 150;
                    this.g = 50;
                    this.b = 255;
                }
                if (mod.getModCategory().getName().equals("Server")) {
                    this.r = 0;
                    this.g = 200;
                    this.b = 50;
                }
                if (mod.getModCategory().getName().equals("Movement")) {
                    this.r = 50;
                    this.g = 140;
                    this.b = 255;
                }
                this.alpha = 200;
            }
        }
    }

    private void drawCoordinate(String header, String setting, double posX, double xint, double yint) {
        SurfaceBuilder builder = new SurfaceBuilder();
        Minecraft MC = FMLClientHandler.instance().getClient();
        ScaledResolution scaledresolution = new ScaledResolution(MC);
        if (FONT != null) {
            builder.reset().fontRenderer(FONT).color(Colors.toRGBA(red.getInt(), green.getInt(), blue.getInt(), 255)).text(header, posX + 1.0, (double)scaledresolution.getScaledHeight() - yint + 1.0, true).color(Colors.toRGBA(red.getInt(), green.getInt(), blue.getInt(), 255)).text(header, posX, (double)scaledresolution.getScaledHeight() - yint).color(Colors.HUDGRAY).text(setting, posX + xint + 1.0, (double)scaledresolution.getScaledHeight() - yint + 1.0, true).color(Colors.HUDGRAY).text(setting, posX + xint, (double)scaledresolution.getScaledHeight() - yint);
        } else {
            builder.reset().fontRenderer(FONT).color(Colors.toRGBA(red.getInt(), green.getInt(), blue.getInt(), 255)).text(header, posX, (double)scaledresolution.getScaledHeight() - yint, true).color(Colors.HUDGRAY).text(setting, posX + xint, (double)scaledresolution.getScaledHeight() - yint, true);
        }
    }

    private void drawCoordinate2(String headerO, String headerN, String coordO, String coordN, double posX, double xint, double yint) {
        SurfaceBuilder builder = new SurfaceBuilder();
        Minecraft MC = FMLClientHandler.instance().getClient();
        ScaledResolution scaledresolution = new ScaledResolution(MC);
        if (FONT != null) {
            builder.reset().fontRenderer(FONT).color(Colors.toRGBA(red.getInt(), green.getInt(), blue.getInt(), 255)).text(headerN, SurfaceHelper.getStringWidth(FONT, coordO) + SurfaceHelper.getStringWidth(FONT, headerO) + posX + 1.0, (double)scaledresolution.getScaledHeight() - yint + 1.0, true).color(Colors.toRGBA(red.getInt(), green.getInt(), blue.getInt(), 255)).text(headerN, SurfaceHelper.getStringWidth(FONT, coordO) + SurfaceHelper.getStringWidth(FONT, headerO) + posX, (double)scaledresolution.getScaledHeight() - yint).color(Colors.HUDGRAY).text(coordN, SurfaceHelper.getStringWidth(FONT, coordO) + SurfaceHelper.getStringWidth(FONT, headerN) + SurfaceHelper.getStringWidth(FONT, headerO) + posX + 1.0, (double)scaledresolution.getScaledHeight() - yint + 1.0, true).color(Colors.HUDGRAY).text(coordN, SurfaceHelper.getStringWidth(FONT, coordO) + SurfaceHelper.getStringWidth(FONT, headerN) + SurfaceHelper.getStringWidth(FONT, headerO) + posX, (double)scaledresolution.getScaledHeight() - yint);
        } else {
            builder.reset().fontRenderer(FONT).color(Colors.toRGBA(red.getInt(), green.getInt(), blue.getInt(), 255)).text(headerN, SurfaceHelper.getStringWidth(FONT, coordO) + SurfaceHelper.getStringWidth(FONT, headerO) + posX, (double)scaledresolution.getScaledHeight() - yint, true).color(Colors.HUDGRAY).text(coordN, SurfaceHelper.getStringWidth(FONT, coordO) + SurfaceHelper.getStringWidth(FONT, headerO) + SurfaceHelper.getStringWidth(FONT, headerN) + posX, (double)scaledresolution.getScaledHeight() - yint, true);
        }
    }

    private void drawHUDText(String name, double posX, double posY, int Color2, SurfaceBuilder builder, ScaledResolution scaledresolution) {
        if (FONT != null) {
            builder.reset().fontRenderer(FONT).color(Color2).text(name, posX + 1.0, posY + 1.0, true).color(Color2).text(name, posX, posY);
        } else {
            builder.reset().fontRenderer(FONT).color(Color2).text(name, posX, posY, true);
        }
    }

    private void drawHUDTextInfo(String name, String setting, double posX, double posY, int Color2, SurfaceBuilder builder, ScaledResolution scaledresolution) {
        if (FONT != null) {
            builder.reset().fontRenderer(FONT).color(Color2).text(name, posX + (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, name) - SurfaceHelper.getStringWidth(FONT, setting) - 2.0 + 1.0 + 1.0, posY + (double)scaledresolution.getScaledHeight() - SurfaceHelper.getStringHeight(FONT) - 13.0 + 1.0, true).color(Color2).text(name, posX + (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, name) - SurfaceHelper.getStringWidth(FONT, setting) - 2.0 + 1.0, posY + (double)scaledresolution.getScaledHeight() - SurfaceHelper.getStringHeight(FONT) - 13.0).color(Colors.HUDGRAY).text(setting, posX + (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, setting) - 2.0 + 1.0, posY + (double)scaledresolution.getScaledHeight() - SurfaceHelper.getStringHeight(FONT) - 13.0 + 1.0, true).color(Colors.HUDGRAY).text(setting, posX + (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, setting) - 2.0, posY + (double)scaledresolution.getScaledHeight() - SurfaceHelper.getStringHeight(FONT) - 13.0);
        } else {
            builder.reset().fontRenderer(FONT).color(Color2).text(name, posX + (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, name) - SurfaceHelper.getStringWidth(FONT, setting) - 2.0 + 1.0, posY + (double)scaledresolution.getScaledHeight() - SurfaceHelper.getStringHeight(FONT) - 13.0, true).color(Colors.HUDGRAY).text(setting, posX + (double)scaledresolution.getScaledWidth() - SurfaceHelper.getStringWidth(FONT, setting) - 2.0, posY + (double)scaledresolution.getScaledHeight() - SurfaceHelper.getStringHeight(FONT) - 13.0, true);
        }
    }
}

