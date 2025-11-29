package com.smushytaco.hunger_remover.mixins.client;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(Gui.class)
public abstract class MixinInGameHud {
    @Shadow
    protected abstract int getVehicleMaxHearts(@Nullable LivingEntity entity);
    @Shadow
    @Nullable
    protected abstract LivingEntity getPlayerVehicleWithHealth();
    @WrapOperation(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVehicleMaxHearts(Lnet/minecraft/world/entity/LivingEntity;)I"))
    private int hookRenderStatusBarsOne(Gui instance, LivingEntity entity, Operation<Integer> original) { return HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getHideHungerBar() ? original.call(instance, entity) : -1; }
    @WrapOperation(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderArmor(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;IIII)V"))
    private void hookRenderStatusBarsTwo(GuiGraphics context, Player player, int i, int j, int k, int x, Operation<Void> original) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getHideHungerBar() || !HungerRemover.INSTANCE.getConfig().getMoveArmorBarToHungerBar() || getVehicleMaxHearts(getPlayerVehicleWithHealth()) != 0) {
            original.call(context, player, i, j, k, x);
        } else {
            original.call(context, player, i - 1, 0, 11, x + 101);
        }
    }
    @WrapOperation(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderAirBubbles(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;III)V"))
    private void hookRenderStatusBarsThree(Gui instance, GuiGraphics context, Player player, int heartCount, int top, int left, Operation<Void> original, @Local Player playerEntity, @Local LivingEntity livingEntity) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getHideHungerBar()) {
            original.call(instance, context, player, heartCount, top, left);
            return;
        }
        int rideableHeartCount = getVehicleMaxHearts(livingEntity);
        if (!HungerRemover.INSTANCE.getConfig().getMoveArmorBarToHungerBar() || playerEntity.getArmorValue() == 0) {
            original.call(instance, context, player, rideableHeartCount, top, left);
        } else {
            original.call(instance, context, player, rideableHeartCount, rideableHeartCount == 0 ? top - 10 : top, left);
        }
    }
}