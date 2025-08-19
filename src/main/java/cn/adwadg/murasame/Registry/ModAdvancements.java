package cn.adwadg.murasame.advancement;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ModAdvancements {
    public static final ResourceLocation MURASAME_ADVANCEMENT_ID =
            new ResourceLocation("murasame", "get_murasame");

    public static void grantFromStone(ServerPlayer player) {
        Advancement advancement = player.server.getAdvancements()
                .getAdvancement(MURASAME_ADVANCEMENT_ID);

        if (advancement != null) {
            // 直接授予成就，不检查物品
            AdvancementProgress progress = player.getAdvancements()
                    .getOrStartProgress(advancement);

            if (!progress.isDone()) {
                for (String criterion : progress.getRemainingCriteria()) {
                    player.getAdvancements().award(advancement, criterion);
                }
            }
        }
    }
}
