package com.smushytaco.hunger_remover.mixins.client;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
    @WrapOperation(method = "renderStatusBars", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/hud/InGameHud;getHeartCount(Lnet/minecraft/entity/LivingEntity;)I"))
    private int hookRenderStatusBars(InGameHud instance, LivingEntity entity, Operation<Integer> original) { return HungerRemover.INSTANCE.getConfig().getDisableMod() ? original.call(instance, entity) : -1; }
}