package cn.adwadg.murasame.client.event;

import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerSoulHandler {

    private static final Map<UUID, Long> lastSoulSpawnTime = new HashMap<>();

    // 移除所有刀魂生成相关的代码，只保留音效功能
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // 可以保留一些音效触发逻辑，但移除实体生成
        if (event.phase == TickEvent.Phase.END &&
                event.player != null &&
                isHoldingAwakenedMurasame(event.player)) {

            // 只处理音效，不生成实体
            handleSoulSounds(event.player);
        }
    }

    private void handleSoulSounds(EntityPlayer player) {
        // 这里可以添加一些音效触发逻辑
        // 例如：低血量音效、战斗音效等
    }

    // 保留这些工具方法供SoundEventHandler使用
    public static boolean isHoldingAwakenedMurasame(EntityPlayer player) {
        return isAwakenedMurasame(player.getHeldItemMainhand()) ||
                isAwakenedMurasame(player.getHeldItemOffhand());
    }

    public static boolean isAwakenedMurasame(ItemStack stack) {
        if (stack.getItem() instanceof ItemSlashBlade && stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            String currentName = ItemSlashBladeNamed.CurrentItemName.get(tag);
            return "flammpfeil.slashblade.named.murasamemaru_awakened".equals(currentName);
        }
        return false;
    }
}
