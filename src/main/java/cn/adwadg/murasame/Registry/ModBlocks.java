package cn.adwadg.murasame.Registry;

import cn.adwadg.murasame.Blocks.EmbeddedBladeStoneBlock;
import cn.adwadg.murasame.Murasame;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Murasame.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Murasame.MOD_ID);

    public static final RegistryObject<Block> EMBEDDED_STONE = BLOCKS.register("embedded_stone",
            () -> new EmbeddedBladeStoneBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> EMPTY_STONE = BLOCKS.register("empty_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Item> EMBEDDED_STONE_ITEM = ITEMS.register("embedded_stone",
            () -> new BlockItem(EMBEDDED_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> EMPTY_STONE_ITEM = ITEMS.register("empty_stone",
            () -> new BlockItem(EMPTY_STONE.get(), new Item.Properties()));



}
