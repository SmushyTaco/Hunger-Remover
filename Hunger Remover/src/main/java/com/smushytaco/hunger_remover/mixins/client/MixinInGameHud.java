package com.smushytaco.hunger_remover.mixins.client;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
    @Shadow
    protected abstract int getHeartCount(LivingEntity entity);
    @Shadow
    protected abstract LivingEntity getRiddenEntity();
    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/hud/InGameHud;getHeartCount(Lnet/minecraft/entity/LivingEntity;)I"))
    private int hookRenderStatusBars(InGameHud hud, LivingEntity entity) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod()) return getHeartCount(getRiddenEntity());
        // This tricks the code into thinking that there will be a mount
        // health bar to be rendered instead of the hunger bar.
        return -1;
    }
}