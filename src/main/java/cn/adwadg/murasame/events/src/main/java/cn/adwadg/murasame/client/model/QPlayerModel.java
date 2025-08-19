package cn.adwadg.murasame.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class QPlayerModel<T extends LivingEntity> extends HumanoidModel<T> {
    // 缩放比例
    private static final float HEAD_SCALE = 1.5f;
    private static final float BODY_SCALE = 0.7f;
    private static final float LIMB_SCALE = 0.55f;

    // 位置偏移
    private static final float HEAD_Y_OFFSET = 0f;
    private static final float ARM_X_OFFSET = 3.2f;
    private static final float ARM_Y_OFFSET = 1.8f;
    private static final float LEG_Y_OFFSET = 8f;

    public QPlayerModel(ModelPart root) {
        super(root);
        // 初始化时应用缩放
        applyScaling(root);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        // 重新应用位置偏移，防止被默认动画覆盖
        this.head.y = HEAD_Y_OFFSET;
        this.rightArm.x = -ARM_X_OFFSET;
        this.rightArm.y = ARM_Y_OFFSET;
        this.leftArm.x = ARM_X_OFFSET;
        this.leftArm.y = ARM_Y_OFFSET;
        this.rightLeg.y = LEG_Y_OFFSET;
        this.leftLeg.y = LEG_Y_OFFSET;
    }

    private static void applyScaling(ModelPart root) {
        root.getChild("head").xScale = HEAD_SCALE;
        root.getChild("head").yScale = HEAD_SCALE;
        root.getChild("head").zScale = HEAD_SCALE;

        root.getChild("body").xScale = BODY_SCALE;
        root.getChild("body").yScale = BODY_SCALE;
        root.getChild("body").zScale = BODY_SCALE;

        root.getChild("right_arm").xScale = LIMB_SCALE;
        root.getChild("right_arm").yScale = LIMB_SCALE;
        root.getChild("right_arm").zScale = LIMB_SCALE;

        root.getChild("left_arm").xScale = LIMB_SCALE;
        root.getChild("left_arm").yScale = LIMB_SCALE;
        root.getChild("left_arm").zScale = LIMB_SCALE;

        root.getChild("right_leg").xScale = LIMB_SCALE;
        root.getChild("right_leg").yScale = LIMB_SCALE;
        root.getChild("right_leg").zScale = LIMB_SCALE;

        root.getChild("left_leg").xScale = LIMB_SCALE;
        root.getChild("left_leg").yScale = LIMB_SCALE;
        root.getChild("left_leg").zScale = LIMB_SCALE;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition parts = mesh.getRoot();

        // 头部（使用原始尺寸，缩放由 applyScaling 处理）
        parts.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8),
                PartPose.offset(0, HEAD_Y_OFFSET, 0));

        // 帽子（跟随头部）
        parts.addOrReplaceChild("hat",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, new CubeDeformation(0.5F)),
                PartPose.offset(0, HEAD_Y_OFFSET, 0));

        // 身体
        parts.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(16, 16)
                        .addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4),
                PartPose.ZERO);

        // 右臂
        parts.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4),
                PartPose.offset(-ARM_X_OFFSET, ARM_Y_OFFSET, 0));

        // 左臂
        parts.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .mirror()
                        .addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4),
                PartPose.offset(ARM_X_OFFSET, ARM_Y_OFFSET, 0));

        // 右腿
        parts.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4),
                PartPose.offset(-1.9F, LEG_Y_OFFSET, 0));

        // 左腿
        parts.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .mirror()
                        .addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4),
                PartPose.offset(1.9F, LEG_Y_OFFSET, 0));

        return LayerDefinition.create(mesh, 64, 64);
    }
}
