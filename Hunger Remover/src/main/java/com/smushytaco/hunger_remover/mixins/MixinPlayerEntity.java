package com.smushytaco.hunger_remover.mixins;
import com.smushytaco.hunger_remover.HungerRemover;
import com.smushytaco.hunger_remover.NoHungerManager;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity {
    @Shadow
    protected HungerManager hungerManager;
    @Inject(method = "<init>", at = @At("RETURN"))
    private void hookInit(CallbackInfo info) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod()) return;
        hungerManager = new NoHungerManager((PlayerEntity) (Object) this);
    }
}