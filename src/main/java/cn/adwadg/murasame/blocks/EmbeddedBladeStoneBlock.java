package cn.adwadg.murasame.blocks;

import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.tileentity.TileEntityEmbeddedBladeStone;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EmbeddedBladeStoneBlock extends Block implements ITileEntityProvider {

    public EmbeddedBladeStoneBlock() {
        super(Material.ROCK);
        this.setHardness(3.0F);
        this.setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setCreativeTab(Murasame.creativeTab);

        // 使用原版石头材质
        this.setSoundType(Blocks.STONE.getSoundType());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityEmbeddedBladeStone();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityEmbeddedBladeStone) {
                TileEntityEmbeddedBladeStone stoneEntity = (TileEntityEmbeddedBladeStone) tile;

                // 检查获取条件
                if (!canPlayerGetBlade(player)) {
                    player.sendMessage(new TextComponentTranslation("message.murasame.cannot_get_blade"));
                    return true;
                }

                ItemStack bladeStack = stoneEntity.getBladeStack();

                if (!player.inventory.addItemStackToInventory(bladeStack)) {
                    // 在某个事件或方法中调用
                    world.playSound(player, player.getPosition(), SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    player.dropItem(bladeStack, false);
                }
                // 替换为原版石头方块
                world.setBlockState(pos, Blocks.STONE.getDefaultState());
            }
        }
        return true;
    }

    // 检查玩家是否满足获取条件
    private boolean canPlayerGetBlade(EntityPlayer player) {
        // 条件1: 等级大于等于50
        if (player.experienceLevel < 50) {
            return false;
        }

        // 条件2: 拥有5种正面增益效果
        List<PotionEffect> positiveEffects = new ArrayList<>();
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (isPositiveEffect(effect)) {
                positiveEffects.add(effect);
            }
        }
        if (positiveEffects.size() < 5) {
            return false;
        }

        // 条件3: 击杀过500个亡灵生物
        // 这里需要实现击杀计数，可以使用能力系统或NBT存储
        int undeadKills = getUndeadKillCount(player);
        if (undeadKills < 500) {
            return false;
        }

        return true;
    }

    // 判断是否为正面效果
    private boolean isPositiveEffect(PotionEffect effect) {
        return effect.getPotion().isBeneficial();
    }

    // 获取亡灵生物击杀数（需要实现存储逻辑）
    private int getUndeadKillCount(EntityPlayer player) {
        // 这里可以读取玩家NBT数据中的击杀计数
        // 实际实现需要配合事件监听器来统计击杀数
        return player.getEntityData().getInteger("murasame_undead_kills");
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }
}
