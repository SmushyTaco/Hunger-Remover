package com.smushytaco.hunger_remover.mixins;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(StatusEffectInstance.class)
public interface StatusEffectInstanceAccessor {
    @Accessor
    @Mutable
    void setType(RegistryEntry<StatusEffect> type);
}