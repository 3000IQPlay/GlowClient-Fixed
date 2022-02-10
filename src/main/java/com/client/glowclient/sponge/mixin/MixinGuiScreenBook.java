//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiScreenBook
 *  net.minecraft.util.ChatAllowedCharacters
 */
package com.client.glowclient.sponge.mixin;

import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.util.ChatAllowedCharacters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={GuiScreenBook.class})
public abstract class MixinGuiScreenBook
extends GuiScreen {
    @Shadow
    private String bookTitle = "";
    @Shadow
    private boolean bookIsModified;

    @Shadow
    private void updateButtons() {
    }

    @Shadow
    private void sendBookToServer(boolean publish) throws IOException {
    }

    /**
     * @author Gopro336
     * @reason because tags are requred for @Overwrite
     */
    @Overwrite
    private void keyTypedInTitle(char typedChar, int keyCode) throws IOException {
        if (GuiScreen.isKeyComboCtrlV((int)keyCode)) {
            this.bookTitle = this.bookTitle + GuiScreen.getClipboardString();
        }
        switch (keyCode) {
            case 14: {
                if (!this.bookTitle.isEmpty()) {
                    this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
                    this.updateButtons();
                }
                return;
            }
            case 28: 
            case 156: {
                if (!this.bookTitle.isEmpty()) {
                    this.sendBookToServer(true);
                    this.mc.displayGuiScreen((GuiScreen)null);
                }
                return;
            }
        }
        if (ChatAllowedCharacters.isAllowedCharacter((char)typedChar)) {
            this.bookTitle = this.bookTitle + Character.toString(typedChar);
            this.updateButtons();
            this.bookIsModified = true;
        }
    }
}

