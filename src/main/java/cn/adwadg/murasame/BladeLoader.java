package cn.adwadg.murasame;

import cn.adwadg.murasame.blades.Murasamemaru;
import cn.adwadg.murasame.blades.Murasamemaru_awakened;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class BladeLoader {
    public BladeLoader(FMLPreInitializationEvent event) {
        SlashBlade.InitEventBus.register(new Murasamemaru());
        SlashBlade.InitEventBus.register(new Murasamemaru_awakened());
    }
}
