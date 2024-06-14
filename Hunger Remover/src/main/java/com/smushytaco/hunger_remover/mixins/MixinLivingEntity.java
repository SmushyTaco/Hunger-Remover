package com.smushytaco.hunger_remover.mixins;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @ModifyVariable(method = "setSprinting", at = @At("HEAD"), argsOnly = true)
    @SuppressWarnings("ConstantConditions")
    private boolean hookSetSprinting(boolean sprinting) { return (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getCantSprint() || !(((LivingEntity) (Object) this) instanceof PlayerEntity playerEntity) || playerEntity.isCreative() || playerEntity.isSpectator()) && sprinting; }
}