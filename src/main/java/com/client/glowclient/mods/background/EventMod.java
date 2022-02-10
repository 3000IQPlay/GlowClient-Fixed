//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.FutureCallback
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 *  joptsimple.internal.Strings
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.server.SPacketChat
 *  net.minecraft.network.play.server.SPacketPlayerListItem
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.IWorldEventListener
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.entity.living.LivingEvent$LivingUpdateEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.event.world.WorldEvent$Unload
 *  net.minecraftforge.fml.common.eventhandler.Event
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  net.minecraftforge.fml.common.gameevent.InputEvent$KeyInputEvent
 *  net.minecraftforge.fml.common.gameevent.TickEvent$ClientTickEvent
 *  org.lwjgl.input.Keyboard
 */
package com.client.glowclient.mods.background;

import com.client.glowclient.sponge.events.ModEvents.ChatMessageEvent;
import com.client.glowclient.sponge.events.ModEvents.PlayerConnectEvent;
import com.client.glowclient.sponge.events.ModEvents.PlayerUpdateEvent;
import com.client.glowclient.sponge.events.ModEvents.RenderEvent;
import com.client.glowclient.sponge.events.ModEvents.WorldChangeEvent;
import com.client.glowclient.sponge.events.ModEvents.listeners.WorldListener;
import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.base.mod.Module;
import com.client.glowclient.utils.base.mod.branches.ServiceMod;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.mod.builder.ModuleManager;
import com.client.glowclient.utils.client.Globals;
import com.client.glowclient.utils.render.geometry.GeometryTessellator;
import com.client.glowclient.utils.world.entity.EntityUtils;
import com.client.glowclient.utils.world.entity.RotationSpoofing;
import com.client.glowclient.utils.world.entity.uuid.PlayerInfo;
import com.client.glowclient.utils.world.entity.uuid.PlayerInfoHelper;
import com.google.common.util.concurrent.FutureCallback;
import com.mojang.authlib.GameProfile;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import joptsimple.internal.Strings;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldEventListener;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventMod
extends ServiceMod {
    private static final WorldListener WORLD_LISTENER = new WorldListener();
    private static final GeometryTessellator TESSELLATOR = new GeometryTessellator();
    private static final Pattern[] MESSAGE_PATTERNS = new Pattern[]{Pattern.compile("<(.*?)> (.*)")};
    private static final Pattern[] INCOMING_PRIVATE_MESSAGES = new Pattern[]{Pattern.compile("(.*?) whispers to you: (.*)"), Pattern.compile("(.*?) whispers: (.*)")};
    private static final Pattern[] OUTGOING_PRIVATE_MESSAGES = new Pattern[]{Pattern.compile("[Tt]o (.*?): (.*)")};

    public EventMod() {
        super("EventMod", "Registers specific events branching off of other events");
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (Globals.MC.world != null) {
            event.getWorld().addEventListener((IWorldEventListener)WORLD_LISTENER);
            MinecraftForge.EVENT_BUS.post((Event)new WorldChangeEvent(event.getWorld()));
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        if (Globals.MC.world != null) {
            MinecraftForge.EVENT_BUS.post((Event)new WorldChangeEvent(event.getWorld()));
        }
    }

    @SubscribeEvent
    public void onScoreboardEvent(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerListItem && Globals.MC.world != null && System.currentTimeMillis() > -1L) {
            final SPacketPlayerListItem packet = (SPacketPlayerListItem)event.getPacket();
            packet.getEntries().stream().filter(Objects::nonNull).filter(data -> data.getProfile() != null).filter(data -> !Strings.isNullOrEmpty((String)data.getProfile().getName())).forEach(data -> {
                String name = data.getProfile().getName();
                PlayerInfoHelper.invokeEfficiently(name, new FutureCallback<PlayerInfo>(){

                    public void onSuccess(@Nullable PlayerInfo result) {
                        if (result != null) {
                            switch (packet.getAction()) {
                                case ADD_PLAYER: {
                                    MinecraftForge.EVENT_BUS.post((Event)new PlayerConnectEvent.Join(result, data.getProfile()));
                                    break;
                                }
                                case REMOVE_PLAYER: {
                                    MinecraftForge.EVENT_BUS.post((Event)new PlayerConnectEvent.Leave(result, data.getProfile()));
                                }
                            }
                        }
                    }

                    public void onFailure(Throwable t) {
                    }
                });
            });
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth((float)1.0f);
        Vec3d renderPos = EntityUtils.getInterpolatedPos((Entity)Globals.MC.player, event.getPartialTicks());
        RenderEvent e = new RenderEvent(TESSELLATOR, renderPos);
        e.resetTranslation();
        MinecraftForge.EVENT_BUS.post((Event)e);
        GlStateManager.glLineWidth((float)1.0f);
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }

    @SubscribeEvent(priority=EventPriority.LOW)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Text event) {
        if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.TEXT)) {
            MinecraftForge.EVENT_BUS.post((Event)new RenderEvent.Render2DEvent(event.getPartialTicks()));
        }
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (Globals.MC.world != null && event.getEntityLiving().equals((Object)Globals.MC.player)) {
            PlayerUpdateEvent ev = new PlayerUpdateEvent(event.getEntityLiving());
            MinecraftForge.EVENT_BUS.post((Event)ev);
            event.setCanceled(ev.isCanceled());
        }
    }

    private static boolean extract(String message, Pattern[] patterns, BiConsumer<GameProfile, String> callback) {
        try {
            for (Pattern pattern : patterns) {
                Matcher matcher = pattern.matcher(message);
                if (!matcher.find()) continue;
                String messageSender = matcher.group(1);
                String messageOnly = matcher.group(2);
                if (Strings.isNullOrEmpty((String)messageSender)) continue;
                for (NetworkPlayerInfo data : Globals.MC.player.connection.getPlayerInfoMap()) {
                    if (String.CASE_INSENSITIVE_ORDER.compare(messageSender, data.getGameProfile().getName()) != 0) continue;
                    callback.accept(data.getGameProfile(), messageOnly);
                    return true;
                }
            }
        }
        catch (Exception e) {
            return false;
        }
        return false;
    }

    @SubscribeEvent
    public void onChatMessage(PacketEvent.Receive event) {
        try {
            SPacketChat packet;
            String message;
            if (event.getPacket() instanceof SPacketChat && !Strings.isNullOrEmpty((String)(message = (packet = (SPacketChat)event.getPacket()).getChatComponent().getUnformattedText()))) {
                if (EventMod.extract(message, MESSAGE_PATTERNS, (senderProfile, msg) -> PlayerInfoHelper.invokeEfficiently(senderProfile.getName(), new FutureCallback<PlayerInfo>(){

                    public void onSuccess(@Nullable PlayerInfo result) {
                        if (result != null) {
                            MinecraftForge.EVENT_BUS.post((Event)ChatMessageEvent.newPublicChat(result, msg));
                        }
                    }

                    public void onFailure(Throwable t) {
                    }
                }))) {
                    return;
                }
                if (EventMod.extract(message, INCOMING_PRIVATE_MESSAGES, (senderProfile, msg) -> PlayerInfoHelper.invokeEfficiently(senderProfile.getName(), new FutureCallback<PlayerInfo>(){

                    public void onSuccess(final @Nullable PlayerInfo sender) {
                        if (sender != null) {
                            PlayerInfoHelper.invokeEfficiently(Globals.MC.player.getName(), new FutureCallback<PlayerInfo>(){

                                public void onSuccess(@Nullable PlayerInfo result) {
                                    if (result != null) {
                                        MinecraftForge.EVENT_BUS.post((Event)ChatMessageEvent.newPrivateChat(sender, result, msg));
                                    }
                                }

                                public void onFailure(Throwable t) {
                                }
                            });
                        }
                    }

                    public void onFailure(Throwable t) {
                    }
                }))) {
                    return;
                }
                if (EventMod.extract(message, OUTGOING_PRIVATE_MESSAGES, (receiverProfile, msg) -> PlayerInfoHelper.invokeEfficiently(receiverProfile.getName(), new FutureCallback<PlayerInfo>(){

                    public void onSuccess(final @Nullable PlayerInfo receiver) {
                        if (receiver != null) {
                            PlayerInfoHelper.invokeEfficiently(Globals.MC.player.getName(), new FutureCallback<PlayerInfo>(){

                                public void onSuccess(@Nullable PlayerInfo sender) {
                                    if (sender != null) {
                                        MinecraftForge.EVENT_BUS.post((Event)ChatMessageEvent.newPrivateChat(sender, receiver, msg));
                                    }
                                }

                                public void onFailure(Throwable t) {
                                }
                            });
                        }
                    }

                    public void onFailure(Throwable t) {
                    }
                }))) {
                    return;
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @SubscribeEvent
    public void onStartModule(TickEvent.ClientTickEvent event) {
        for (Module module : ModuleManager.getMods()) {
            if (!(module instanceof ToggleMod)) continue;
            if (((ToggleMod)module).toggled.getBoolean()) {
                module.start();
                continue;
            }
            module.stop();
        }
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        for (Module module : ModuleManager.getMods()) {
            if (!(module instanceof ToggleMod) || module.getBind() == -1 || !Keyboard.isKeyDown((int)module.getBind()) || Keyboard.getEventKey() != module.getBind() || !Keyboard.getEventKeyState()) continue;
            ((ToggleMod)module).toggle();
        }
    }

    @SubscribeEvent
    public void sendConstantPacketFlow(PlayerUpdateEvent event) {
        for (Module mod : ModuleManager.getMods()) {
            if (!mod.isSpoofingRotation) continue;
            RotationSpoofing.togglePitch();
        }
    }

    @SubscribeEvent
    public void onRotationPacketSent(PacketEvent event) {
        Packet<?> packet = event.getPacket();
        if (packet instanceof CPacketPlayer) {
            for (Module mod : ModuleManager.getMods()) {
                if (!mod.isSpoofingRotation) continue;
                ((CPacketPlayer)packet).yaw = RotationSpoofing.yaw;
                ((CPacketPlayer)packet).pitch = RotationSpoofing.pitch;
            }
        }
    }
}

