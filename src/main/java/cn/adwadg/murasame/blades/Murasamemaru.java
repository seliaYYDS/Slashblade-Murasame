package cn.adwadg.murasame.blades;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.event.LoadEvent.InitEvent;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("DataFlowIssue")
public class Murasamemaru {
    public static final String name = "flammpfeil.slashblade.named.murasamemaru";

    @SubscribeEvent
    public void InitKatana(InitEvent event){
        ItemStack customblade = new ItemStack(SlashBlade.bladeNamed,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);
        customblade.addEnchantment(Enchantments.SHARPNESS, 5);
        customblade.addEnchantment(Enchantments.FIRE_ASPECT, 2);
        ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
        ItemSlashBladeNamed.CurrentItemName.set(tag, name);
        ItemSlashBladeNamed.CustomMaxDamage.set(tag, 150);
        ItemSlashBlade.setBaseAttackModifier(tag, 18.0F);
        ItemSlashBlade.TextureName.set(tag, "named/murasame/murasamemaru");
        ItemSlashBlade.ModelName.set(tag, "named/murasame/murasamemaru");
        ItemSlashBlade.SpecialAttackType.set(tag, 2);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.SummonedSwordColor.set(tag, 65350);

        SlashBlade.registerCustomItemStack(name, customblade);
        ItemSlashBladeNamed.NamedBlades.add(name);
    }
}
