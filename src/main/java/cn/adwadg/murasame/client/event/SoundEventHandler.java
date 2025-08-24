package cn.adwadg.murasame.client.event;

import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.registry.SoundRegistry;
import ibxm.Player;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SoundEventHandler {

    private static final Random random = new Random();
    private static final Map<UUID, Integer> lowHpCooldown = new HashMap<>();
    private static final Map<UUID, Integer> frightenedCooldown = new HashMap<>();
    private static final Map<UUID, Integer> afkCooldown = new HashMap<>();
    private static final Map<UUID, Boolean> wasInCombat = new HashMap<>();

    // 音效组 - 每个事件对应的多个音效
    private static final SoundEvent[] MORNING_SOUNDS = {
            SoundRegistry.MURASAME_MORNING,
            SoundRegistry.MURASAME_MORNING2
    };

    private static final SoundEvent[] NIGHT_SOUNDS = {
            SoundRegistry.MURASAME_NIGHT
    };

    private static final SoundEvent[] LOW_HP_SOUNDS = {
            SoundRegistry.MURASAME_LOWHP
    };

    private static final SoundEvent[] FRIGHTENED_SOUNDS = {
            SoundRegistry.MURASAME_FRIGHTENED
    };

    private static final SoundEvent[] DROP_SOUNDS = {
            SoundRegistry.MURASAME_DROP
    };

    private static final SoundEvent[] AFTER_BOSS_SOUNDS = {
            SoundRegistry.MURASAME_AFTER_BOSS
    };

    private static final SoundEvent[] COMFORT_SOUNDS = {
            SoundRegistry.MURASAME_COMFORT
    };

    private static final SoundEvent[] AFK_SOUNDS = {
            SoundRegistry.MURASAME_AFK,
            SoundRegistry.MURASAME_AFK2
    };

    private static final SoundEvent[] SWITCH_SOUNDS = {
            SoundRegistry.MURASAME_SWITCH,
            SoundRegistry.MURASAME_SWITCH2,
            SoundRegistry.MURASAME_SWITCH3,
            SoundRegistry.MURASAME_SWITCH4,
            SoundRegistry.MURASAME_SWITCH5,
            SoundRegistry.MURASAME_SWITCH6,
            SoundRegistry.MURASAME_SWITCH7,
            SoundRegistry.MURASAME_SWITCH8,
            SoundRegistry.MURASAME_SWITCH9
    };

    public static final SoundEvent[] GREETING_SOUNDS = {
            SoundRegistry.MURASAME_GREETING
    };

    private static final SoundEvent[] YELL_SOUNDS = {
            SoundRegistry.MURASAME_YELL
    };

    private static final SoundEvent[] TOUCH_SOUNDS = {
            SoundRegistry.MURASAME_TOUCH
    };

    private static final SoundEvent[] NO_SOUNDS = {
            SoundRegistry.MURASAME_NO
    };

    // 辅助方法：随机播放音效组
    public static void playRandomSound(EntityPlayer player, SoundEvent[] sounds, float volume, float pitch) {
        if (sounds.length > 0) {
            SoundEvent sound = sounds[random.nextInt(sounds.length)];
            player.world.playSound(null, player.posX, player.posY, player.posZ,
                    sound, SoundCategory.PLAYERS, volume, pitch);
        }
    }

    // 辅助方法：随机播放音效组（带冷却）
    private static void playRandomSoundWithCooldown(EntityPlayer player, SoundEvent[] sounds,
                                                    float volume, float pitch, Map<UUID, Integer> cooldownMap, int cooldownTicks) {
        UUID playerId = player.getUniqueID();
        int currentCooldown = cooldownMap.getOrDefault(playerId, 0);

        if (currentCooldown <= 0) {
            playRandomSound(player, sounds, volume, pitch);
            cooldownMap.put(playerId, cooldownTicks);
        } else {
            cooldownMap.put(playerId, currentCooldown - 1);
        }
    }

    @SubscribeEvent
    public void onPlayerSleep(PlayerSleepInBedEvent event){
        EntityPlayer player = event.getEntityPlayer();
        if(isHoldingAwakenedMurasame(player)){
            playRandomSound(player,NIGHT_SOUNDS,1.0F,1.0F);
        }
    }

    @SubscribeEvent
    public void onPlayerWakeUp(PlayerWakeUpEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (isHoldingAwakenedMurasame(player)) {
            playRandomSound(player, MORNING_SOUNDS, 1.0F, 1.0F);
        }
    }
    @SubscribeEvent
    public void onItemToss(ItemTossEvent event) {
        EntityPlayer player = event.getPlayer();
        ItemStack stack = event.getEntityItem().getItem();
        if (isAwakenedMurasame(stack)) {
            playRandomSound(player, DROP_SOUNDS, 1.0F, 1.0F);
        }
    }
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            if (isHoldingAwakenedMurasame(player) && event.getEntityLiving().getMaxHealth() >= 300) {
                playRandomSound(player, AFTER_BOSS_SOUNDS, 1.0F, 1.0F);
            }
        }
    }
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        EntityPlayer player = event.player;
        if (isHoldingAwakenedMurasame(player)) {
            playRandomSound(player, COMFORT_SOUNDS, 1.0F, 1.0F);
        }
    }
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.world.isRemote) {
            EntityPlayer player = event.player;
            UUID playerId = player.getUniqueID();
            if (isHoldingAwakenedMurasame(player)) {
                // 低血量检测
                if (player.getHealth() < player.getMaxHealth() * 0.4f) {
                    playRandomSoundWithCooldown(player, LOW_HP_SOUNDS, 1.0F, 1.0F,
                            lowHpCooldown, 600);
                }
                // 受惊吓音效
                boolean inCombat = player.getLastAttackedEntity() != null &&
                        player.ticksExisted - player.getLastAttackedEntityTime() < 100;
                if (player.getHealth() < player.getMaxHealth() * 0.2f &&
                        wasInCombat.getOrDefault(playerId, false) && !inCombat) {
                    playRandomSoundWithCooldown(player, FRIGHTENED_SOUNDS, 1.0F, 1.0F,
                            frightenedCooldown, 1200);
                }
                wasInCombat.put(playerId, inCombat);
                // AFK检测
                if (player.ticksExisted % 20 == 0) {
                    if (Math.abs(player.posX - player.prevPosX) < 0.01 &&
                            Math.abs(player.posZ - player.prevPosZ) < 0.01) {
                        playRandomSoundWithCooldown(player, AFK_SOUNDS, 1.0F, 1.0F,
                                afkCooldown, 600);
                    }
                }
            }
            checkWeaponSwitch(player);
        }
    }

    private final Map<UUID, Integer> lastHotbarSlotMap = new ConcurrentHashMap<>();
    private final Map<UUID, Boolean> lastHadMurasameMap = new ConcurrentHashMap<>();

    public void cleanupPlayerData(UUID playerId) {
        lastHotbarSlotMap.remove(playerId);
        lastHadMurasameMap.remove(playerId);
        wasInCombat.remove(playerId);
    }

    private void checkWeaponSwitch(EntityPlayer player) {
        if (player == null || player.world == null || player.world.isRemote) {
            return;
        }

        UUID playerId = player.getUniqueID();
        int currentSlot = player.inventory.currentItem;
        boolean currentHasMurasame = isHoldingAwakenedMurasame(player);

        // 获取上一次的状态
        int lastSlot = lastHotbarSlotMap.getOrDefault(playerId, -1);
        boolean lastHadMurasame = lastHadMurasameMap.getOrDefault(playerId, false);

        // 调试信息
        Murasame.LOGGER.debug("玩家 {}: 槽位 {}->{}, 丛雨丸 {}->{}",
                player.getName(), lastSlot, currentSlot, lastHadMurasame, currentHasMurasame);

        // 检测条件1：快捷栏位置变化且当前持有丛雨丸
        boolean slotChanged = lastSlot != currentSlot;
        boolean weaponEquipped = !lastHadMurasame && currentHasMurasame;

        if ((slotChanged && currentHasMurasame) || weaponEquipped) {
            Murasame.LOGGER.info("触发切换音效: 槽位变化={}, 武器装备={}", slotChanged, weaponEquipped);
            playRandomSound(player, SWITCH_SOUNDS, 0.8F, 1.0F);
        }

        // 更新记录
        lastHotbarSlotMap.put(playerId, currentSlot);
        lastHadMurasameMap.put(playerId, currentHasMurasame);
    }

    // 添加物品拾取事件
    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = event.getItem().getItem();

        if (isAwakenedMurasame(stack)) {
            Murasame.LOGGER.info("检测到捡起丛雨丸");
            playRandomSound(player, SWITCH_SOUNDS, 0.8F, 1.0F);
        }
    }

    @SubscribeEvent
    public void onPlayerOffline(PlayerEvent.PlayerLoggedOutEvent event){
        EntityPlayer player = event.player;
        cleanupPlayerData(player.getUniqueID());
    }

    private static boolean isHoldingAwakenedMurasame(EntityPlayer player) {
        if (player == null) {
            return false;
        }
        ItemStack heldItem = player.getHeldItemMainhand();
        return isAwakenedMurasame(heldItem);
    }
    private static boolean isAwakenedMurasame(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ItemSlashBlade)) {
            return false;
        }

        // 安全地获取 NBT 数据
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            return false;
        }

        // 安全地获取当前物品名称
        String currentName = ItemSlashBladeNamed.CurrentItemName.get(tag);
        return "flammpfeil.slashblade.named.murasamemaru_awakened".equals(currentName);
    }
}
