package cn.adwadg.murasame;

import cn.adwadg.murasame.registry.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Murasame.MODID, name = Murasame.NAME, version = Murasame.VERSION,dependencies = "required-after:flammpfeil.slashblade@[mc1.12-r32,)")
public class Murasame
{
    public static final String MODID = "murasame";
    public static final String NAME = "Murasame";
    public static final String VERSION = "1.0";

    @SuppressWarnings("unused")
    public static final Logger LOGGER = LogManager.getLogger(MODID);



    @SidedProxy(clientSide = "cn.adwadg.murasame.ClientProxy", serverSide = "cn.adwadg.murasame.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs creativeTab;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        creativeTab = new CreativeTabs("murasame_tab") {
            @Override
            public ItemStack getTabIconItem() {
                return new ItemStack(ModBlocks.EMBEDDED_STONE);
            }
        };

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
