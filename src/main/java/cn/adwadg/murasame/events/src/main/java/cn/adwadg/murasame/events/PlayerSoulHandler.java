package cn.adwadg.murasame.events;

import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.Registry.ModEntities;
import cn.adwadg.murasame.Entities.EntityMurasameSoul;
import cn.adwadg.murasame.client.utils.MurasameEvolutionTracker;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = Murasame.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerSoulHandler {
    private static final Map<UUID, EntityMurasameSoul> soulMap = new HashMap<>();

    /*@SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            checkAndSpawnSoul(player);
        }
    }*/

    /*@SubscribeEvent
    public static void onPlayerAttack(LivingAttackEvent event) {
        Entity attacker = event.getSource().getDirectEntity();
        if (attacker instanceof Player player) {
            EntityMurasameSoul soul = soulMap.get(player);
            if (soul != null) {
                // 修改为仅隐藏0.5秒（10 ticks）
                soul.hideTemporarily(100);

                // 强制同步状态
                soul.setVisible(false);
            }
        }
    }*/


    private static void spawnSoulEntity(Player player) {
        try {
            // 检查已有实体
            EntityMurasameSoul existing = soulMap.get(player.getUUID());
            if (existing != null && existing.isAlive()) {
                return;
            }
            ServerLevel level = (ServerLevel) player.level();
            EntityMurasameSoul soul = new EntityMurasameSoul(ModEntities.MURASAME_SOUL.get(), level);



            float distance = 3.5f;
            float horizontalOffset = -2.3f;

            // 计算位置
            Vec3 position = null;
            if (player != null) {
                position = soul.calculateLeftFrontPosition(player, distance, horizontalOffset);
            }

            // 更新实体位置
            soul.setPos(position.x, position.y, position.z);

            // 可选：让实体面朝玩家
            if (player != null) {
                soul.lookAt(EntityAnchorArgument.Anchor.EYES, player.getEyePosition());
            }

            // 必须最后设置Owner
            soul.setOwner(player);

            if (level.addFreshEntity(soul)) {
                soulMap.put(player.getUUID(), soul);
            }
        } catch (Exception e) {
            Murasame.LOGGER.error("生成异常", e);
        }
    }

    public static void removeSoul(Player player) {
        EntityMurasameSoul soul = soulMap.remove(player.getUUID());
        if (soul != null && soul.isAlive()) {
            soul.discard();
        }
    }
    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        removeSoul(player);
    }
    @SubscribeEvent void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event){
        Player player = event.getEntity();
        removeSoul(player);
    }
    /*@SubscribeEvent
    public static void onPlayerChangeItem(PlayerInteractEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            //Murasame.LOGGER.debug("玩家交互事件触发，类型: {}", event.getClass().getSimpleName());
            checkAndSpawnSoul(player);
        }
    }*/





    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START &&
                event.player instanceof ServerPlayer player) {

            // 每3 tick检测一次（更快响应）
            if (player.tickCount % 3 == 0) {
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                boolean shouldHave = false;
                if(stack.getItem() instanceof ItemSlashBlade && stack.getCapability(ItemSlashBlade.BLADESTATE)
                        .map(state -> "item.murasame.murasamemaru_awakened".equals(state.getTranslationKey()))
                        .orElse(false)){
                    shouldHave = true;
                }

                EntityMurasameSoul soul = soulMap.get(player.getUUID());

                if (shouldHave) {
                    if (soul == null || !soul.isAlive()) {
                        spawnSoulEntity(player);
                    } /*else {
                        // 强制位置同步
                        soul.setPos(player.getX(), player.getY() + 1.2, player.getZ());
                    }*/
                } else if (soul != null) {
                    removeSoul(player);
                }
            }
        }
    }
}
