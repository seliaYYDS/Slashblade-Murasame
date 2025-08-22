package cn.adwadg.murasame.events;

import cn.adwadg.murasame.Registry.KeyBindings;
import cn.adwadg.murasame.Murasame;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// 添加正确的注解来注册事件处理器
@Mod.EventBusSubscriber(modid = Murasame.MOD_ID, value = Dist.CLIENT)
public class KeyInputHandler {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        if (KeyBindings.TOGGLE_KEY.consumeClick()) {
            KeyBindings.isToggled = !KeyBindings.isToggled;
        }
    }
}
