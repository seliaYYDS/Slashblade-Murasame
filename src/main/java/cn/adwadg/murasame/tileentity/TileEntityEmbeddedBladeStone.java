package cn.adwadg.murasame.tileentity;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityEmbeddedBladeStone extends TileEntity {

    private ItemStack bladeStack;

    public TileEntityEmbeddedBladeStone() {
        this.bladeStack = createMurasameBlade();
    }

    private ItemStack createMurasameBlade() {
        ItemStack blade = new ItemStack(SlashBlade.bladeNamed);
        NBTTagCompound tag = new NBTTagCompound();
        blade.setTagCompound(tag);

        // 设置村雨刀属性
        ItemSlashBladeNamed.CurrentItemName.set(tag, "flammpfeil.slashblade.named.murasamemaru");
        ItemSlashBladeNamed.CustomMaxDamage.set(tag, 150);
        ItemSlashBlade.setBaseAttackModifier(tag, 18.0F);
        ItemSlashBlade.TextureName.set(tag, "named/murasame/murasamemaru");
        ItemSlashBlade.ModelName.set(tag, "named/murasame/murasamemaru");
        ItemSlashBlade.SpecialAttackType.set(tag, 2);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.SummonedSwordColor.set(tag, 65350);
        ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);

        // 添加附魔
        blade.addEnchantment(Enchantments.SHARPNESS, 5);
        blade.addEnchantment(Enchantments.FIRE_ASPECT, 2);

        return blade;
    }

    public ItemStack getBladeStack() {
        return bladeStack.copy();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("BladeStack")) {
            bladeStack = new ItemStack(compound.getCompoundTag("BladeStack"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (bladeStack != null) {
            compound.setTag("BladeStack", bladeStack.writeToNBT(new NBTTagCompound()));
        }
        return compound;
    }
}
