package com.smushytaco.hunger_remover.mixins.client;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.smushytaco.hunger_remover.HungerRemover;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(Hud.class)
public abstract class MixinInGameHud {
    @Shadow
    protected abstract int getVehicleMaxHearts(@Nullable LivingEntity vehicle);
    @Shadow
    @Nullable
    protected abstract LivingEntity getPlayerVehicleWithHealth();
    @WrapOperation(method = "extractPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Hud;getVehicleMaxHearts(Lnet/minecraft/world/entity/LivingEntity;)I"))
    private int hookRenderStatusBarsOne(Hud instance, LivingEntity vehicle, Operation<Integer> original) { return HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getHideHungerBar() ? original.call(instance, vehicle) : -1; }
    @WrapOperation(method = "extractPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Hud;extractArmor(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;IIII)V"))
    private void hookRenderStatusBarsTwo(GuiGraphicsExtractor graphics, Player player, int yLineBase, int numHealthRows, int healthRowHeight, int xLeft, Operation<Void> original) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getHideHungerBar() || !HungerRemover.INSTANCE.getConfig().getMoveArmorBarToHungerBar() || getVehicleMaxHearts(getPlayerVehicleWithHealth()) != 0) {
            original.call(graphics, player, yLineBase, numHealthRows, healthRowHeight, xLeft);
        } else {
            original.call(graphics, player, yLineBase - 1, 0, 11, xLeft + 101);
        }
    }
    @WrapOperation(method = "extractPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Hud;extractAirBubbles(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;III)V"))
    private void hookRenderStatusBarsThree(Hud instance, GuiGraphicsExtractor graphics, Player player, int vehicleHearts, int yLineAir, int xRight, Operation<Void> original, @SuppressWarnings("NameDoesntMatchTargetClass") @Local(name = "player") Player playerEntity, @Local(name = "vehicleWithHearts") LivingEntity vehicleWithHearts) {
        if (HungerRemover.INSTANCE.getConfig().getDisableMod() || !HungerRemover.INSTANCE.getConfig().getHideHungerBar()) {
            original.call(instance, graphics, player, vehicleHearts, yLineAir, xRight);
            return;
        }
        int rideableHeartCount = getVehicleMaxHearts(vehicleWithHearts);
        if (!HungerRemover.INSTANCE.getConfig().getMoveArmorBarToHungerBar() || playerEntity.getArmorValue() == 0) {
            original.call(instance, graphics, player, rideableHeartCount, yLineAir, xRight);
        } else {
            original.call(instance, graphics, player, rideableHeartCount, rideableHeartCount == 0 ? yLineAir - 10 : yLineAir, xRight);
        }
    }
}