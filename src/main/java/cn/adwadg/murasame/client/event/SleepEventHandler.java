package cn.adwadg.murasame.client.event;

import cn.adwadg.murasame.util.MurasameEvolutionTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SleepEventHandler {

    @SubscribeEvent
    public void onPlayerWakeUp(PlayerWakeUpEvent event) {
        EntityPlayer player = event.getEntityPlayer();

        // 检查主手物品
        ItemStack mainHand = player.getHeldItemMainhand();
        if (MurasameEvolutionTracker.isValidBlade(mainHand)) {
            MurasameEvolutionTracker.checkEvolution(player, mainHand);
            return;
        }

        // 检查副手物品
        ItemStack offHand = player.getHeldItemOffhand();
        if (MurasameEvolutionTracker.isValidBlade(offHand)) {
            MurasameEvolutionTracker.checkEvolution(player, offHand);
        }
    }
}
