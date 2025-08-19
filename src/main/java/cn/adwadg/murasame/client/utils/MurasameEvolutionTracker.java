package cn.adwadg.murasame.client.utils;

import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.Registry.SoundRegistry;
import cn.adwadg.murasame.events.PlayerEventHandler;
import cn.adwadg.murasame.events.SoundEventHandler;
import mods.flammpfeil.slashblade.capability.slashblade.ISlashBladeState;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MurasameEvolutionTracker {

    // NBT标签
    private static final String EVOLVED_TAG = "murasame_evolved";

    // 进化条件
    public static final int REQUIRED_KILLS = 1000;
    public static final int REQUIRED_SOULS = 50000; // 新条件：Proud Soul数量

    public static void checkEvolution(ServerPlayer player, ItemStack stack) {
        if (!isValidBlade(stack)) return;

        stack.getCapability(ItemSlashBlade.BLADESTATE).ifPresent(state -> {
            int kills = state.getKillCount();
            int souls = state.getProudSoulCount();


            if (kills >= REQUIRED_KILLS && souls >= REQUIRED_SOULS) {
                evolveBlade(player, stack, state);
            }
        });
    }

    private static void evolveBlade(ServerPlayer player, ItemStack stack, ISlashBladeState state) {
        // 更新模型和属性
        state.setModel(new ResourceLocation("murasame", "models/murasame/murasamemaru_awakened.obj"));
        state.setTexture(new ResourceLocation("murasame", "models/murasame/murasamemaru_awakened.png"));
        state.setBaseAttackModifier(40.0f);
        state.setTranslationKey("item.murasame.murasamemaru_awakened");

        // 添加附魔
        stack.enchant(Enchantments.SHARPNESS, 15);
        stack.enchant(Enchantments.FIRE_ASPECT, 3);
        stack.enchant(Enchantments.UNBREAKING, 10);
        stack.enchant(Enchantments.POWER_ARROWS,10);
        stack.enchant(Enchantments.SMITE,25);
        stack.setHoverName(Component.translatable("item.murasame.murasamemaru_awakened"));

        // 标记为已进化
        stack.getOrCreateTag().putBoolean(EVOLVED_TAG, true);

        // 通知玩家
        Murasame.playSound(player,SoundRegistry.MURASAME_GREETING.get());

        player.displayClientMessage(Component.translatable("message.murasame.evolved"), true);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 0.5F, 1.2F);
    }

    public static boolean isValidBlade(ItemStack stack) {
        return stack.getItem() instanceof ItemSlashBlade &&
                !isEvolved(stack) &&
                stack.getCapability(ItemSlashBlade.BLADESTATE)
                        .map(state -> "item.murasame.murasamemaru".equals(state.getTranslationKey()))
                        .orElse(false);
    }

    private static boolean isEvolved(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean(EVOLVED_TAG);
    }

}
