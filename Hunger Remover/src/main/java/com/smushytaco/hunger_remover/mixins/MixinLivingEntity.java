package com.smushytaco.hunger_remover.mixins;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @ModifyVariable(method = "setSprinting", at = @At("HEAD"))
    private boolean hookSetSprinting(boolean sprinting) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod()||
                !HungerRemover.INSTANCE.getConfig().getCantSprint()) return sprinting;
        try {
            PlayerEntity playerEntity = (PlayerEntity) (Object) this;
            if (playerEntity.isCreative() || playerEntity.isSpectator()) return sprinting;
            return false;
        } catch (Exception ignored) {}
        return sprinting;
    }
}