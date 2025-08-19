package cn.adwadg.murasame.client.utils;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "yourmodid", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CombatTracker {

    private static final Map<UUID, Integer> playerCombatTimers = new HashMap<>();
    private static final int COMBAT_DURATION = 20 * 10; // 10秒战斗状态

    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        Player player = event.getEntity();
        playerCombatTimers.put(player.getUUID(), COMBAT_DURATION);
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            playerCombatTimers.put(player.getUUID(), COMBAT_DURATION);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            // 每tick减少计时器
            playerCombatTimers.entrySet().removeIf(entry -> {
                int newTime = entry.getValue() - 1;
                if (newTime <= 0) {
                    return true; // 移除计时器为0的玩家
                }
                entry.setValue(newTime);
                return false;
            });
        }
    }

    public static boolean isInCombat(Player player) {
        return playerCombatTimers.containsKey(player.getUUID());
    }

    public static int getCombatTimeLeft(Player player) {
        return playerCombatTimers.getOrDefault(player.getUUID(), 0);
    }
}
