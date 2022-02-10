/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.client.CPacketAnimation
 *  net.minecraft.network.play.client.CPacketChatMessage
 *  net.minecraft.network.play.client.CPacketClickWindow
 *  net.minecraft.network.play.client.CPacketClientSettings
 *  net.minecraft.network.play.client.CPacketClientStatus
 *  net.minecraft.network.play.client.CPacketCloseWindow
 *  net.minecraft.network.play.client.CPacketConfirmTeleport
 *  net.minecraft.network.play.client.CPacketConfirmTransaction
 *  net.minecraft.network.play.client.CPacketCreativeInventoryAction
 *  net.minecraft.network.play.client.CPacketCustomPayload
 *  net.minecraft.network.play.client.CPacketEnchantItem
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketHeldItemChange
 *  net.minecraft.network.play.client.CPacketInput
 *  net.minecraft.network.play.client.CPacketKeepAlive
 *  net.minecraft.network.play.client.CPacketPlaceRecipe
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayerAbilities
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.network.play.client.CPacketRecipeInfo
 *  net.minecraft.network.play.client.CPacketResourcePackStatus
 *  net.minecraft.network.play.client.CPacketSeenAdvancements
 *  net.minecraft.network.play.client.CPacketSpectate
 *  net.minecraft.network.play.client.CPacketSteerBoat
 *  net.minecraft.network.play.client.CPacketTabComplete
 *  net.minecraft.network.play.client.CPacketUpdateSign
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketVehicleMove
 *  net.minecraft.network.play.server.SPacketAdvancementInfo
 *  net.minecraft.network.play.server.SPacketAnimation
 *  net.minecraft.network.play.server.SPacketBlockAction
 *  net.minecraft.network.play.server.SPacketBlockBreakAnim
 *  net.minecraft.network.play.server.SPacketBlockChange
 *  net.minecraft.network.play.server.SPacketCamera
 *  net.minecraft.network.play.server.SPacketChangeGameState
 *  net.minecraft.network.play.server.SPacketChat
 *  net.minecraft.network.play.server.SPacketChunkData
 *  net.minecraft.network.play.server.SPacketCloseWindow
 *  net.minecraft.network.play.server.SPacketCollectItem
 *  net.minecraft.network.play.server.SPacketCombatEvent
 *  net.minecraft.network.play.server.SPacketConfirmTransaction
 *  net.minecraft.network.play.server.SPacketCooldown
 *  net.minecraft.network.play.server.SPacketCustomPayload
 *  net.minecraft.network.play.server.SPacketCustomSound
 *  net.minecraft.network.play.server.SPacketDestroyEntities
 *  net.minecraft.network.play.server.SPacketDisconnect
 *  net.minecraft.network.play.server.SPacketDisplayObjective
 *  net.minecraft.network.play.server.SPacketEffect
 *  net.minecraft.network.play.server.SPacketEntity
 *  net.minecraft.network.play.server.SPacketEntityAttach
 *  net.minecraft.network.play.server.SPacketEntityEffect
 *  net.minecraft.network.play.server.SPacketEntityEquipment
 *  net.minecraft.network.play.server.SPacketEntityHeadLook
 *  net.minecraft.network.play.server.SPacketEntityMetadata
 *  net.minecraft.network.play.server.SPacketEntityProperties
 *  net.minecraft.network.play.server.SPacketEntityStatus
 *  net.minecraft.network.play.server.SPacketEntityTeleport
 *  net.minecraft.network.play.server.SPacketEntityVelocity
 *  net.minecraft.network.play.server.SPacketExplosion
 *  net.minecraft.network.play.server.SPacketHeldItemChange
 *  net.minecraft.network.play.server.SPacketJoinGame
 *  net.minecraft.network.play.server.SPacketKeepAlive
 *  net.minecraft.network.play.server.SPacketMaps
 *  net.minecraft.network.play.server.SPacketMoveVehicle
 *  net.minecraft.network.play.server.SPacketMultiBlockChange
 *  net.minecraft.network.play.server.SPacketOpenWindow
 *  net.minecraft.network.play.server.SPacketParticles
 *  net.minecraft.network.play.server.SPacketPlaceGhostRecipe
 *  net.minecraft.network.play.server.SPacketPlayerAbilities
 *  net.minecraft.network.play.server.SPacketPlayerListHeaderFooter
 *  net.minecraft.network.play.server.SPacketPlayerListItem
 *  net.minecraft.network.play.server.SPacketPlayerPosLook
 *  net.minecraft.network.play.server.SPacketRecipeBook
 *  net.minecraft.network.play.server.SPacketRemoveEntityEffect
 *  net.minecraft.network.play.server.SPacketResourcePackSend
 *  net.minecraft.network.play.server.SPacketRespawn
 *  net.minecraft.network.play.server.SPacketScoreboardObjective
 *  net.minecraft.network.play.server.SPacketSelectAdvancementsTab
 *  net.minecraft.network.play.server.SPacketServerDifficulty
 *  net.minecraft.network.play.server.SPacketSetExperience
 *  net.minecraft.network.play.server.SPacketSetPassengers
 *  net.minecraft.network.play.server.SPacketSetSlot
 *  net.minecraft.network.play.server.SPacketSignEditorOpen
 *  net.minecraft.network.play.server.SPacketSoundEffect
 *  net.minecraft.network.play.server.SPacketSpawnExperienceOrb
 *  net.minecraft.network.play.server.SPacketSpawnGlobalEntity
 *  net.minecraft.network.play.server.SPacketSpawnMob
 *  net.minecraft.network.play.server.SPacketSpawnObject
 *  net.minecraft.network.play.server.SPacketSpawnPainting
 *  net.minecraft.network.play.server.SPacketSpawnPlayer
 *  net.minecraft.network.play.server.SPacketSpawnPosition
 *  net.minecraft.network.play.server.SPacketStatistics
 *  net.minecraft.network.play.server.SPacketTabComplete
 *  net.minecraft.network.play.server.SPacketTeams
 *  net.minecraft.network.play.server.SPacketTimeUpdate
 *  net.minecraft.network.play.server.SPacketTitle
 *  net.minecraft.network.play.server.SPacketUnloadChunk
 *  net.minecraft.network.play.server.SPacketUpdateBossInfo
 *  net.minecraft.network.play.server.SPacketUpdateHealth
 *  net.minecraft.network.play.server.SPacketUpdateScore
 *  net.minecraft.network.play.server.SPacketUpdateTileEntity
 *  net.minecraft.network.play.server.SPacketUseBed
 *  net.minecraft.network.play.server.SPacketWindowItems
 *  net.minecraft.network.play.server.SPacketWindowProperty
 *  net.minecraft.network.play.server.SPacketWorldBorder
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods;

