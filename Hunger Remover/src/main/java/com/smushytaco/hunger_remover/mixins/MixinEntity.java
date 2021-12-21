package com.smushytaco.hunger_remover.mixins;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(Entity.class)
@SuppressWarnings("ConstantConditions")
public abstract class MixinEntity {
    @Inject(method = "isSprinting", at = @At("HEAD"), cancellable = true)
    private void hookIsSprinting(CallbackInfoReturnable<Boolean> cir) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod()|| !HungerRemover.INSTANCE.getConfig().getCantSprint()) return;
        Entity entity = (Entity) (Object) this;
        if (!(entity instanceof PlayerEntity playerEntity) || playerEntity.isCreative() || playerEntity.isSpectator()) return;
        cir.setReturnValue(false);
    }
}