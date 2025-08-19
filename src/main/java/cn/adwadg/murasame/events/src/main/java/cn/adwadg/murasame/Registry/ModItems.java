package cn.adwadg.murasame.Registry;

import cn.adwadg.murasame.Murasame;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Murasame.MOD_ID);

}
