package cn.adwadg.murasame.Registry;

import cn.adwadg.murasame.Entities.EntityMurasameSoul;
import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.TileEntity.BlockEntity.EmbeddedBladeStoneEntity;
import cn.adwadg.murasame.client.renderer.EmbeddedBladeStoneRenderer;
import cn.adwadg.murasame.client.renderer.MurasameSoulRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Murasame.MOD_ID);

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Murasame.MOD_ID);

    public static final RegistryObject<BlockEntityType<EmbeddedBladeStoneEntity>> EMBEDDED_STONE_ENTITY = BLOCK_ENTITIES.register("embedded_stone",
            () -> BlockEntityType.Builder.of(EmbeddedBladeStoneEntity::new, ModBlocks.EMBEDDED_STONE.get()).build(null));

    public static final RegistryObject<EntityType<EntityMurasameSoul>> MURASAME_SOUL =
            ENTITIES.register("murasame_soul", () ->
                    EntityType.Builder.of(EntityMurasameSoul::new, MobCategory.MISC)
                            .sized(0.5f, 1.8f) // 更合适的碰撞箱
                            .clientTrackingRange(16)
                            .updateInterval(2)
                            .noSummon()
                            .fireImmune()
                            .build("murasame_soul"));
    // 在主类初始化时调用
    public static void registerAttributes(IEventBus eventBus) {
        eventBus.addListener(EventPriority.NORMAL, false, EntityAttributeCreationEvent.class,
                event -> event.put(MURASAME_SOUL.get(), EntityMurasameSoul.createAttributes().build()));
    }

}
