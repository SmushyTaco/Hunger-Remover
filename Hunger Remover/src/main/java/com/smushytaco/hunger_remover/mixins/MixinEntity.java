package com.smushytaco.hunger_remover.mixins;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(Entity.class)
public abstract class MixinEntity {
    @ModifyReturnValue(method = "isSprinting", at = @At("RETURN"))
    @SuppressWarnings("ConstantConditions")
    private boolean hookIsSprinting(boolean original) { return (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getCantSprint() || !(((Entity) (Object) this) instanceof Player playerEntity) || playerEntity.isCreative() || playerEntity.isSpectator()) && original; }
}