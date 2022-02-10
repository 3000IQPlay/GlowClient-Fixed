//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.entity.item.EntityMinecartChest
 *  net.minecraft.entity.item.EntityMinecartHopper
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.tileentity.TileEntityDispenser
 *  net.minecraft.tileentity.TileEntityDropper
 *  net.minecraft.tileentity.TileEntityEnderChest
 *  net.minecraft.tileentity.TileEntityFurnace
 *  net.minecraft.tileentity.TileEntityHopper
 *  net.minecraft.tileentity.TileEntityShulkerBox
 *  net.minecraft.util.math.BlockPos
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.render.RenderUtils;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StorageESP
extends ToggleMod {
    public static SettingBoolean chest = SettingUtils.settingBoolean("StorageESP", "Chests", "Outlines Chests", true);
    public static SettingBoolean furnace = SettingUtils.settingBoolean("StorageESP", "Furnaces", "Outlines Furnaces", true);
    public static SettingBoolean echest = SettingUtils.settingBoolean("StorageESP", "EChests", "Outlines EChests", true);
    public static SettingBoolean dispenser = SettingUtils.settingBoolean("StorageESP", "Dispensers", "Outlines Dispensers", true);
    public static SettingBoolean hopper = SettingUtils.settingBoolean("StorageESP", "Hoppers", "Outlines Hoppers", true);
    public static SettingBoolean shulker = SettingUtils.settingBoolean("StorageESP", "Shulkers", "Outlines ShulkerBoxes", true);
    public static SettingBoolean itemframes = SettingUtils.settingBoolean("StorageESP", "ItemFrames", "Outlines ItemFrames", false);

    public StorageESP() {
        super(Category.RENDER, "StorageESP", false, -1, "Draws outline around storage blocks");
    }

    @SubscribeEvent
    public void onRender(RenderEvent event) {
        BlockPos pos;
        if (chest.getBoolean()) {
            event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            for (TileEntity tileEntity : Globals.MC.world.loadedTileEntityList) {
                if (!(tileEntity instanceof TileEntityChest)) continue;
                pos = tileEntity.getPos();
                RenderUtils.blockEspFrame(pos, 0.53, 0.33, 0.0);
            }
            event.getTessellator().draw();
        }
        if (furnace.getBoolean()) {
            event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            for (TileEntity tileEntity : Globals.MC.world.loadedTileEntityList) {
                if (!(tileEntity instanceof TileEntityFurnace)) continue;
                pos = tileEntity.getPos();
                RenderUtils.blockEspFrame(pos, 0.5, 0.5, 0.5);
            }
            event.getTessellator().draw();
        }
        if (echest.getBoolean()) {
            event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            for (TileEntity tileEntity : Globals.MC.world.loadedTileEntityList) {
                if (!(tileEntity instanceof TileEntityEnderChest)) continue;
                pos = tileEntity.getPos();
                RenderUtils.blockEspFrame(pos, 0.64, 0.29, 0.64);
            }
            event.getTessellator().draw();
        }
        if (dispenser.getBoolean()) {
            event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            for (TileEntity tileEntity : Globals.MC.world.loadedTileEntityList) {
                if (!(tileEntity instanceof TileEntityDispenser)) continue;
                pos = tileEntity.getPos();
                RenderUtils.blockEspFrame(pos, 0.5, 0.5, 0.5);
            }
            event.getTessellator().draw();
        }
        if (dispenser.getBoolean()) {
            event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            for (TileEntity tileEntity : Globals.MC.world.loadedTileEntityList) {
                if (!(tileEntity instanceof TileEntityDropper)) continue;
                pos = tileEntity.getPos();
                RenderUtils.blockEspFrame(pos, 0.5, 0.5, 0.5);
            }
            event.getTessellator().draw();
        }
        if (hopper.getBoolean()) {
            event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            for (TileEntity tileEntity : Globals.MC.world.loadedTileEntityList) {
                if (!(tileEntity instanceof TileEntityHopper)) continue;
                pos = tileEntity.getPos();
                RenderUtils.blockEspFrame(pos, 0.5, 0.5, 0.5);
            }
            event.getTessellator().draw();
        }
        if (shulker.getBoolean()) {
            event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            for (TileEntity tileEntity : Globals.MC.world.loadedTileEntityList) {
                if (!(tileEntity instanceof TileEntityShulkerBox)) continue;
                pos = tileEntity.getPos();
                RenderUtils.blockEspFrame(pos, 1.0, 0.0, 0.71);
            }
            event.getTessellator().draw();
        }
        if (chest.getBoolean()) {
            event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            for (Entity entity : Globals.MC.world.loadedEntityList) {
                if (!(entity instanceof EntityMinecartChest)) continue;
                pos = entity.getPosition();
                RenderUtils.blockEspFrame(pos, 0.53, 0.33, 0.0);
            }
            event.getTessellator().draw();
        }
        if (hopper.getBoolean()) {
            event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            for (Entity entity : Globals.MC.world.loadedEntityList) {
                if (!(entity instanceof EntityMinecartHopper)) continue;
                pos = entity.getPosition();
                RenderUtils.blockEspFrame(pos, 0.5, 0.5, 0.5);
            }
            event.getTessellator().draw();
        }
        if (itemframes.getBoolean()) {
            event.getBuffer().begin(1, DefaultVertexFormats.POSITION_COLOR);
            for (Entity entity : Globals.MC.world.loadedEntityList) {
                if (!(entity instanceof EntityItemFrame)) continue;
                pos = entity.getPosition();
                RenderUtils.blockEspFrame(pos, 0.53, 0.33, 0.0);
            }
            event.getTessellator().draw();
        }
    }
}

