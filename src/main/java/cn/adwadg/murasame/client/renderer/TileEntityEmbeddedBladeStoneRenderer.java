package cn.adwadg.murasame.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import cn.adwadg.murasame.tileentity.TileEntityEmbeddedBladeStone;

public class TileEntityEmbeddedBladeStoneRenderer extends TileEntitySpecialRenderer<TileEntityEmbeddedBladeStone> {

    @Override
    public void render(TileEntityEmbeddedBladeStone te, double x, double y, double z,
                       float partialTicks, int destroyStage, float alpha) {

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 1.3, z + 0.5);

        // 调整剑的角度
        GlStateManager.rotate(180, 1, 0, 0);
        GlStateManager.rotate(-45, 0, 0, 1);

        // 调整大小
        GlStateManager.scale(1.2f, 1.2f, 1.2f);

        // 禁用光照计算，强制使用最大亮度
        GlStateManager.disableLighting();

        // 设置颜色为白色（最大亮度）
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        // 渲染铁剑
        Minecraft.getMinecraft().getRenderItem().renderItem(
                new ItemStack(Items.IRON_SWORD),
                ItemCameraTransforms.TransformType.FIXED
        );

        // 重新启用光照（如果需要）
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    @Override
    public boolean isGlobalRenderer(TileEntityEmbeddedBladeStone te) {
        return true;
    }
}
