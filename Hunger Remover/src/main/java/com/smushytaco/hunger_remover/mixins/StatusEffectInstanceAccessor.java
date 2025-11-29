package com.smushytaco.hunger_remover.mixins;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(MobEffectInstance.class)
public interface StatusEffectInstanceAccessor {
    @Accessor
    @Mutable
    void setEffect(Holder<MobEffect> type);
}