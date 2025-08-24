package cn.adwadg.murasame.util;

import cn.adwadg.murasame.client.event.SoundEventHandler;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;

public class MurasameEvolutionTracker {

    // NBT标签
    private static final String EVOLVED_TAG = "murasame_evolved";

    // 进化条件
    public static final int REQUIRED_KILLS = 10;
    public static final int REQUIRED_SOULS = 50;

    public static void checkEvolution(EntityPlayer player, ItemStack stack) {
        if (!isValidBlade(stack)) return;

        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) return;

        int kills = ItemSlashBlade.KillCount.get(tag);
        int souls = ItemSlashBlade.ProudSoul.get(tag);

        if (kills >= REQUIRED_KILLS && souls >= REQUIRED_SOULS) {
            evolveBlade(player, stack, tag);
        }
    }

    private static void evolveBlade(EntityPlayer player, ItemStack stack, NBTTagCompound tag) {
        SoundEventHandler.playRandomSound(player,SoundEventHandler.GREETING_SOUNDS,1.0F,1.0F);

        // 更新为觉醒版本
        ItemSlashBladeNamed.CurrentItemName.set(tag, "flammpfeil.slashblade.named.murasamemaru_awakened");
        ItemSlashBladeNamed.CustomMaxDamage.set(tag, 300);
        ItemSlashBlade.setBaseAttackModifier(tag, 35.0F);
        ItemSlashBlade.TextureName.set(tag, "named/murasame/murasamemaru_awakened");
        ItemSlashBlade.ModelName.set(tag, "named/murasame/murasamemaru_awakened");

        // 添加附魔
        stack.addEnchantment(Enchantments.SMITE, 25);
        stack.addEnchantment(Enchantments.SHARPNESS, 10);
        stack.addEnchantment(Enchantments.FIRE_ASPECT, 3);
        stack.addEnchantment(Enchantments.UNBREAKING, 10);

        // 标记为已进化
        stack.getTagCompound().setBoolean(EVOLVED_TAG, true);

        // 通知玩家
        player.world.playSound(null, player.posX, player.posY, player.posZ,
                SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.PLAYERS, 0.5F, 1.2F);

        player.sendMessage(new TextComponentTranslation("message.murasame.evolved"));
    }

    public static boolean isValidBlade(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemSlashBlade) || isEvolved(stack)) {
            return false;
        }

        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) return false;

        String currentName = ItemSlashBladeNamed.CurrentItemName.get(tag);
        return "flammpfeil.slashblade.named.murasamemaru".equals(currentName);
    }

    private static boolean isEvolved(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().getBoolean(EVOLVED_TAG);
    }
}
