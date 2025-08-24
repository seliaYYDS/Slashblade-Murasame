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
public class Murasamemaru_awakened {
    public static final String name = "flammpfeil.slashblade.named.murasamemaru_awakened";

    @SubscribeEvent
    public void InitKatana(InitEvent event){
        ItemStack customblade = new ItemStack(SlashBlade.bladeNamed,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);
        customblade.addEnchantment(Enchantments.SMITE,25);
        customblade.addEnchantment(Enchantments.SHARPNESS,10);
        customblade.addEnchantment(Enchantments.FIRE_ASPECT,3);
        customblade.addEnchantment(Enchantments.UNBREAKING, 10);
        ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
        ItemSlashBladeNamed.CurrentItemName.set(tag, name);
        ItemSlashBladeNamed.CustomMaxDamage.set(tag, 300);
        ItemSlashBlade.setBaseAttackModifier(tag, 35.0F);
        ItemSlashBlade.TextureName.set(tag, "named/murasame/murasamemaru_awakened");
        ItemSlashBlade.ModelName.set(tag, "named/murasame/murasamemaru_awakened");
        ItemSlashBlade.SpecialAttackType.set(tag, 2);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.SummonedSwordColor.set(tag, 65350);
        //SpecialEffects.addEffect(customblade, SELoader.Ciallo);

        SlashBlade.registerCustomItemStack(name, customblade);
        ItemSlashBladeNamed.NamedBlades.add(name);
    }
}
