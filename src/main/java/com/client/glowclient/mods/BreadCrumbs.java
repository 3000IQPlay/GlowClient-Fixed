//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package com.client.glowclient.mods;

import com.client.glowclient.mods.HUD;
import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingDouble;
import com.client.glowclient.utils.client.Globals;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class BreadCrumbs
extends ToggleMod {
    public static final SettingDouble width = SettingUtils.settingDouble("BreadCrumbs", "Width", "Line Thickness", 1.5, 0.5, 0.0, 10.0);
    public static ArrayList<double[]> positionsList = new ArrayList();
    static int count = 0;

    public BreadCrumbs() {
        super(Category.RENDER, "BreadCrumbs", false, -1, "Draws Line behind player");
    }

    @Override
    public void onDisabled() {
        positionsList.removeAll(positionsList);
    }

    @SubscribeEvent
    public void onLocalPlayerUpdate(RenderEvent event) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            if (this.isEnabled()) {
                if (++count >= 50) {
                    count = 0;
                    if (positionsList.size() > 5) {
                        positionsList.remove(0);
                    }
                }
                for (Object o : Globals.MC.world.playerEntities) {
                    if (!(o instanceof EntityPlayer)) continue;
                    EntityPlayer player1 = (EntityPlayer)o;
                    boolean shouldBreadCrumb = player1 == Globals.MC.player;
                    double pozY = renderPosY + 2.0;
                    if (Globals.MC.player.isElytraFlying()) {
                        pozY -= 1.5;
                    }
                    if (!shouldBreadCrumb) continue;
                    double x = renderPosX;
                    double y = pozY;
                    double z = renderPosZ;
                    positionsList.add(new double[]{x, y - (double)player1.height, z});
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static double posit(double val) {
        return val == 0.0 ? val : (val < 0.0 ? val * -1.0 : val);
    }

    @SubscribeEvent
    public void onRender(RenderEvent event) {
        try {
            double renderPosX = Globals.MC.getRenderManager().renderPosX;
            double renderPosY = Globals.MC.getRenderManager().renderPosY;
            double renderPosZ = Globals.MC.getRenderManager().renderPosZ;
            float red = (float)HUD.red.getInt() / 255.0f;
            float green = (float)HUD.green.getInt() / 255.0f;
            float blue = (float)HUD.blue.getInt() / 255.0f;
            if (this.isEnabled()) {
                GL11.glPushMatrix();
                GL11.glLineWidth((float)((float)width.getDouble()));
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glEnable((int)3042);
                GL11.glLineWidth((float)((float)width.getDouble()));
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2929);
                GL11.glDepthMask((boolean)false);
                GL11.glBegin((int)3);
                for (double[] pos : positionsList) {
                    double distance = BreadCrumbs.posit(Math.hypot(pos[0] - Globals.MC.player.posX, pos[1] - Globals.MC.player.posY));
                    if (distance > 100.0) continue;
                    GL11.glColor4f((float)red, (float)green, (float)blue, (float)(1.0f - (float)(distance / 100.0)));
                    GL11.glVertex3d((double)(pos[0] - renderPosX), (double)(pos[1] - renderPosY), (double)(pos[2] - renderPosZ));
                }
                GL11.glEnd();
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2929);
                GL11.glDepthMask((boolean)true);
                GL11.glDisable((int)3042);
                GL11.glPopMatrix();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

