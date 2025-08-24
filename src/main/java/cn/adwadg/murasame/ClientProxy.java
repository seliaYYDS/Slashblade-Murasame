package cn.adwadg.murasame;

import cn.adwadg.murasame.client.renderer.TileEntityEmbeddedBladeStoneRenderer;
import cn.adwadg.murasame.registry.ModBlocks;
import cn.adwadg.murasame.tileentity.TileEntityEmbeddedBladeStone;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        // 注册TileEntity渲染器
        ClientRegistry.bindTileEntitySpecialRenderer(
                TileEntityEmbeddedBladeStone.class,
                new TileEntityEmbeddedBladeStoneRenderer()
        );

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        // 注册方块的物品模型
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(ModBlocks.EMBEDDED_STONE),
                0,
                new ModelResourceLocation("murasame:embedded_stone", "inventory")
        );
    }
}
