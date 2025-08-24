package cn.adwadg.murasame.registry;

import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.blocks.EmbeddedBladeStoneBlock;
import cn.adwadg.murasame.items.ItemEmbeddedBladeStone;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Murasame.MODID)
public class ModBlocks {

    public static final EmbeddedBladeStoneBlock EMBEDDED_STONE = new EmbeddedBladeStoneBlock();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        EMBEDDED_STONE.setRegistryName("embedded_stone");
        EMBEDDED_STONE.setUnlocalizedName("embedded_stone");
        registry.register(EMBEDDED_STONE);

    }


    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        // 使用自定义的 ItemBlock
        registry.register(new ItemEmbeddedBladeStone(EMBEDDED_STONE).setRegistryName(EMBEDDED_STONE.getRegistryName()));
    }
}
