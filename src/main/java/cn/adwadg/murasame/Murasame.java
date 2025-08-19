package cn.adwadg.murasame;


import cn.adwadg.murasame.Registry.*;
import cn.adwadg.murasame.client.model.ModModelProvider;
import cn.adwadg.murasame.client.renderer.EmbeddedBladeStoneRenderer;
import cn.adwadg.murasame.client.renderer.MurasameSoulRenderer;
import cn.adwadg.murasame.data.ModAdvancementProvider;
import cn.adwadg.murasame.data.ModWorldGenProvider;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(Murasame.MOD_ID)
public class Murasame {
    public static final String MOD_ID = "murasame";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Murasame() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();




        MinecraftForge.EVENT_BUS.register(this);
        SoundRegistry.register(modEventBus);
        //SERegistry.register(modEventBus);

        ModBlocks.BLOCKS.register(modEventBus);  // 先注册方块
        ModEntities.ENTITIES.register(modEventBus);
        ModBlocks.ITEMS.register(modEventBus);   // 然后注册方块物品
        ModEntities.BLOCK_ENTITIES.register(modEventBus); // 接着注册方块实体
        ModItems.ITEMS.register(modEventBus);    // 最后注册其他物品


        ModEntities.registerAttributes(FMLJavaModLoadingContext.get().getModEventBus());

        modEventBus.addListener(ModModelProvider::registerLayers);


        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::onGatherData);
        modEventBus.addListener(this::onCommonSetup);




    }

    /*private void registerAfterModsLoaded(FMLCommonSetupEvent event) {
        // 确保所有mod已加载
        SERegistry.register(FMLJavaModLoadingContext.get().getModEventBus());
    }*/

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(SoundRegistry::logRegistration);
    }

    private void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeServer()) {
            // 注册成就数据生成器
            generator.addProvider(event.includeServer(),
                    new ModAdvancementProvider(output, registries, existingFileHelper));
            generator.addProvider(event.includeServer(),
                    new ModWorldGenProvider(output, registries, existingFileHelper));
        }
    }




    private void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("Client setup started");

        if (!ModEntities.MURASAME_SOUL.isPresent()) {
            LOGGER.error("Murasame Soul entity not registered!");
            return;
        }

        event.enqueueWork(() -> {
            try {
                EntityRenderers.register(ModEntities.MURASAME_SOUL.get(), MurasameSoulRenderer::new);
                Murasame.LOGGER.info("Murasame Soul renderer registered successfully");
            } catch (Exception e) {
                Murasame.LOGGER.error("Failed to register Murasame Soul renderer", e);
            }
            LOGGER.info("Registering block entity renderer");
            BlockEntityRenderers.register(ModEntities.EMBEDDED_STONE_ENTITY.get(),
                    EmbeddedBladeStoneRenderer::new);
        });
    }

    public static void playSound(Player player, SoundEvent sound) {
        if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.serverLevel().playSound(
                    null,
                    player.getX(), player.getY(), player.getZ(),
                    sound,
                    player.getSoundSource(),
                    1.0F, 1.0F
            );
        }
    }



}
