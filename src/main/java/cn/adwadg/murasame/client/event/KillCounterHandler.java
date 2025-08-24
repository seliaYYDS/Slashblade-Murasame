package cn.adwadg.murasame.event;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "murasame")
public class KillCounterHandler {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();

            // 检查是否为亡灵生物
            if (isUndeadMob(event.getEntity())) {
                // 增加击杀计数
                int currentKills = player.getEntityData().getInteger("murasame_undead_kills");
                player.getEntityData().setInteger("murasame_undead_kills", currentKills + 1);
            }
        }
    }

    // 判断是否为亡灵生物
    private static boolean isUndeadMob(net.minecraft.entity.Entity entity) {
        return entity instanceof EntityZombie ||
                entity instanceof EntitySkeleton ||
                (entity instanceof EntityMob && ((EntityMob) entity).isEntityUndead());
    }
}
