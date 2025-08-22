// SoundRegistry.java
package cn.adwadg.murasame.Registry;

import cn.adwadg.murasame.Murasame;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Collectors;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Murasame.MOD_ID);



    // 定义所有音效
    public static final RegistryObject<SoundEvent> MURASAME_YELL = register("murasame_yell");
    public static final RegistryObject<SoundEvent> MURASAME_TOUCH = register("murasame_touch");
    public static final RegistryObject<SoundEvent> MURASAME_NO = register("murasame_no");
    public static final RegistryObject<SoundEvent> MURASAME_NIGHT = register("murasame_night");
    public static final RegistryObject<SoundEvent> MURASAME_MORNING2 = register("murasame_morning2");
    public static final RegistryObject<SoundEvent> MURASAME_MORNING = register("murasame_morning");
    public static final RegistryObject<SoundEvent> MURASAME_LOWHP = register("murasame_lowhp");
    public static final RegistryObject<SoundEvent> MURASAME_GREETING = register("murasame_greeting");
    public static final RegistryObject<SoundEvent> MURASAME_FRIGHTENED = register("murasame_frightened");
    public static final RegistryObject<SoundEvent> MURASAME_DROP = register("murasame_drop");
    public static final RegistryObject<SoundEvent> MURASAME_COMFORT = register("murasame_comfort");
    public static final RegistryObject<SoundEvent> MURASAME_AFTER_BOSS = register("murasame_after_boss");
    public static final RegistryObject<SoundEvent> MURASAME_AFK = register("murasame_afk");
    public static final RegistryObject<SoundEvent> MURASAME_AFK2 = register("murasame_afk2");
    public static final RegistryObject<SoundEvent> MURASAME_SWITCH = register("murasame_switch");
    public static final RegistryObject<SoundEvent> MURASAME_SWITCH2 = register("murasame_switch2");
    public static final RegistryObject<SoundEvent> MURASAME_SWITCH3 = register("murasame_switch3");
    public static final RegistryObject<SoundEvent> MURASAME_SWITCH4 = register("murasame_switch4");
    public static final RegistryObject<SoundEvent> MURASAME_SWITCH5 = register("murasame_switch5");
    public static final RegistryObject<SoundEvent> MURASAME_SWITCH6 = register("murasame_switch6");
    public static final RegistryObject<SoundEvent> MURASAME_SWITCH7 = register("murasame_switch7");
    public static final RegistryObject<SoundEvent> MURASAME_SWITCH8 = register("murasame_switch8");
    public static final RegistryObject<SoundEvent> MURASAME_SWITCH9 = register("murasame_switch9");

    private static RegistryObject<SoundEvent> register(String name) {
        // 使用官方推荐的createVariableRangeEvent方法
        return SOUNDS.register(name, () ->
                SoundEvent.createVariableRangeEvent(new ResourceLocation(Murasame.MOD_ID, name)));
    }
    // 调试方法
    public static void logRegistration() {
        Murasame.LOGGER.info("已注册音效: {}",
                String.join(", ",
                        SOUNDS.getEntries().stream()
                                .map(ro -> ro.getId().toString())
                                .toArray(String[]::new)));
    }

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
