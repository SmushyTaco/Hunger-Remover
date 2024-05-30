package com.smushytaco.hunger_remover.mixins;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(Entity.class)
public abstract class MixinEntity {
    @ModifyReturnValue(method = "isSprinting", at = @At("RETURN"))
    private boolean hookIsSprinting(boolean original) { return (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getCantSprint() || !(((Entity) (Object) this) instanceof PlayerEntity playerEntity) || playerEntity.isCreative() || playerEntity.isSpectator()) && original; }
}