package com.smushytaco.hunger_remover.mixins;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(ApplyStatusEffectsConsumeEffect.class)
public abstract class MixinApplyEffectsConsumeEffect {
    @WrapOperation(method = "apply", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"))
    private boolean hookOnConsume(LivingEntity instance, MobEffectInstance effect, Operation<Boolean> original) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getReplaceAllHungerWithPoison() && !HungerRemover.INSTANCE.getConfig().getReplaceHungerFromFoodWithPoison() || effect.getEffect() != MobEffects.HUNGER) return original.call(instance, effect);
        ((StatusEffectInstanceAccessor) effect).setEffect(MobEffects.POISON);
        return original.call(instance, effect);
    }
}