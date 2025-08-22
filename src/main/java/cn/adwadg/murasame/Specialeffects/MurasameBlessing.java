package cn.adwadg.murasame.Specialeffects;

import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.Registry.SERegistry;
import mods.flammpfeil.slashblade.event.SlashBladeEvent;
import mods.flammpfeil.slashblade.registry.specialeffects.SpecialEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Murasame.MOD_ID)
public class MurasameBlessing extends SpecialEffect {
    private static int critCoolDown = 0;
    private static boolean isLowHP = false;
    private static float kbBoost = 0;
    public MurasameBlessing(){
        super(50,false,false);
    }
    @SubscribeEvent
    public static void onPlayerTick(SlashBladeEvent.UpdateEvent event){

        if(!(event.getEntity() instanceof Player player) || !event.isSelected()){
            return;
        }
        if(critCoolDown!=0){
            critCoolDown-=1;
        }
        isLowHP= player.getHealth() <= player.getMaxHealth() * 0.4;
        kbBoost= 2f - player.getHealth()/player.getMaxHealth() * 2f;
        int level = player.experienceLevel;
        if(SpecialEffect.isEffective(SERegistry.MURASAME_BLESSING.get(),level)){
            if(!player.hasEffect(MobEffects.DAMAGE_RESISTANCE)){
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,40,2,false,false));
            }
            if(!player.hasEffect(MobEffects.REGENERATION)){
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,40,1,false,false));
            }
            if(!player.hasEffect(MobEffects.DAMAGE_BOOST)){
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,40,1,false,false));
            }
        }
    }
    @SubscribeEvent
    public static void onSlashHit(SlashBladeEvent.HitEvent event){
        LivingEntity target = event.getTarget();
        if(!(event.getUser() instanceof Player player)){
            return;
        }
        int level = player.experienceLevel;
        if(SpecialEffect.isEffective(SERegistry.MURASAME_BLESSING.get(),level) && isLowHP){
            applyKnockback(event.getUser(),target,kbBoost);
        }
    }

    @SubscribeEvent
    public static void onSlash(SlashBladeEvent.DoSlashEvent event){
        Player player = (Player)event.getUser();
        int level = player.experienceLevel;
        if(SpecialEffect.isEffective(SERegistry.MURASAME_BLESSING.get(),level)){
            if(critCoolDown==0 && isLowHP){
                event.getSlashBladeState().setColorCode(8388736);
                event.setCritical(true);
                event.setDamage(event.getDamage() + event.getDamage()*0.5f);
                critCoolDown=200;
            }else {
                event.getSlashBladeState().setColorCode(65350);
            }
        }
    }

    public static void applyKnockback(LivingEntity attacker, LivingEntity target, float strength) {
        Vec3 direction = new Vec3(
                target.getX() - attacker.getX(),
                0,
                target.getZ() - attacker.getZ()
        );

        if (direction.length() > 0) {
            direction = direction.normalize();
        } else {
            direction = new Vec3(
                    (Math.random() - 0.5) * 2,
                    0,
                    (Math.random() - 0.5) * 2
            ).normalize();
        }
        target.setDeltaMovement(
                target.getDeltaMovement().x + direction.x * strength,
                target.getDeltaMovement().y + 0.3,
                target.getDeltaMovement().z + direction.z * strength
        );

        target.hasImpulse = true;
    }
}
