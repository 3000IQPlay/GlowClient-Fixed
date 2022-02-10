//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.ChunkPos
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.GenChunkEvent;
import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.base.setting.branches.SettingMode;
import com.client.glowclient.utils.render.RenderUtils;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChunkFinder
extends ToggleMod {
    public static final SettingDouble red = SettingUtils.settingDouble("ChunkFinder", "Red", "Red", 255.0, 1.0, 0.0, 255.0);
    public static final SettingDouble green = SettingUtils.settingDouble("ChunkFinder", "Green", "Green", 255.0, 1.0, 0.0, 255.0);
    public static final SettingDouble blue = SettingUtils.settingDouble("ChunkFinder", "Blue", "Blue", 255.0, 1.0, 0.0, 255.0);
    public static final SettingDouble alpha = SettingUtils.settingDouble("ChunkFinder", "Alpha", "Transparency", 255.0, 1.0, 0.0, 255.0);
    public static SettingMode mode = SettingUtils.settingMode("ChunkFinder", "Mode", "Rendering mode of ChunkFinder", "Flat", "Flat", "Wall");
    private ChunkPos pos;
    private static ArrayList<ChunkPos> chunkList = new ArrayList();

    public ChunkFinder() {
        super(Category.SERVER, "ChunkFinder", false, -1, "Find newly loaded chunks");
    }

    @Override
    public String getHUDTag() {
        return "";
    }

    @SubscribeEvent
    public void onChuckLoad(GenChunkEvent event) {
        this.pos = event.getChunk().getPos();
        chunkList.add(this.pos);
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onRender(RenderEvent event) throws ConcurrentModificationException {
        if (this.pos != null && chunkList != null) {
            for (ChunkPos pos : chunkList) {
                if (mode.getMode().equals("Flat")) {
                    RenderUtils.chunkEspFlat(pos, red.getInt(), green.getInt(), blue.getInt(), alpha.getInt());
                }
                if (!mode.getMode().equals("Wall")) continue;
                RenderUtils.chunkEspTall(pos, red.getInt(), green.getInt(), blue.getInt(), alpha.getInt());
            }
        }
    }

    @Override
    public void onDisabled() {
        chunkList.removeAll(chunkList);
    }
}

