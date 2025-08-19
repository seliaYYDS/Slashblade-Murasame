package cn.adwadg.murasame.TileEntity.BlockEntity;

import cn.adwadg.murasame.Registry.ModEntities;
import cn.adwadg.murasame.Registry.ModItems;
import cn.adwadg.murasame.client.utils.MurasameBladeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EmbeddedBladeStoneEntity extends BlockEntity {
    private final ItemStack bladeStack;

    public EmbeddedBladeStoneEntity(BlockPos pos, BlockState state) {
        super(ModEntities.EMBEDDED_STONE_ENTITY.get(), pos, state);
        this.bladeStack = MurasameBladeHelper.createMurasameBlade();
    }

    /*private ItemStack createMurasameBlade() {
        ItemStack blade = new ItemStack(SBItems.slashblade);
        blade.getCapability(ItemSlashBlade.BLADESTATE).ifPresent(s -> {
            // 设置模型和纹理
            s.setModel(new ResourceLocation("murasame", "models/murasame/murasamemaru.obj"));
            s.setTexture(new ResourceLocation("murasame", "models/murasame/murasamemaru.png"));

            // 设置基础属性
            s.setBaseAttackModifier(18.0f); // 与JSON中的attack_base一致
            s.setMaxDamage(150); // 与JSON中的max_damage一致
            s.setColorCode(65350); // 与JSON中的summon_sword_color一致

            // 设置特殊属性
            s.setTranslationKey("item.murasame.murasamemaru");
            s.setBroken(false);
            s.setSealed(false);
            s.setDefaultBewitched(true);

            // 设置斩技
            s.setSlashArtsKey(new ResourceLocation("slashblade", "wave_edge"));
        });

        // 添加附魔 (与JSON中的enchantments一致)
        blade.enchant(Enchantments.SHARPNESS, 5);
        blade.enchant(Enchantments.FIRE_ASPECT, 2);

        // 设置自定义名称
        blade.setHoverName(Component.translatable("item.murasame.murasamemaru"));

        return blade;
    }*/

    public ItemStack getBladeStack() {
        return bladeStack.copy();
    }
}
