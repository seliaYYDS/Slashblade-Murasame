package cn.adwadg.murasame.registry;

import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldGenRegistry {

    public static void registerWorldGenerators() {
        // 注册世界生成器
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 5);

        // 注册结构（如果需要使用StructureComponent）
        // MapGenStructureIO.registerStructure(ShrineStructure.Start.class, "murasame:shrine");
    }
}
