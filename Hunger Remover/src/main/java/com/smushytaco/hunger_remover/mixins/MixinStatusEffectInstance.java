package com.smushytaco.hunger_remover.mixins;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(MobEffectInstance.class)
public abstract class MixinStatusEffectInstance {
    @Shadow
    @Final
    @Mutable
    private Holder<MobEffect> effect;
    @Inject(method = "<init>(Lnet/minecraft/core/Holder;IIZZZLnet/minecraft/world/effect/MobEffectInstance;)V", at = @At("TAIL"))
    private void hookInitializeOne(Holder<MobEffect> effect, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon, MobEffectInstance hiddenEffect, CallbackInfo ci) {
        if (!HungerRemover.INSTANCE.getConfig().getDisableMod() && HungerRemover.INSTANCE.getConfig().getReplaceAllHungerWithPoison() && effect == MobEffects.HUNGER) this.effect = MobEffects.POISON;
    }
    @Inject(method = "<init>(Lnet/minecraft/world/effect/MobEffectInstance;)V", at = @At("TAIL"))
    private void hookInitializeTwo(MobEffectInstance instance, CallbackInfo ci) {
        if (!HungerRemover.INSTANCE.getConfig().getDisableMod() && HungerRemover.INSTANCE.getConfig().getReplaceAllHungerWithPoison() && effect == MobEffects.HUNGER) this.effect = MobEffects.POISON;
    }
}