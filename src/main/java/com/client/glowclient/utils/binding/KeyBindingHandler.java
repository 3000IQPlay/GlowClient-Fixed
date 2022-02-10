//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraftforge.client.settings.IKeyConflictContext
 */
package com.client.glowclient.utils.binding;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.IKeyConflictContext;

public class KeyBindingHandler {
    private static final IKeyConflictContext OVERRIDE_KEYCONFLICT_CONTEXT = new IKeyConflictContext(){

        public boolean isActive() {
            return true;
        }

        public boolean conflicts(IKeyConflictContext other) {
            return false;
        }
    };
    private final KeyBinding binding;
    private IKeyConflictContext oldConflictContext = null;
    private int bindingCount = 0;

    public KeyBindingHandler(KeyBinding bind) {
        this.binding = bind;
    }

    public KeyBinding getBinding() {
        return this.binding;
    }

    public void setPressed(boolean b) {
        KeyBinding.setKeyBindState((int)this.binding.getKeyCode(), (boolean)b);
    }

    public boolean isBound() {
        return this.binding.getKeyConflictContext() == OVERRIDE_KEYCONFLICT_CONTEXT;
    }

    public void bind() {
        ++this.bindingCount;
        if (this.oldConflictContext == null) {
            this.oldConflictContext = this.binding.getKeyConflictContext();
            this.binding.setKeyConflictContext(OVERRIDE_KEYCONFLICT_CONTEXT);
        }
    }

    public void unbind() {
        --this.bindingCount;
        if (this.oldConflictContext != null && this.bindingCount <= 0) {
            this.binding.setKeyConflictContext(this.oldConflictContext);
            this.oldConflictContext = null;
        }
        if (this.bindingCount < 0) {
            this.bindingCount = 0;
        }
    }
}

