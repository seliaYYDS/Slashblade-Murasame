package cn.adwadg.murasame.client.renderer;

import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.TileEntity.BlockEntity.EmbeddedBladeStoneEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class EmbeddedBladeStoneRenderer implements BlockEntityRenderer<EmbeddedBladeStoneEntity> {

    public EmbeddedBladeStoneRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(EmbeddedBladeStoneEntity entity, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (entity == null) {
            return;
        }
        Level world = entity.getLevel();
        BlockPos pos = entity.getBlockPos();

        int blockLight = world.getBrightness(LightLayer.BLOCK, pos.above());
        int skyLight = world.getBrightness(LightLayer.SKY, pos.above());
        int packedLight = skyLight << 20 | blockLight << 4;

        poseStack.pushPose();
        try {

            // 使用铁剑作为默认渲染
            ItemStack ironSword = new ItemStack(Items.IRON_SWORD);

            // 调整位置 - 剑插入石头中
            poseStack.translate(0.5, 1.3, 0.5); // 降低Y值使剑看起来插入石头

            // 设置旋转 - 剑直立插入
            poseStack.mulPose(Axis.ZP.rotationDegrees(135)); // 稍微旋转一定角度

            // 调整大小
            poseStack.scale(1f, 1.2f, 1f);

            // 获取物品渲染器
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

            // 渲染铁剑
            itemRenderer.renderStatic(
                    ironSword,
                    ItemDisplayContext.FIXED,
                    packedLight, // 使用手动计算的光照值
                    combinedOverlay,
                    poseStack,
                    buffer,
                    world,
                    0
            );

        } catch (Exception e) {
            Murasame.LOGGER.error("Error rendering iron sword in stone", e);
        } finally {
            poseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(EmbeddedBladeStoneEntity entity) {
        return true;
    }
}
