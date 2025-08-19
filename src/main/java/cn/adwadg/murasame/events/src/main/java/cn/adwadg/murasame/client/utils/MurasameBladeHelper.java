package cn.adwadg.murasame.client.utils;

import mods.flammpfeil.slashblade.init.SBItems;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class MurasameBladeHelper {
    public static final ResourceLocation MODEL = new ResourceLocation("murasame", "models/murasame/murasamemaru.obj");
    public static final ResourceLocation TEXTURE = new ResourceLocation("murasame", "models/murasame/murasamemaru.png");
    public static final ResourceLocation SLASH_ART = new ResourceLocation("slashblade", "wave_edge");

    public static ItemStack createMurasameBlade() {
        ItemStack blade = new ItemStack(SBItems.slashblade);

        blade.getCapability(ItemSlashBlade.BLADESTATE).ifPresent(s -> {
            // 设置模型和纹理
            s.setModel(MODEL);
            s.setTexture(TEXTURE);

            // 设置基础属性
            s.setBaseAttackModifier(18.0f); // 攻击力
            s.setMaxDamage(150); // 耐久度
            s.setColorCode(65350); // 召唤剑颜色


            // 设置特殊属性
            s.setTranslationKey("item.murasame.murasamemaru");
            s.setBroken(false);
            s.setSealed(false);
            s.setDefaultBewitched(true);

            // 设置斩技
            s.setSlashArtsKey(SLASH_ART);
        });

        // 添加附魔
        blade.enchant(Enchantments.SHARPNESS, 5);
        blade.enchant(Enchantments.FIRE_ASPECT, 2);

        // 设置自定义名称
        blade.setHoverName(Component.translatable("item.murasame.murasamemaru"));

        return blade;
    }
}
