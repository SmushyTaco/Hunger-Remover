package com.smushytaco.hunger_remover.mixins;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @ModifyVariable(method = "setSprinting", at = @At("HEAD"), argsOnly = true)
    @SuppressWarnings("ConstantConditions")
    private boolean hookSetSprinting(boolean sprinting) { return (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getCantSprint() || !(((LivingEntity) (Object) this) instanceof Player playerEntity) || playerEntity.isCreative() || playerEntity.isSpectator()) && sprinting; }
}