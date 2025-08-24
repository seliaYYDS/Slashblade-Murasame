package cn.adwadg.murasame.registry;

import cn.adwadg.murasame.Murasame;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Murasame.MODID)
public class SoundRegistry {
    private static final List<SoundEvent> SOUND_LIST = new ArrayList<>();

    // 定义所有音效
    public static SoundEvent MURASAME_YELL = registerSound("murasame_yell");
    public static SoundEvent MURASAME_TOUCH = registerSound("murasame_touch");
    public static SoundEvent MURASAME_NO = registerSound("murasame_no");
    public static SoundEvent MURASAME_NIGHT = registerSound("murasame_night");
    public static SoundEvent MURASAME_MORNING2 = registerSound("murasame_morning2");
    public static SoundEvent MURASAME_MORNING = registerSound("murasame_morning");
    public static SoundEvent MURASAME_LOWHP = registerSound("murasame_lowhp");
    public static SoundEvent MURASAME_GREETING = registerSound("murasame_greeting");
    public static SoundEvent MURASAME_FRIGHTENED = registerSound("murasame_frightened");
    public static SoundEvent MURASAME_DROP = registerSound("murasame_drop");
    public static SoundEvent MURASAME_COMFORT = registerSound("murasame_comfort");
    public static SoundEvent MURASAME_AFTER_BOSS = registerSound("murasame_after_boss");
    public static SoundEvent MURASAME_AFK = registerSound("murasame_afk");
    public static SoundEvent MURASAME_AFK2 = registerSound("murasame_afk2");
    public static SoundEvent MURASAME_SWITCH = registerSound("murasame_switch");
    public static SoundEvent MURASAME_SWITCH2 = registerSound("murasame_switch2");
    public static SoundEvent MURASAME_SWITCH3 = registerSound("murasame_switch3");
    public static SoundEvent MURASAME_SWITCH4 = registerSound("murasame_switch4");
    public static SoundEvent MURASAME_SWITCH5 = registerSound("murasame_switch5");
    public static SoundEvent MURASAME_SWITCH6 = registerSound("murasame_switch6");
    public static SoundEvent MURASAME_SWITCH7 = registerSound("murasame_switch7");
    public static SoundEvent MURASAME_SWITCH8 = registerSound("murasame_switch8");
    public static SoundEvent MURASAME_SWITCH9 = registerSound("murasame_switch9");

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        for (SoundEvent sound : SOUND_LIST) {
            event.getRegistry().register(sound);
        }
    }

    private static SoundEvent registerSound(String name) {
        SoundEvent event = new SoundEvent(new ResourceLocation(Murasame.MODID, name))
                .setRegistryName(Murasame.MODID, name);
        SOUND_LIST.add(event);
        return event;
    }
}
