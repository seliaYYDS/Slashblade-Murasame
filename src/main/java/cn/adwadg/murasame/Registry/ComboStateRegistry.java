package cn.adwadg.murasame.Registry;

import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.Slasharts.SpatialSlash;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.ability.StunManager;
import mods.flammpfeil.slashblade.registry.combo.ComboState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ComboStateRegistry {
    public static final DeferredRegister<ComboState> COMBO_STATES =
            DeferredRegister.create(ComboState.REGISTRY_KEY, Murasame.MOD_ID);

    public static final RegistryObject<ComboState> SPATIAL_SLASH = COMBO_STATES.register("spatial_slash",
            ComboState.Builder.newInstance()
                    .startAndEnd(2200, 2300)  // 100帧的动画时间
                    .priority(60)              // 较高优先级
                    .speed(0.8F)               // 稍微减缓动画速度，增强时间减缓感
                    .next(entity -> SlashBlade.prefix("none"))  // 完成后回到空闲状态
                    .nextOfTimeout(entity -> SlashBlade.prefix("none"))

                    // 开始时减缓时间和固定生物
                    .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                            .put(0, SpatialSlash::doSpatialSlash) // 立即开始空间固定
                            .build())

                    // 玩家移动持续减缓
                    .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                            .put(5, (entityIn) -> entityIn.setDeltaMovement(entityIn.getDeltaMovement().scale(0.3)))
                            .put(10, (entityIn) -> entityIn.setDeltaMovement(entityIn.getDeltaMovement().scale(0.3)))
                            .put(15, (entityIn) -> entityIn.setDeltaMovement(entityIn.getDeltaMovement().scale(0.3)))
                            .put(20, (entityIn) -> entityIn.setDeltaMovement(entityIn.getDeltaMovement().scale(0.3)))
                            .build())

                    // 释放圆形斩击（第50帧）
                    .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                            .put(50, SpatialSlash::executeCircularSlash) // 释放范围斩击
                            .build())

                    // 结束时释放所有固定并恢复移动
                    .addTickAction(ComboState.TimeLineTickAction.getBuilder()
                            .put(95, SpatialSlash::releaseSpatialHold) // 结束前释放
                            .build())

                    // 防止下落和添加命中效果
                    .addHitEffect((t, a) -> StunManager.setStun(t, 30)) // 中等眩晕效果
                    ::build);
}
