package com.smushytaco.hunger_remover.mixins.client;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
    @Shadow
    protected abstract int getHeartCount(@Nullable LivingEntity entity);
    @Shadow
    @Nullable
    protected abstract LivingEntity getRiddenEntity();
    @WrapOperation(method = "renderStatusBars", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/hud/InGameHud;getHeartCount(Lnet/minecraft/entity/LivingEntity;)I"))
    private int hookRenderStatusBarsOne(InGameHud instance, LivingEntity entity, Operation<Integer> original) { return HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getHideHungerBar() ? original.call(instance, entity) : -1; }
    @WrapOperation(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderArmor(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;IIII)V"))
    private void hookRenderStatusBarsTwo(DrawContext context, PlayerEntity player, int i, int j, int k, int x, Operation<Void> original) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getHideHungerBar() || !HungerRemover.INSTANCE.getConfig().getMoveArmorBarToHungerBar() || getHeartCount(getRiddenEntity()) != 0) {
            original.call(context, player, i, j, k, x);
        } else {
            original.call(context, player, i - 1, 0, 11, x + 101);
        }
    }
    @WrapOperation(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderAirBubbles(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;III)V"))
    private void hookRenderStatusBarsThree(InGameHud instance, DrawContext context, PlayerEntity player, int heartCount, int top, int left, Operation<Void> original, @Local LivingEntity livingEntity) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getHideHungerBar()) {
            original.call(instance, context, player, heartCount, top, left);
            return;
        }
        int rideableHeartCount = getHeartCount(livingEntity);
        if (!HungerRemover.INSTANCE.getConfig().getMoveArmorBarToHungerBar()) {
            original.call(instance, context, player, rideableHeartCount, top, left);
        } else {
            original.call(instance, context, player, rideableHeartCount, rideableHeartCount == 0 ? top - 10 : top, left);
        }
    }
}