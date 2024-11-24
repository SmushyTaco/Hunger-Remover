package com.smushytaco.hunger_remover.mixins;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(StatusEffectInstance.class)
public abstract class MixinStatusEffectInstance {
    @Shadow
    @Final
    @Mutable
    private RegistryEntry<StatusEffect> type;
    @Inject(method = "<init>(Lnet/minecraft/registry/entry/RegistryEntry;IIZZZLnet/minecraft/entity/effect/StatusEffectInstance;)V", at = @At("TAIL"))
    private void hookInitializeOne(RegistryEntry<StatusEffect> effect, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon, StatusEffectInstance hiddenEffect, CallbackInfo ci) {
        if (!HungerRemover.INSTANCE.getConfig().getDisableMod() && HungerRemover.INSTANCE.getConfig().getReplaceAllHungerWithPoison() && type == StatusEffects.HUNGER) type = StatusEffects.POISON;
    }
    @Inject(method = "<init>(Lnet/minecraft/entity/effect/StatusEffectInstance;)V", at = @At("TAIL"))
    private void hookInitializeTwo(StatusEffectInstance instance, CallbackInfo ci) {
        if (!HungerRemover.INSTANCE.getConfig().getDisableMod() && HungerRemover.INSTANCE.getConfig().getReplaceAllHungerWithPoison() && type == StatusEffects.HUNGER) type = StatusEffects.POISON;
    }
}