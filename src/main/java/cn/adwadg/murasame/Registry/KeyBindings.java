package cn.adwadg.murasame.Registry;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBindings {
    public static final KeyMapping TOGGLE_KEY = new KeyMapping(
            "key.murasame.toggle_soul",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_R,
            "category.murasame"
    );

    public static boolean isToggled = false;
}
