package com.smushytaco.hunger_remover.mixins;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(ApplyEffectsConsumeEffect.class)
public abstract class MixinApplyEffectsConsumeEffect {
    @WrapOperation(method = "onConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z"))
    private boolean hookOnConsume(LivingEntity instance, StatusEffectInstance effect, Operation<Boolean> original) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getReplaceAllHungerWithPoison() && !HungerRemover.INSTANCE.getConfig().getReplaceHungerFromFoodWithPoison() || effect.getEffectType() != StatusEffects.HUNGER) return original.call(instance, effect);
        ((StatusEffectInstanceAccessor) effect).setType(StatusEffects.POISON);
        return original.call(instance, effect);
    }
}