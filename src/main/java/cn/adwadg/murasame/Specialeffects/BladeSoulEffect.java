// BladeSoulEffect.java
package cn.adwadg.murasame.Specialeffects;

import cn.adwadg.murasame.Registry.SoundRegistry;
import mods.flammpfeil.slashblade.registry.specialeffects.SpecialEffect;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BladeSoulEffect extends SpecialEffect {
    // SE激活音效

    public BladeSoulEffect() {
        super(0); // 需求等级0
    }

    @SubscribeEvent
    public static void doSlash(){

    }
}
