package cn.adwadg.murasame.Slasharts;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import java.util.Random;

public class SpatialParticleEffects {

    private static final Random random = new Random();

    // 时间定格时的空间扭曲粒子效果
    public static void createTimeFreezeEffect(LivingEntity entity) {
        if (entity.level().isClientSide()) return;

        ServerLevel level = (ServerLevel) entity.level();
        Vec3 center = entity.position();
        double radius = 8.0;

        // 创建球形粒子场
        for (int i = 0; i < 120; i++) { // 增加粒子数量
            double theta = random.nextDouble() * Math.PI * 2;
            double phi = random.nextDouble() * Math.PI;
            double r = radius * random.nextDouble();

            double x = center.x + r * Math.sin(phi) * Math.cos(theta);
            double y = center.y + r * Math.sin(phi) * Math.sin(theta);
            double z = center.z + r * Math.cos(phi);

            // 使用末影粒子创造空间扭曲效果
            level.sendParticles(ParticleTypes.PORTAL,
                    x, y, z,
                    1, // 数量
                    0, 0, 0, // 偏移
                    0.05 // 速度
            );

            // 添加一些闪光粒子
            if (random.nextFloat() < 0.3f) {
                level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                        x, y, z,
                        1,
                        0.1, 0.1, 0.1,
                        0.02
                );
            }
        }

        // 创建地面环形效果
        createGroundRingEffect(level, center, radius);

        // 创建玩家周围的保护罩效果
        createPlayerAuraEffect(level, entity);
    }

    // 地面环形粒子效果
    private static void createGroundRingEffect(ServerLevel level, Vec3 center, double radius) {
        for (int i = 0; i < 36; i++) {
            double angle = i * 10 * Math.PI / 180;
            double x = center.x + radius * Math.cos(angle);
            double z = center.z + radius * Math.sin(angle);

            level.sendParticles(ParticleTypes.REVERSE_PORTAL,
                    x, center.y + 0.1, z,
                    2,
                    0.2, 0, 0.2,
                    0.03
            );
        }
    }

    // 玩家周围的光环效果
    private static void createPlayerAuraEffect(ServerLevel level, LivingEntity entity) {
        Vec3 pos = entity.position();

        for (int i = 0; i < 24; i++) {
            double angle = i * 15 * Math.PI / 180;
            double height = 0.5 + random.nextDouble() * 1.5;
            double offset = 0.8 + random.nextDouble() * 0.4;

            double x = pos.x + offset * Math.cos(angle);
            double z = pos.z + offset * Math.sin(angle);

            level.sendParticles(ParticleTypes.ENCHANT,
                    x, pos.y + height, z,
                    1,
                    0, 0, 0,
                    0.1
            );
        }
    }

    // 空间斩释放时的切割效果
    public static void createSpatialCutEffect(LivingEntity entity) {
        if (entity.level().isClientSide()) return;

        ServerLevel level = (ServerLevel) entity.level();
        Vec3 center = entity.position();

        // 创建16个方向的切割效果
        for (int i = 0; i < 16; i++) {
            float angle = i * 22.5f;
            double rad = Math.toRadians(angle);

            for (double distance = 1.0; distance <= 8.0; distance += 0.5) {
                double x = center.x + distance * Math.cos(rad);
                double z = center.z + distance * Math.sin(rad);

                // 主要切割粒子
                level.sendParticles(ParticleTypes.SWEEP_ATTACK,
                        x, center.y + 1.0, z,
                        1,
                        0.1, 0.2, 0.1,
                        0.05
                );

                // 附加效果粒子
                if (random.nextFloat() < 0.4f) {
                    level.sendParticles(ParticleTypes.CRIT,
                            x, center.y + 1.2, z,
                            1,
                            0.05, 0.1, 0.05,
                            0.03
                    );
                }
            }
        }
    }

    // 时间恢复时的消散效果
    public static void createTimeResumeEffect(LivingEntity entity) {
        if (entity.level().isClientSide()) return;

        ServerLevel level = (ServerLevel) entity.level();
        Vec3 center = entity.position();
        AABB area = new AABB(center.x - 8, center.y - 4, center.z - 8,
                center.x + 8, center.y + 4, center.z + 8);

        // 创建粒子消散效果
        for (int i = 0; i < 80; i++) {
            double x = center.x + (random.nextDouble() - 0.5) * 16;
            double y = center.y + (random.nextDouble() - 0.5) * 8;
            double z = center.z + (random.nextDouble() - 0.5) * 16;

            if (area.contains(x, y, z)) {
                level.sendParticles(ParticleTypes.CLOUD,
                        x, y, z,
                        1,
                        0.1, 0.1, 0.1,
                        0.08
                );
            }
        }

        // 创建收缩的光环效果
        createShrinkingAuraEffect(level, center);
    }

    // 收缩的光环效果
    private static void createShrinkingAuraEffect(ServerLevel level, Vec3 center) {
        for (double radius = 8.0; radius > 0; radius -= 0.5) {
            for (int i = 0; i < 12; i++) {
                double angle = i * 30 * Math.PI / 180;
                double x = center.x + radius * Math.cos(angle);
                double z = center.z + radius * Math.sin(angle);

                level.sendParticles(ParticleTypes.WITCH,
                        x, center.y + 1.0, z,
                        1,
                        0.05, 0.1, 0.05,
                        0.04
                );
            }
        }
    }
}
