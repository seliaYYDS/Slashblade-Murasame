package cn.adwadg.murasame.client.renderer;

import cn.adwadg.murasame.Entities.EntityMurasameSoul;
import cn.adwadg.murasame.client.model.ModModelLayers;
import cn.adwadg.murasame.client.model.QPlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MurasameSoulRenderer extends MobRenderer<EntityMurasameSoul, QPlayerModel<EntityMurasameSoul>> {
    public MurasameSoulRenderer(EntityRendererProvider.Context context) {
        super(context, new QPlayerModel<>(context.bakeLayer(ModModelLayers.Q_PLAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMurasameSoul entity) {
        return new ResourceLocation("murasame:textures/entity/murasame_soul/default.png");
    }
}
