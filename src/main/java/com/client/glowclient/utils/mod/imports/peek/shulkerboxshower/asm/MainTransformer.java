/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.IClassTransformer
 *  net.minecraftforge.common.ForgeVersion
 *  net.minecraftforge.fml.common.FMLLog
 *  org.objectweb.asm.ClassReader
 *  org.objectweb.asm.ClassVisitor
 *  org.objectweb.asm.ClassWriter
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package com.client.glowclient.utils.mod.imports.peek.shulkerboxshower.asm;

import com.client.glowclient.utils.mod.imports.peek.shulkerboxshower.asm.IRegisterTransformer;
import com.client.glowclient.utils.mod.imports.peek.shulkerboxshower.asm.RegisterTransformer;
import java.util.HashMap;
import java.util.List;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.FMLLog;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public class MainTransformer
implements IClassTransformer {
    private HashMap<String, IRegisterTransformer> map = new HashMap();
    private String MCVERSION = this.getMCVERSION();

    public MainTransformer() {
        new RegisterTransformer(this).register();
    }

    public void register(IRegisterTransformer iRegisterTransformer) {
        List<String> name = iRegisterTransformer.getClassName();
        if (iRegisterTransformer.getMCVERSION().contains(this.MCVERSION)) {
            for (String s : name) {
                this.map.put(s, iRegisterTransformer);
            }
            FMLLog.log.info("{} Register SUCCESS", (Object)iRegisterTransformer.getClass().getSimpleName());
        } else {
            FMLLog.log.warn("This mc.ersion is {} but Transformer {} accept mc.ersion is {} that ignore this Transformer", (Object)this.MCVERSION, (Object)iRegisterTransformer.getClass().getSimpleName(), iRegisterTransformer.getMCVERSION());
        }
    }

    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        IRegisterTransformer irtf = null;
        if (this.map.containsKey(transformedName)) {
            irtf = this.map.get(transformedName);
            FMLLog.log.info("CLASS: " + irtf.getClass().getSimpleName() + " Transformer SUCCESS");
            return irtf.transform(name, transformedName, basicClass);
        }
        if (this.map.containsKey(name)) {
            irtf = this.map.get(name);
            FMLLog.log.info("CLASS: " + irtf.getClass().getSimpleName() + " Transformer SUCCESS");
            return irtf.transform(name, transformedName, basicClass);
        }
        return basicClass;
    }

    public static byte[] clearMethod(String name, String transformedName, byte[] basicClass, List<String> methodInfo) {
        ClassReader classReader = new ClassReader(basicClass);
        ClassNode classNode = new ClassNode();
        classReader.accept((ClassVisitor)classNode, 0);
        for (MethodNode method : classNode.methods) {
            if (!methodInfo.contains(method.name) || !methodInfo.contains(method.desc)) continue;
            method.instructions.clear();
            method.instructions.add((AbstractInsnNode)new InsnNode(177));
        }
        ClassWriter classWriter = new ClassWriter(2);
        classNode.accept((ClassVisitor)classWriter);
        return classWriter.toByteArray();
    }

    private String getMCVERSION() {
        try {
            return (String)ForgeVersion.class.getField("mcVersion").get("");
        }
        catch (Exception exception) {
            return "";
        }
    }
}

