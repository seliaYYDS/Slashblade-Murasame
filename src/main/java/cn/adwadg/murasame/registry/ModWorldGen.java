package cn.adwadg.murasame.registry;

import cn.adwadg.murasame.Murasame;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ModWorldGen implements IWorldGenerator {

    public static final ResourceLocation SHRINE_STRUCTURE = new ResourceLocation(Murasame.MODID, "shrine");
    private static final int SHRINE_CHANCE = 1500; // 生成几率，数值越大几率越小
    private static final int MIN_DISTANCE_CHUNKS = 24; // 最小生成间距（区块数）

    // 存储已生成神社的区块位置，用于间距控制
    private static final Set<ChunkPos> generatedShrines = new HashSet<>();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == 0) { // 只在主世界生成
            generateShrine(world, random, chunkX, chunkZ);
        }
    }

    private void generateShrine(World world, Random random, int chunkX, int chunkZ) {
        // 控制生成密度
        if (random.nextInt(SHRINE_CHANCE) != 0) {
            return;
        }

        ChunkPos currentChunk = new ChunkPos(chunkX, chunkZ);

        // 检查最小生成间距
        if (!isFarEnoughFromOtherShrines(currentChunk)) {
            return;
        }

        int x = chunkX * 16 + random.nextInt(16);
        int z = chunkZ * 16 + random.nextInt(16);
        int y = world.getHeight(x, z);
        BlockPos pos = new BlockPos(x, y, z);

        // 获取生物群系
        Biome biome = world.getBiome(pos);

        // 检查是否为海洋群系
        if (isOceanBiome(biome)) {
            return; // 如果是海洋群系，不生成
        }

        // 直接生成结构，不再检查地形平坦度
        generateStructure(world, pos, SHRINE_STRUCTURE);

        // 记录已生成的区块位置
        generatedShrines.add(currentChunk);
    }

    // 检查是否与其他神社保持足够距离
    private boolean isFarEnoughFromOtherShrines(ChunkPos currentChunk) {
        for (ChunkPos existingChunk : generatedShrines) {
            int distanceX = Math.abs(currentChunk.x - existingChunk.x);
            int distanceZ = Math.abs(currentChunk.z - existingChunk.z);

            // 计算区块距离
            int chunkDistance = Math.max(distanceX, distanceZ);

            if (chunkDistance < MIN_DISTANCE_CHUNKS) {
                return false; // 距离太近，不生成
            }
        }
        return true; // 距离足够，可以生成
    }

    // 检测是否为海洋群系
    private boolean isOceanBiome(Biome biome) {
        String biomeName = biome.getBiomeName().toLowerCase();
        return biomeName.contains("ocean") || biomeName.contains("deep_ocean");
    }

    private void generateStructure(World world, BlockPos pos, ResourceLocation structure) {
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        Template template = templateManager.getTemplate(world.getMinecraftServer(), structure);

        if (template != null) {
            // 调整位置确保结构正确放置
            BlockPos adjustedPos = pos.add(-template.getSize().getX() / 2, 0, -template.getSize().getZ() / 2);

            // 添加处理过程（可选）
            template.addBlocksToWorld(world, adjustedPos, new ShrinePlacementSettings(), 2);
        }
    }

    // 自定义放置设置
    private static class ShrinePlacementSettings extends net.minecraft.world.gen.structure.template.PlacementSettings {
        public ShrinePlacementSettings() {
            this.setIgnoreEntities(false);
            this.setReplacedBlock(null);
            this.setChunk(null);
            this.setIgnoreStructureBlock(false);
        }
    }
}
