package cn.adwadg.murasame.Registry;

import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.Specialeffects.BladeSoulEffect;
import com.mojang.datafixers.kinds.IdF;
import mods.flammpfeil.slashblade.registry.specialeffects.SpecialEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SERegistry {
    public static final DeferredRegister<SpecialEffect> REGISTRY_KEY2;
    public static final RegistryObject<SpecialEffect> BLADE_SOUL;
    static {
        REGISTRY_KEY2 = DeferredRegister.create(SpecialEffect.REGISTRY_KEY, Murasame.MOD_ID);
        BLADE_SOUL = REGISTRY_KEY2.register("blade_soul", BladeSoulEffect::new);
    }
    public static void register(IEventBus bus){
        REGISTRY_KEY2.register(bus);
    }
}
