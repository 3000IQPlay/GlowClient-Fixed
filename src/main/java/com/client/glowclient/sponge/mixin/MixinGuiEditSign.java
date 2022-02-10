//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiEditSign
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.init.Blocks
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntitySign
 *  net.minecraft.util.ChatAllowedCharacters
 *  net.minecraft.util.text.TextComponentString
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  org.lwjgl.input.Keyboard
 */
package com.client.glowclient.sponge.mixin;

import com.client.glowclient.mods.ColorSigns;
import com.client.glowclient.mods.background.TextureDownloader;
import com.client.glowclient.sponge.mixinutils.HookUtils;
import com.client.glowclient.utils.client.Globals;
import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(value=Side.CLIENT)
@Mixin(value={GuiEditSign.class})
public abstract class MixinGuiEditSign
extends GuiScreen {
    @Shadow
    private TileEntitySign tileSign;
    @Shadow
    private int updateCounter;
    @Shadow
    private int editLine;
    @Shadow
    private GuiButton doneBtn;

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents((boolean)true);
        this.doneBtn = this.addButton(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.format((String)"gui.done", (Object[])new Object[0])));
        this.tileSign.setEditable(false);
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled && button.id == 0) {
            this.tileSign.markDirty();
            Globals.MC.displayGuiScreen(null);
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 200) {
            this.editLine = this.editLine - 1 & 3;
        }
        if (keyCode == 208 || keyCode == 28 || keyCode == 156) {
            this.editLine = this.editLine + 1 & 3;
        }
        String s = HookUtils.isColorSignsActivated ? this.tileSign.signText[this.editLine].getFormattedText() : this.tileSign.signText[this.editLine].getUnformattedText();
        if (keyCode == 14 && s.length() > 0) {
            s = this.tileSign.signText[this.editLine].getUnformattedText();
            s = s.substring(0, s.length() - 1).replace("\u00a7r", "");
        }
        if (GuiScreen.isKeyComboCtrlV((int)keyCode)) {
            s = s + GuiScreen.getClipboardString();
        }
        if (ChatAllowedCharacters.isAllowedCharacter((char)typedChar) && this.fontRenderer.getStringWidth(s + typedChar) <= ColorSigns.doubleS.getInt()) {
            String s2 = s + typedChar;
            s = s2.replace("\u00a7r", "");
        }
        this.tileSign.signText[this.editLine] = new TextComponentString(s);
        if (keyCode == 1) {
            this.actionPerformed(this.doneBtn);
        }
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        if (HookUtils.isColorSignsActivated) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureDownloader.texturefile);
            int width = 73;
            int height = 300;
            Gui.drawModalRectWithCustomSizedTexture((int)2, (int)2, (float)0.0f, (float)0.0f, (int)width, (int)height, (float)width, (float)height);
        }
        this.drawCenteredString(this.fontRenderer, I18n.format((String)"sign.edit", (Object[])new Object[0]), this.width / 2, 40, 0xFFFFFF);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2), (float)0.0f, (float)50.0f);
        float f = 93.75f;
        GlStateManager.scale((float)-93.75f, (float)-93.75f, (float)-93.75f);
        GlStateManager.rotate((float)180.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        Block block = this.tileSign.getBlockType();
        if (block == Blocks.STANDING_SIGN) {
            float f1 = (float)(this.tileSign.getBlockMetadata() * 360) / 16.0f;
            GlStateManager.rotate((float)f1, (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.translate((float)0.0f, (float)-1.0625f, (float)0.0f);
        } else {
            int i = this.tileSign.getBlockMetadata();
            float f2 = 0.0f;
            if (i == 2) {
                f2 = 180.0f;
            }
            if (i == 4) {
                f2 = 90.0f;
            }
            if (i == 5) {
                f2 = -90.0f;
            }
            GlStateManager.rotate((float)f2, (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.translate((float)0.0f, (float)-1.0625f, (float)0.0f);
        }
        if (this.updateCounter / 6 % 2 == 0) {
            this.tileSign.lineBeingEdited = this.editLine;
        }
        TileEntityRendererDispatcher.instance.render((TileEntity)this.tileSign, -0.5, -0.75, -0.5, 0.0f);
        this.tileSign.lineBeingEdited = -1;
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

