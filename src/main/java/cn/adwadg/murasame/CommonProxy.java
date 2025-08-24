package cn.adwadg.murasame;

import cn.adwadg.murasame.client.event.PlayerSoulHandler;
import cn.adwadg.murasame.client.event.SleepEventHandler;
import cn.adwadg.murasame.client.event.SoundEventHandler;
import cn.adwadg.murasame.registry.WorldGenRegistry;
import cn.adwadg.murasame.tileentity.TileEntityEmbeddedBladeStone;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        // 先注册TileEntity
        GameRegistry.registerTileEntity(
                TileEntityEmbeddedBladeStone.class,
                Murasame.MODID + ":embedded_stone"
        );

        WorldGenRegistry.registerWorldGenerators();

        // 注册事件处理器
        MinecraftForge.EVENT_BUS.register(new PlayerSoulHandler());
        MinecraftForge.EVENT_BUS.register(new SoundEventHandler());
        MinecraftForge.EVENT_BUS.register(new SleepEventHandler());

        // 初始化其他内容
        new BladeLoader(event);
    }

    public void init(FMLInitializationEvent event) {
        // 注册结构生成器
    }

    public void postInit(FMLPostInitializationEvent event) {}
}
