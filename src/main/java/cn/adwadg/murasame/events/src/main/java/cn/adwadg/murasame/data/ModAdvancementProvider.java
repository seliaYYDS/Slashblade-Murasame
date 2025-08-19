package cn.adwadg.murasame.data;

import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.advancement.ModAdvancements;
import cn.adwadg.murasame.Registry.ModItems;
import mods.flammpfeil.slashblade.init.SBItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.ForgeAdvancementProvider.AdvancementGenerator;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends ForgeAdvancementProvider {
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries,
                                  ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new MurasameAdvancementGenerator()));
    }

    private static class MurasameAdvancementGenerator implements AdvancementGenerator {
        private static final ResourceLocation BACKGROUND = new ResourceLocation("minecraft",
                "textures/gui/advancements/backgrounds/stone.png");

        @Override
        public void generate(HolderLookup.Provider provider, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
            Advancement.Builder.advancement()
                    .display(
                            SBItems.slashblade,
                            Component.translatable("advancements.murasame.get_murasame.title"),
                            Component.translatable("advancements.murasame.get_murasame.description"),
                            BACKGROUND,  // 设置背景图片
                            FrameType.CHALLENGE,
                            true,
                            true,
                            false
                    )
                    .addCriterion("obtained_from_stone", new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, ModAdvancements.MURASAME_ADVANCEMENT_ID, existingFileHelper);
        }
    }

}