import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.base.mod.Category;
import com.client.glowclient.utils.base.mod.branches.ToggleMod;
import com.client.glowclient.utils.base.setting.SettingUtils;
import com.client.glowclient.utils.base.setting.branches.SettingBoolean;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlaceRecipe;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketSeenAdvancements;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketAdvancementInfo;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketBlockAction;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketCamera;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketCollectItem;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import net.minecraft.network.play.server.SPacketCooldown;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.network.play.server.SPacketDisplayObjective;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.network.play.server.SPacketKeepAlive;
import net.minecraft.network.play.server.SPacketMaps;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketPlaceGhostRecipe;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketRecipeBook;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketScoreboardObjective;
import net.minecraft.network.play.server.SPacketSelectAdvancementsTab;
import net.minecraft.network.play.server.SPacketServerDifficulty;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketSignEditorOpen;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.network.play.server.SPacketSpawnPosition;
import net.minecraft.network.play.server.SPacketStatistics;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.network.play.server.SPacketTeams;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.network.play.server.SPacketUnloadChunk;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.network.play.server.SPacketUpdateScore;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.network.play.server.SPacketWindowProperty;
import net.minecraft.network.play.server.SPacketWorldBorder;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiPackets
extends ToggleMod {
    public static final SettingBoolean AnimationN = SettingUtils.settingBoolean("AntiPackets", "Animation", "Cancel CPacketAnimation", false);
    public static final SettingBoolean ChatMessage = SettingUtils.settingBoolean("AntiPackets", "ChatMessage", "Cancel CPacketChatMessage", false);
    public static final SettingBoolean ClickWindow = SettingUtils.settingBoolean("AntiPackets", "ClickWindow", "Cancel CPacketClickWindow", false);
    public static final SettingBoolean ClientSettings = SettingUtils.settingBoolean("AntiPackets", "ClientSettings", "Cancel CPacketClientSettings", false);
    public static final SettingBoolean ClientStatus = SettingUtils.settingBoolean("AntiPackets", "ClientStatus", "Cancel CPacketClientStatus", false);
    public static final SettingBoolean CloseWindow = SettingUtils.settingBoolean("AntiPackets", "CloseWindow", "Cancel CPacketCloseWindow", false);
    public static final SettingBoolean ConfirmTeleport = SettingUtils.settingBoolean("AntiPackets", "ConfirmTeleport", "Cancel CPacketConfirmTeleport", false);
    public static final SettingBoolean ConfirmTransaction = SettingUtils.settingBoolean("AntiPackets", "ConfirmTransaction", "Cancel CPacketConfirmTransaction", false);
    public static final SettingBoolean CreativeInventoryAction = SettingUtils.settingBoolean("AntiPackets", "CreativeInventoryAction", "Cancel CPacketCreativeInventoryAction", false);
    public static final SettingBoolean CustomPayload = SettingUtils.settingBoolean("AntiPackets", "CustomPayload", "Cancel CPacketCustomPayload", false);
    public static final SettingBoolean EnchantItem = SettingUtils.settingBoolean("AntiPackets", "EnchantItem", "Cancel CPacketEnchantItem", false);
    public static final SettingBoolean EntityAction = SettingUtils.settingBoolean("AntiPackets", "EntityAction", "Cancel CPacketEntityAction", false);
    public static final SettingBoolean HeldItemChange = SettingUtils.settingBoolean("AntiPackets", "HeldItemChange", "Cancel CPacketHeldItemChange", false);
    public static final SettingBoolean Input = SettingUtils.settingBoolean("AntiPackets", "Input", "Cancel CPacketInput", false);
    public static final SettingBoolean KeepAlive = SettingUtils.settingBoolean("AntiPackets", "KeepAlive", "Cancel CPacketKeepAlive", false);
    public static final SettingBoolean PlaceRecipe = SettingUtils.settingBoolean("AntiPackets", "PlaceRecipe", "Cancel CPacketPlaceRecipe", false);
    public static final SettingBoolean Player = SettingUtils.settingBoolean("AntiPackets", "Player", "Cancel CPacketPlayer", false);
    public static final SettingBoolean PlayerAbilities = SettingUtils.settingBoolean("AntiPackets", "PlayerAbilities", "Cancel CPacketPlayerAbilities", false);
    public static final SettingBoolean PlayerDigging = SettingUtils.settingBoolean("AntiPackets", "PlayerDigging", "Cancel CPacketPlayerDigging", false);
    public static final SettingBoolean PlayerTryUseItem = SettingUtils.settingBoolean("AntiPackets", "PlayerTryUseItem", "Cancel CPacketPlayerTryUseItem", false);
    public static final SettingBoolean PlayerTryUseItemOnBlock = SettingUtils.settingBoolean("AntiPackets", "PlayerTryUseItemOnBlock", "Cancel CPacketPlayerTryUseItemOnBlock", false);
    public static final SettingBoolean RecipeInfo = SettingUtils.settingBoolean("AntiPackets", "RecipeInfo", "Cancel CPacketRecipeInfo", false);
    public static final SettingBoolean ResourcePackStatus = SettingUtils.settingBoolean("AntiPackets", "ResourcePackStatus", "Cancel CPacketResourcePackStatus", false);
    public static final SettingBoolean SeenAdvancements = SettingUtils.settingBoolean("AntiPackets", "SeenAdvancements", "Cancel CPacketSeenAdvancements", false);
    public static final SettingBoolean Spectate = SettingUtils.settingBoolean("AntiPackets", "Spectate", "Cancel CPacketSpectate", false);
    public static final SettingBoolean SteerBoat = SettingUtils.settingBoolean("AntiPackets", "SteerBoat", "Cancel CPacketSteerBoat", false);
    public static final SettingBoolean TabComplete = SettingUtils.settingBoolean("AntiPackets", "TabComplete", "Cancel CPacketTabComplete", false);
    public static final SettingBoolean UpdateSign = SettingUtils.settingBoolean("AntiPackets", "UpdateSign", "Cancel CPacketUpdateSign", false);
    public static final SettingBoolean UseEntity = SettingUtils.settingBoolean("AntiPackets", "UseEntity", "Cancel CPacketUseEntity", false);
    public static final SettingBoolean VehicleMove = SettingUtils.settingBoolean("AntiPackets", "VehicleMove", "Cancel CPacketVehicleMove", false);
    public static final SettingBoolean AdvancementInfo = SettingUtils.settingBoolean("AntiPacketsServer", "AdvancementInfo", "Cancel SPacketAdvancementInfo", false);
    public static final SettingBoolean Animation = SettingUtils.settingBoolean("AntiPacketsServer", "Animation", "Cancel SPacketAnimation", false);
    public static final SettingBoolean BlockAction = SettingUtils.settingBoolean("AntiPacketsServer", "BlockAction", "Cancel SPacketBlockAction", false);
    public static final SettingBoolean BlockBreakAnim = SettingUtils.settingBoolean("AntiPacketsServer", "BlockBreakAnim", "Cancel SPacketBlockBreakAnim", false);
    public static final SettingBoolean BlockChange = SettingUtils.settingBoolean("AntiPacketsServer", "BlockChange", "Cancel SPacketBlockChange", false);
    public static final SettingBoolean Camera = SettingUtils.settingBoolean("AntiPacketsServer", "Camera", "Cancel SPacketCamera", false);
    public static final SettingBoolean ChangeGameState = SettingUtils.settingBoolean("AntiPacketsServer", "ChangeGameState", "Cancel SPacketChangeGameState", false);
    public static final SettingBoolean Chat = SettingUtils.settingBoolean("AntiPacketsServer", "Chat", "Cancel SPacketChat", false);
    public static final SettingBoolean ChunkData = SettingUtils.settingBoolean("AntiPacketsServer", "ChunkData", "Cancel SPacketChunkData", false);
    public static final SettingBoolean CloseWindow2 = SettingUtils.settingBoolean("AntiPacketsServer", "CloseWindow", "Cancel SPacketCloseWindow", false);
    public static final SettingBoolean CollectItem = SettingUtils.settingBoolean("AntiPacketsServer", "CollectItem", "Cancel SPacketCollectItem", false);
    public static SettingBoolean CombatEvent = SettingUtils.settingPacketServer("CombatEvent");
    public static SettingBoolean ConfirmTransaction2 = SettingUtils.settingPacketServer("ConfirmTransaction");
    public static SettingBoolean Cooldown = SettingUtils.settingPacketServer("Cooldown");
    public static SettingBoolean CustomPayload2 = SettingUtils.settingPacketServer("CustomPayload");
    public static SettingBoolean CustomSound = SettingUtils.settingPacketServer("CustomSound");
    public static SettingBoolean DestroyEntities = SettingUtils.settingPacketServer("DestroyEntities");
    public static SettingBoolean Disconnect = SettingUtils.settingPacketServer("Disconnect");
    public static SettingBoolean DisplayObjective = SettingUtils.settingPacketServer("DisplayObjective");
    public static SettingBoolean Effect = SettingUtils.settingPacketServer("Effect");
    public static SettingBoolean Entity = SettingUtils.settingPacketServer("Entity");
    public static SettingBoolean EntityAttach = SettingUtils.settingPacketServer("EntityAttach");
    public static SettingBoolean EntityEffect = SettingUtils.settingPacketServer("EntityEffect");
    public static SettingBoolean EntityEquipment = SettingUtils.settingPacketServer("EntityEquipment");
    public static SettingBoolean EntityHeadLook = SettingUtils.settingPacketServer("EntityHeadLook");
    public static SettingBoolean EntityMetadata = SettingUtils.settingPacketServer("EntityMetadata");
    public static SettingBoolean EntityProperties = SettingUtils.settingPacketServer("EntityProperties");
    public static SettingBoolean EntityStatus = SettingUtils.settingPacketServer("EntityStatus");
    public static SettingBoolean EntityTeleport = SettingUtils.settingPacketServer("EntityTeleport");
    public static SettingBoolean EntityVelocity = SettingUtils.settingPacketServer("EntityVelocity");
    public static SettingBoolean Explosion = SettingUtils.settingPacketServer("Explosion");
    public static SettingBoolean HeldItemChange2 = SettingUtils.settingPacketServer("HeldIteMC.ange");
    public static SettingBoolean JoinGame = SettingUtils.settingPacketServer("JoinGame");
    public static SettingBoolean KeepAlive2 = SettingUtils.settingPacketServer("KeepAlive");
    public static SettingBoolean Maps = SettingUtils.settingPacketServer("Maps");
    public static SettingBoolean MoveVehicle = SettingUtils.settingPacketServer("MoveVehicle");
    public static SettingBoolean MultiBlockChange = SettingUtils.settingPacketServer("MultiBlockChange");
    public static SettingBoolean OpenWindow = SettingUtils.settingPacketServer("OpenWindow");
    public static SettingBoolean Particles = SettingUtils.settingPacketServer("Particles");
    public static SettingBoolean PlaceGhostRecipe = SettingUtils.settingPacketServer("PlaceGhostRecipe");
    public static SettingBoolean PlayerAbilities2 = SettingUtils.settingPacketServer("PlayerAbilities");
    public static SettingBoolean PlayerListHeaderFooter = SettingUtils.settingPacketServer("PlayerListHeaderFooter");
    public static SettingBoolean PlayerListItem = SettingUtils.settingPacketServer("PlayerListItem");
    public static SettingBoolean PlayerPosLook = SettingUtils.settingPacketServer("PlayerPosLook");
    public static SettingBoolean RecipeBook = SettingUtils.settingPacketServer("RecipeBook");
    public static SettingBoolean RemoveEntityEffect = SettingUtils.settingPacketServer("RemoveEntityEffect");
    public static SettingBoolean ResourcePackSend = SettingUtils.settingPacketServer("ResourcePackSend");
    public static SettingBoolean Respawn = SettingUtils.settingPacketServer("Respawn");
    public static SettingBoolean ScoreboardObjective = SettingUtils.settingPacketServer("ScoreboardObjective");
    public static SettingBoolean SelectAdvancementsTab = SettingUtils.settingPacketServer("SelectAdvancementsTab");
    public static SettingBoolean ServerDifficulty = SettingUtils.settingPacketServer("ServerDifficulty");
    public static SettingBoolean SetExperience = SettingUtils.settingPacketServer("SetExperience");
    public static SettingBoolean SetPassengers = SettingUtils.settingPacketServer("SetPassengers");
    public static SettingBoolean SetSlot = SettingUtils.settingPacketServer("SetSlot");
    public static SettingBoolean SignEditorOpen = SettingUtils.settingPacketServer("SignEditorOpen");
    public static SettingBoolean SoundEffect = SettingUtils.settingPacketServer("SoundEffect");
    public static SettingBoolean SpawnExperienceOrb = SettingUtils.settingPacketServer("SpawnExperienceOrb");
    public static SettingBoolean SpawnGlobalEntity = SettingUtils.settingPacketServer("SpawnGlobalEntity");
    public static SettingBoolean SpawnMob = SettingUtils.settingPacketServer("SpawnMob");
    public static SettingBoolean SpawnObject = SettingUtils.settingPacketServer("SpawnObject");
    public static SettingBoolean SpawnPainting = SettingUtils.settingPacketServer("SpawnPainting");
    public static SettingBoolean SpawnPlayer = SettingUtils.settingPacketServer("SpawnPlayer");
    public static SettingBoolean SpawnPosition = SettingUtils.settingPacketServer("SpawnPosition");
    public static SettingBoolean Statistics = SettingUtils.settingPacketServer("Statistics");
    public static SettingBoolean TabComplete2 = SettingUtils.settingPacketServer("TabComplete");
    public static SettingBoolean Teams = SettingUtils.settingPacketServer("Teams");
    public static SettingBoolean TimeUpdate = SettingUtils.settingPacketServer("TimeUpdate");
    public static SettingBoolean Title = SettingUtils.settingPacketServer("Title");
    public static SettingBoolean UnloadChunk = SettingUtils.settingPacketServer("UnloadChunk");
    public static SettingBoolean UpdateBossInfo = SettingUtils.settingPacketServer("UpdateBossInfo");
    public static SettingBoolean UpdateHealth = SettingUtils.settingPacketServer("UpdateHealth");
    public static SettingBoolean UpdateScore = SettingUtils.settingPacketServer("UpdateScore");
    public static SettingBoolean UpdateTileEntity = SettingUtils.settingPacketServer("UpdateTileEntity");
    public static SettingBoolean UseBed = SettingUtils.settingPacketServer("UseBed");
    public static SettingBoolean WindowItems = SettingUtils.settingPacketServer("WindowItems");
    public static SettingBoolean WindowProperty = SettingUtils.settingPacketServer("WindowProperty");
    public static SettingBoolean WorldBorder = SettingUtils.settingPacketServer("WorldBorder");

    public AntiPackets() {
        super(Category.SERVER, "AntiPackets", false, -1, "Cancel specific packets");
    }

    @SubscribeEvent
    public void clientPackets(PacketEvent event) {
        if (event.getPacket() instanceof CPacketAnimation && AnimationN.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketChatMessage && ChatMessage.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketClickWindow && ClickWindow.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketClientSettings && ClientSettings.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketClientStatus && ClientStatus.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketCloseWindow && CloseWindow.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketConfirmTeleport && ConfirmTeleport.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketConfirmTransaction && ConfirmTransaction.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketCreativeInventoryAction && CreativeInventoryAction.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketCustomPayload && CustomPayload.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketEnchantItem && EnchantItem.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketEntityAction && EntityAction.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketHeldItemChange && HeldItemChange.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketInput && Input.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketKeepAlive && KeepAlive.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlaceRecipe && PlaceRecipe.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlayer && Player.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlayerAbilities && PlayerAbilities.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlayerDigging && PlayerDigging.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlayerTryUseItem && PlayerTryUseItem.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && PlayerTryUseItemOnBlock.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketRecipeInfo && RecipeInfo.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketResourcePackStatus && ResourcePackStatus.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketSeenAdvancements && SeenAdvancements.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketSpectate && Spectate.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketSteerBoat && SteerBoat.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketTabComplete && TabComplete.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketUpdateSign && UpdateSign.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketUseEntity && UseEntity.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketVehicleMove && VehicleMove.getBoolean()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void serverPackets(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketAdvancementInfo && AdvancementInfo.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketAnimation && Animation.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketBlockAction && BlockAction.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketBlockBreakAnim && BlockBreakAnim.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketBlockChange && BlockChange.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketCamera && Camera.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketChangeGameState && ChangeGameState.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketChat && Chat.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketChunkData && ChunkData.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketCloseWindow && CloseWindow2.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketCollectItem && CollectItem.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketCombatEvent && CombatEvent.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketConfirmTransaction && ConfirmTransaction2.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketCooldown && Cooldown.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketCustomPayload && CustomPayload2.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketCustomSound && CustomSound.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketDestroyEntities && DestroyEntities.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketDisconnect && Disconnect.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketDisplayObjective && DisplayObjective.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEffect && Effect.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntity && Entity.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntityAttach && EntityAttach.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntityEffect && EntityEffect.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntityEquipment && EntityEquipment.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntityHeadLook && EntityHeadLook.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntityMetadata && EntityMetadata.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntityProperties && EntityProperties.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntityStatus && EntityStatus.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntityTeleport && EntityTeleport.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntityVelocity && EntityVelocity.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketExplosion && Explosion.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketHeldItemChange && HeldItemChange2.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketJoinGame && JoinGame.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketKeepAlive && KeepAlive2.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketMaps && Maps.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketMoveVehicle && MoveVehicle.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketMultiBlockChange && MultiBlockChange.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketOpenWindow && OpenWindow.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketParticles && Particles.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketPlaceGhostRecipe && PlaceGhostRecipe.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketPlayerAbilities && PlayerAbilities2.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketPlayerListHeaderFooter && PlayerListHeaderFooter.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketPlayerListItem && PlayerListItem.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketPlayerPosLook && PlayerPosLook.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketRecipeBook && RecipeBook.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketRemoveEntityEffect && RemoveEntityEffect.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketResourcePackSend && ResourcePackSend.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketRespawn && Respawn.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketScoreboardObjective && ScoreboardObjective.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSelectAdvancementsTab && SelectAdvancementsTab.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketServerDifficulty && ServerDifficulty.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSetExperience && SetExperience.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSetPassengers && SetPassengers.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSetSlot && SetSlot.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSignEditorOpen && SignEditorOpen.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSoundEffect && SoundEffect.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSpawnExperienceOrb && SpawnExperienceOrb.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSpawnGlobalEntity && SpawnGlobalEntity.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSpawnMob && SpawnMob.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSpawnObject && SpawnObject.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSpawnPainting && SpawnPainting.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSpawnPlayer && SpawnPlayer.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketSpawnPosition && SpawnPosition.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketStatistics && Statistics.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketTabComplete && TabComplete2.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketTeams && Teams.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketTimeUpdate && TimeUpdate.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketTitle && Title.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketUnloadChunk && UnloadChunk.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketUpdateBossInfo && UpdateBossInfo.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketUpdateHealth && UpdateHealth.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketUpdateScore && UpdateScore.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketUpdateTileEntity && UpdateTileEntity.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketUseBed && UseBed.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketWindowItems && WindowItems.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketWindowProperty && WindowProperty.getBoolean()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketWorldBorder && WorldBorder.getBoolean()) {
            event.setCanceled(true);
        }
    }
}

