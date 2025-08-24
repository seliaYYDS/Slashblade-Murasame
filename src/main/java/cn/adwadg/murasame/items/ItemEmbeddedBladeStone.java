package cn.adwadg.murasame.items;

import cn.adwadg.murasame.blocks.EmbeddedBladeStoneBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEmbeddedBladeStone extends ItemBlock {

    public ItemEmbeddedBladeStone(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack) {
        // 可选：让物品有附魔光效
        return true;
    }
}
